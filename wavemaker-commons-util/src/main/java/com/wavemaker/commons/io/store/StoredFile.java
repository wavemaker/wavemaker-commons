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
package com.wavemaker.commons.io.store;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.springframework.util.Assert;

import com.wavemaker.commons.ResourceAlreadyExistException;
import com.wavemaker.commons.io.AbstractFileContent;
import com.wavemaker.commons.io.File;
import com.wavemaker.commons.io.FileContent;
import com.wavemaker.commons.io.Folder;
import com.wavemaker.commons.io.exception.ResourceException;
import com.wavemaker.commons.util.WMIOUtils;

/**
 * A {@link File} that is backed by a {@link FileStore}. Allows developers to use the simpler {@link FileStore}
 * interface to provide a full {@link File} implementation. Subclasses must provide a suitable {@link FileStore}
 * implementation via the {@link #getStore()} method.
 *
 * @author Phillip Webb
 * @see FileStore
 * @see StoredFolder
 */
public abstract class StoredFile extends StoredResource implements File {

    private static final String FOLDER_NULL_MESSAGE = "Folder must not be null";
    private final StoredFileContent content = new StoredFileContent();

    @Override
    protected abstract FileStore getStore();

    @Override
    public long getSize() {
        return getStore().getSize();
    }

    @Override
    public long getLastModified() {
        return getStore().getLastModified();
    }

    @Override
    public void touch() {
        ensureExists();
        getStore().touch();
    }

    @Override
    public FileContent getContent() {
        return this.content;
    }

    @Override
    public File rename(String name) {
        return (File) super.rename(name);
    }

    @Override
    public void delete() {
        if (exists()) {
            getStore().delete();
        }
    }

    @Override
    public File moveTo(Folder folder) {
        Assert.notNull(folder, FOLDER_NULL_MESSAGE);
        ensureExists();
        File destination = folder.getFile(getName());
        if (destination.exists()) {
            destination.delete();
        }
        try {
            FileUtils.moveFile(WMIOUtils.getJavaIOFile(this), WMIOUtils.getJavaIOFile(destination));
        } catch (IOException e) {
            throw new ResourceException(e);
        }
        return destination;

    }

    @Override
    public File copyTo(Folder folder) {
        Assert.notNull(folder, FOLDER_NULL_MESSAGE);
        ensureExists();
        File destination = folder.getFile(getName());
        destination.getContent().write(this);
        return destination;
    }

    @Override
    public File copyToIfNewer(Folder folder) {
        Assert.notNull(folder, FOLDER_NULL_MESSAGE);
        ensureExists();
        File destination = folder.getFile(getName());
        if (!destination.exists() || this.isModifiedAfter(destination)) {
            destination.getContent().write(this);
            return destination;
        } else {
            return null;
        }
    }

    @Override
    public void createIfMissing() {
        if (!exists()) {
            createParentIfMissing();
            try {
                getStore().create();
            } catch (ResourceAlreadyExistException e) {
                // ignore
            }
        }
    }

    /**
     * Called to write the contents of another file to this file. This method is can optionally be implemented by
     * subclasses to implement custom file copy strategies.
     *
     * @param file the file being written to this one
     *
     * @return if the write operation has been handled. Return <tt>false</tt> for standard stream based writes.
     */
    protected boolean write(File file) {
        return false;
    }

    private class StoredFileContent extends AbstractFileContent {

        @Override
        public InputStream asInputStream() {
            return getStore().getInputStream();
        }

        @Override
        public OutputStream asOutputStream() {
            return asOutputStream(false);
        }

        @Override
        public OutputStream asOutputStream(boolean append) {
            createParentIfMissing();
            return getStore().getOutputStream(append);
        }

        @Override
        public void write(File file) {
            createParentIfMissing();
            if (!StoredFile.this.write(file)) {
                super.write(file);
            }
        }
    }
}
