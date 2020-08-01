/**
 * Copyright © 2013 - 2017 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wavemaker.commons.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wavemaker.commons.io.local.LocalFile;

/**
 * @author Uday Shankar
 */
@SuppressWarnings("NoFinalizer")
public class DeleteTempFileOnCloseInputStream extends FileInputStream {

    private File tempFile;

    private static final Logger logger = LoggerFactory.getLogger(DeleteTempFileOnCloseInputStream.class);

    public DeleteTempFileOnCloseInputStream(File tempFile) throws FileNotFoundException {
        super(tempFile);
        this.tempFile = tempFile;
    }

    public DeleteTempFileOnCloseInputStream(com.wavemaker.commons.io.File tempFile) throws FileNotFoundException {
        this(((LocalFile) tempFile).getLocalFile());
    }

    @Override
    public void close() throws IOException {
        super.close();
        deleteTempFile();
    }

    @Override
    protected void finalize() throws IOException {
        deleteTempFile();
        super.finalize();
    }

    private void deleteTempFile() {
        try {
            Files.deleteIfExists(tempFile.toPath());
        } catch (Exception e) {
            logger.warn("Unable to delete the temp file {} on closing the stream.", tempFile, e);
        }
    }
}
