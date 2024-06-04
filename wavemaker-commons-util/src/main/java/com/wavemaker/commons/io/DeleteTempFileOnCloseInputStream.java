/*******************************************************************************
 * Copyright (C) 2022-2023 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.wavemaker.commons.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wavemaker.commons.io.local.LocalFile;

/**
 * @author Uday Shankar
 */
public class DeleteTempFileOnCloseInputStream extends FileInputStream {
    private final File tempFile;
    private static final Logger logger = LoggerFactory.getLogger(DeleteTempFileOnCloseInputStream.class);

    public DeleteTempFileOnCloseInputStream(File tempFile) throws FileNotFoundException {
        super(tempFile);
        this.tempFile = tempFile;
        TempFileManager.register(this, tempFile);
    }

    public DeleteTempFileOnCloseInputStream(com.wavemaker.commons.io.File tempFile) throws FileNotFoundException {
        this(((LocalFile) tempFile).getLocalFile());
    }

    @Override
    public void close() throws IOException {
        super.close();
        deleteTempFile();
        TempFileManager.unregister(this);
    }

    public File getTempFile() {
        return tempFile;
    }

    private void deleteTempFile() {
        try {
            Files.deleteIfExists(tempFile.toPath());
        } catch (Exception e) {
            logger.warn("Unable to delete the temp file {} on closing the stream.", tempFile, e);
        }
    }

    public static class TempFileManager {
        private static ScheduledExecutorService scheduler;
        private static final Map<WeakReference<DeleteTempFileOnCloseInputStream>, File> objectsMap = new ConcurrentHashMap<>();

        private TempFileManager() {
        }

        public static void stopScheduler() {
            synchronized (TempFileManager.class) {
                if (scheduler != null && !scheduler.isShutdown()) {
                    logger.info("Shutting down temp-file-delete-thread");
                    scheduler.shutdown();
                }
            }
        }

        private static void register(DeleteTempFileOnCloseInputStream stream, File tempFile) {
            objectsMap.put(new WeakReference<>(stream), tempFile);
            startSchedulerIfNeeded();
        }

        private static void unregister(DeleteTempFileOnCloseInputStream stream) {
            objectsMap.keySet().removeIf(weakReference -> weakReference.get() == stream);
        }

        private static void startSchedulerIfNeeded() {
            synchronized (TempFileManager.class) {
                if (scheduler == null || scheduler.isShutdown()) {
                    scheduler = Executors.newSingleThreadScheduledExecutor(runnable -> {
                        Thread thread = new Thread(runnable, "temp-file-delete-thread");
                        thread.setUncaughtExceptionHandler((t, e) -> logger.error("Exception in thread {}", t, e));
                        return thread;
                    });
                    logger.info("Starting temp-file-delete-thread for temp files clean up");
                    scheduler.scheduleAtFixedRate(TempFileManager::cleanUpTempFiles, 1, 5, TimeUnit.MINUTES);
                }
            }
        }

        private static void cleanUpTempFiles() {
            logger.info("Trying to clean up temp files that are unreferenced");
            Iterator<Map.Entry<WeakReference<DeleteTempFileOnCloseInputStream>, File>> iterator = objectsMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<WeakReference<DeleteTempFileOnCloseInputStream>, File> entry = iterator.next();
                WeakReference<DeleteTempFileOnCloseInputStream> weakReference = entry.getKey();
                if (weakReference.get() == null) {
                    File tempFile = entry.getValue();
                    try {
                        Files.deleteIfExists(tempFile.toPath());
                        iterator.remove();
                    } catch (Exception e) {
                        logger.warn("Unable to delete the temp file {} during cleanup.", tempFile, e);
                    }
                }
            }
        }
    }
}
