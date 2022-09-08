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
package com.wavemaker.commons.zip;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.util.Assert;

import com.wavemaker.commons.io.File;
import com.wavemaker.commons.io.Folder;
import com.wavemaker.commons.io.NoCloseInputStream;
import com.wavemaker.commons.io.Resources;
import com.wavemaker.commons.io.exception.ResourceException;
import com.wavemaker.commons.io.store.FolderStore;
import com.wavemaker.commons.io.store.StoredFolder;
import com.wavemaker.commons.util.WMIOUtils;

/**
 * Adapter class that can be used present a zip file as a {@link Folder}.
 *
 * @author Phillip Webb
 */
public class ZipArchive extends StoredFolder {

    private final ZipResourceStore.ZipFolderStore store;

    /**
     * Create a new {@link ZipArchive} instance from the specified zip file.
     *
     * @param zipFile the zip file
     */
    public ZipArchive(File zipFile) {
        this.store = new ZipResourceStore.ZipFolderStore(zipFile);
    }

    @Override
    protected FolderStore getStore() {
        return this.store;
    }

    /**
     * Unzip the specified zip file into a folder.
     *
     * @param file        the file to unzip (this must reference a zip file)
     * @param destination the destination folder
     */
    public static void unpack(File file, Folder destination) {
        Assert.notNull(file, "File must not be null");
        Assert.notNull(destination, "Destination must not be null");
        unpack(file.getContent().asInputStream(), destination);
    }

    /**
     * Unzip the specified input stream into a folder.
     *
     * @param inputStream the input stream to unzip (this must contain zip contents)
     * @param destination the destination folder
     */
    public static void unpack(InputStream inputStream, Folder destination) {
        Assert.notNull(inputStream, "InputStream must not be null");
        Assert.notNull(destination, "Destination must not be null");
        destination.createIfMissing();
        ZipInputStream zip = new ZipInputStream(new BufferedInputStream(inputStream));
        try {
            InputStream noCloseZip = new NoCloseInputStream(zip);
            ZipEntry entry = zip.getNextEntry();
            while (entry != null) {
                if (entry.isDirectory()) {
                    destination.getFolder(entry.getName()).createIfMissing();
                } else {
                    destination.getFile(entry.getName()).getContent().write(noCloseZip);
                }
                entry = zip.getNextEntry();
            }
        } catch (IOException e) {
            throw new ResourceException(e);
        } finally {
            WMIOUtils.closeSilently(zip);
        }
    }

    public static void compress(Folder folder, File file) {
        InputStream inputStream = compress(folder);
        file.getContent().write(inputStream);
    }

    /**
     * Create a {@link InputStream} containing a zip representation of the given folder.
     *
     * @param folder the folder to compress
     */
    public static InputStream compress(Folder folder) {
        return compress(folder, (String) null);
    }

    /**
     * Create a {@link InputStream} containing a zip representation of the given folder.
     *
     * @param folder the folder to compress
     * @param prefix an optional entry prefix. This allows a entries to be nested within a folder if required
     */
    public static InputStream compress(Folder folder, String prefix) {
        return compress(folder.find(), prefix);
    }

    /**
     * Create a {@link InputStream} containing a zip representation of the given resources.
     *
     * @param resources resources to compress
     */
    public static InputStream compress(Resources<?> resources) {
        return compress(resources, null);
    }

    /**
     * Create a {@link InputStream} containing a zip representation of the given resources.
     *
     * @param resources resources to compress
     * @param prefix    an optional entry prefix. This allows a entries to be nested within a folder if required
     */
    public static InputStream compress(Resources<?> resources, String prefix) {
        return new ZipResourcesStream(resources, prefix);
    }

}
