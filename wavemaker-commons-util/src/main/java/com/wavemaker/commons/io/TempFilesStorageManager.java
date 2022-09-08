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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.wavemaker.commons.MessageResource;
import com.wavemaker.commons.ResourceNotFoundException;
import com.wavemaker.commons.WMRuntimeException;
import com.wavemaker.commons.util.FileValidationUtils;
import com.wavemaker.commons.util.WMIOUtils;

/**
 * @author Uday Shankar
 */
//TODO need to implement auto deRegister
public class TempFilesStorageManager {

    private static final Logger logger = LoggerFactory.getLogger(TempFilesStorageManager.class);
    private static final int CLEAN_INTERVAL = 5 * 60 * 1000;
    private static final String AUTO_PURGE_DIR = "auto-purge";
    private static final int AUTO_PURGE_TIME = 30 * 60 * 1000;
    private Long previousCleanUpExecutedTime = System.currentTimeMillis();
    private volatile boolean purgeTaskInProgress;

    private String filesStorageDirectory;


    public TempFilesStorageManager() {
        this.filesStorageDirectory =
                System.getProperty("wm.studio.temp.dir",
                        System.getProperty("java.io.tmpdir")) + File.separator + AUTO_PURGE_DIR;
    }

    private Set<String> getSafeToDeleteFileInfo() {
        Set<String> safeToDeleteFileSet = new HashSet<>();
        File rootDirectory = new File(filesStorageDirectory);
        File[] files = rootDirectory.listFiles();
        final long purgeTime = System.currentTimeMillis() - AUTO_PURGE_TIME;
        for (File file : files) {
            long modifiedTime = file.lastModified();
            if (modifiedTime < purgeTime) {
                safeToDeleteFileSet.add(file.getName());
            }
        }
        return safeToDeleteFileSet;
    }

    public String registerNewFile(MultipartFile multipartFile) {
        InputStream inputStream;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (IOException e) {
            throw new WMRuntimeException(e);
        }
        return registerNewFile(inputStream, multipartFile.getOriginalFilename());
    }

    public String registerNewFile(InputStream inputStream, String filename) {
        String fileId = generateUniqueId();
        return registerNewFile(inputStream, filename, fileId);
    }

    private String registerNewFile(InputStream inputStream, String filename, String fileId) {
        filename = StringUtils.isBlank(filename) ? fileId : filename;
        logger.info("Adding new temp file with fileId {} and filename {}", fileId, filename);
        File uniqueDirectory = getUniqueDirectory(fileId);
        uniqueDirectory.mkdirs();
        File uniqueFile = new File(uniqueDirectory, filename);
        OutputStream outputStream = null;
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(uniqueFile));
            WMIOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            WMIOUtils.deleteDirectorySilently(uniqueDirectory);
            throw new WMRuntimeException(
                    MessageResource.create("com.wavemaker.commons.failed.to.copy.input.stream.to.file"), e, filename);
        } finally {
            WMIOUtils.closeSilently(inputStream);
            WMIOUtils.closeSilently(outputStream);
        }
        purgeOldFiles();
        return fileId;
    }

    public String registerNewFile(String filename) {
        String fileId = generateUniqueId();
        if (StringUtils.isBlank(filename)) {
            filename = generateUniqueId();
        }
        logger.info("Adding new temp file with fileId {} and filename {}", fileId, filename);
        File uniqueDirectory = getUniqueDirectory(fileId);
        uniqueDirectory.mkdirs();
        File uniqueFile = new File(uniqueDirectory, FileValidationUtils.validateFilePath(filename));
        try {
            uniqueFile.createNewFile();
        } catch (IOException e) {
            WMIOUtils.deleteDirectorySilently(uniqueDirectory);
            throw new WMRuntimeException(MessageResource.create("com.wavemaker.commons.failed.to.create.empty.file"), e,
                    filename);
        }
        purgeOldFiles();
        return fileId;
    }

    public void deRegister(String fileId) {
        logger.info("DeRegistering file with unique fileId {}", fileId);
        File uniqueDirectory = getUniqueDirectory(fileId);
        if (uniqueDirectory.exists()) {
            WMIOUtils.deleteDirectorySilently(uniqueDirectory);
        }
    }

    public String getFileName(String fileId) {
        return getUniqueFile(fileId).getName();
    }

    public String getFilePath(String fileId) {
        return getUniqueFile(fileId).getAbsolutePath();
    }

    public InputStream getFileInputStream(String fileId) {
        File uniqueFile = getFile(fileId);
        try {
            return new FileInputStream(uniqueFile);
        } catch (IOException e) {
            throw new WMRuntimeException(MessageResource.create("com.wavemaker.commons.failed.to.get.output.stream"), e,
                    fileId);
        }
    }

    public File getFile(String fileId) {
        logger.info("Accessing file output stream for fileId {}", fileId);
        File uniqueFile = getUniqueFile(fileId);
        if (!uniqueFile.exists()) {
            throw new ResourceNotFoundException(
                    MessageResource.create("com.wavemaker.commons.no.files.found.with.fileid"), fileId);
        }
        return uniqueFile;
    }

    public OutputStream getFileOutputStream(String fileId) {
        File uniqueFile = getFile(fileId);
        try {
            return new FileOutputStream(uniqueFile);
        } catch (IOException e) {
            throw new WMRuntimeException(MessageResource.create("com.wavemaker.commons.failed.to.get.output.stream"), e,
                    fileId);
        }
    }

    private String generateUniqueId() {
        String s = UUID.randomUUID().toString();
        return s.replaceAll("-", "");
    }

    private File getUniqueFile(String fileId) {
        File uniqueDirectory = getUniqueDirectory(fileId);
        if (!uniqueDirectory.exists()) {
            throw new IllegalStateException("Unique file not found in the temp directory with fileId:" + fileId);
        }
        File[] files = uniqueDirectory.listFiles();
        if (files.length == 1) {
            return files[0];
        }
        throw new IllegalStateException(
                "Unique file not found in the temp directory with fileId " + fileId + ".Files count in the directory is " + files.length);
    }

    public File getUniqueDirectory(String fileId) {
        return new File(filesStorageDirectory, FileValidationUtils.validateFilePath(fileId));
    }

    private void purgeOldFiles() {
        if (previousCleanUpExecutedTime + CLEAN_INTERVAL < System.currentTimeMillis()) {
            synchronized (this) {
                if (purgeTaskInProgress) {
                    return;
                }
                purgeTaskInProgress = true;
                try {
                    for (String fileId : getSafeToDeleteFileInfo()) {
                        try {
                            deRegister(fileId);
                        } catch (Exception e) {
                            logger.error("Failed to clean up the temp file with id {}", fileId, e);
                        }
                    }
                } finally {
                    previousCleanUpExecutedTime = System.currentTimeMillis();
                    purgeTaskInProgress = false;
                }
            }
        }
    }
}
