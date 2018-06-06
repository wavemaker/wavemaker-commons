/**
 * Copyright (C) 2015 WaveMaker, Inc.
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
package com.wavemaker.commons.zip;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import com.wavemaker.commons.io.File;
import com.wavemaker.commons.io.Folder;
import com.wavemaker.commons.io.JailedResourcePath;
import com.wavemaker.commons.io.NoCloseInputStream;
import com.wavemaker.commons.io.Resource;
import com.wavemaker.commons.io.ResourcePath;
import com.wavemaker.commons.io.exception.ReadOnlyResourceException;
import com.wavemaker.commons.io.exception.ResourceException;
import com.wavemaker.commons.io.store.FileStore;
import com.wavemaker.commons.io.store.FolderStore;
import com.wavemaker.commons.io.store.ResourceStore;
import com.wavemaker.commons.io.store.StoredFile;
import com.wavemaker.commons.io.store.StoredFolder;
import com.wavemaker.commons.util.WMIOUtils;

/**
 * {@link ResourceStore}s for {@link ZipFile} and {@link ZipArchive}.
 * 
 * @author Phillip Webb
 */
abstract class ZipResourceStore implements ResourceStore {

    private final JailedResourcePath path;

    private final ZipFile zipFile;

    public ZipResourceStore(ZipFile zipFile, JailedResourcePath path) {
        this.zipFile = zipFile;
        this.path = path;
    }

    protected ZipFile getZipFile() {
        return this.zipFile;
    }

    @Override
    public JailedResourcePath getPath() {
        return this.path;
    }

    @Override
    public Resource getExisting(JailedResourcePath path) {
        ZipFile.ZipFileDetailsEntry entry = getZipFile().getEntry(path, false);
        if (entry == null) {
            return null;
        }
        return entry.isFolder() ? getFolder(path) : getFile(path);
    }

    @Override
    public Folder getFolder(JailedResourcePath path) {
        final ZipFolderStore store = new ZipFolderStore(getZipFile(), path);
        return new StoredFolder() {

            @Override
            protected FolderStore getStore() {
                return store;
            }
        };
    }

    @Override
    public File getFile(JailedResourcePath path) {
        final ZipFileStore store = new ZipFileStore(getZipFile(), path);
        return new StoredFile() {

            @Override
            protected FileStore getStore() {
                return store;
            }
        };
    }

    @Override
    public boolean exists() {
        return getZipFile().getEntry(this.path, false) != null;
    }

    @Override
    public Resource rename(String name) {
        throw createReadOnlyException();
    }

    @Override
    public void delete() {
        throw createReadOnlyException();
    }

    @Override
    public void create() {
        throw createReadOnlyException();
    }

    @Override
    public int hashCode() {
        return this.zipFile.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ZipResourceStore other = (ZipResourceStore) obj;
        return ObjectUtils.nullSafeEquals(this.zipFile, other.zipFile);
    }

    /**
     * create a new {@link ReadOnlyResourceException} that should be thrown on any write operations.
     * 
     * @return the ReadOnlyResourceException exception to throw
     */
    protected final ReadOnlyResourceException createReadOnlyException() {
        throw new ReadOnlyResourceException("The Zip File " + getZipFile() + " is read-only");
    }

    static class ZipFileStore extends ZipResourceStore implements FileStore {

        public ZipFileStore(ZipFile zipFile, JailedResourcePath path) {
            super(zipFile, path);
        }

        @Override
        public InputStream getInputStream() {
            try {
                return getZipFile().getEntry(getPath()).getInputStream();
            } catch (IOException e) {
                throw new ResourceException(e);
            }
        }

        @Override
        public OutputStream getOutputStream() {
            throw createReadOnlyException();
        }

        @Override
        public OutputStream getOutputStream(boolean append) {
            throw createReadOnlyException();
        }

        @Override
        public long getSize() {
            return getZipFile().getEntry(getPath()).getSize();
        }

        @Override
        public long getLastModified() {
            return getZipFile().getLastModified();
        }

        @Override
        public void touch() {
            throw createReadOnlyException();
        }
    }

    static class ZipFolderStore extends ZipResourceStore implements FolderStore {

        public ZipFolderStore(File zipFile) {
            this(new ZipFile(zipFile), new JailedResourcePath());
        }

        public ZipFolderStore(ZipFile zipFile, JailedResourcePath path) {
            super(zipFile, path);
        }

        @Override
        public Iterable<String> list() {
            return getZipFile().getEntry(getPath()).list();
        }

        @Override
        public long getLastModified() {
            return getZipFile().getLastModified();
        }
    }

    private static class ZipFile {

        private final File file;

        private final Map<ResourcePath, ZipFileDetailsEntry> entries = new HashMap<>();

        private boolean loadedAtLeastOnce;

        private long size;

        private long lastModified;

        public ZipFile(File file) {
            Assert.notNull(file, "ZipFile must not be null");
            Assert.isTrue(file.exists(), "ZipFile must exist");
            this.file = file;
        }

        public long getLastModified() {
            return this.file.getLastModified();
        }

        /**
         * Get {@link ZipFileDetailsEntry} for the specified key. This method will never return null.
         * 
         * @param path the path to find
         * @return the {@link ZipFileDetailsEntry}
         * @throws IOException
         */
        private ZipFileDetailsEntry getEntry(JailedResourcePath path) {
            return getEntry(path, true);
        }

        /**
         * Get {@link ZipFileDetailsEntry} for the specified key if it exists.
         * 
         * @param path the path to find
         * @param required if the entry must exist
         * @return the {@link ZipFileDetailsEntry}
         * @throws IOException
         */
        private ZipFileDetailsEntry getEntry(JailedResourcePath path, boolean required) {
            if (isUnderlyingZipFileChanged()) {
                try {
                    reloadZipFile();
                } catch (IOException e) {
                    throw new ResourceException(e);
                }
            }
            ResourcePath unjailedPath = path.getUnjailedPath();
            ZipFileDetailsEntry entry = this.entries.get(unjailedPath);
            if (entry == null && required) {
                return new MissingZipFileDetailsEntry(unjailedPath);
            }
            return entry;
        }

        /**
         * Determine if the underlying zip file has changed.
         * 
         * @return if the underlying file has changed.
         */
        private boolean isUnderlyingZipFileChanged() {
            return !this.loadedAtLeastOnce || this.size != this.file
                    .getSize() || this.lastModified != this.file.getLastModified();
        }

        private void reloadZipFile() throws IOException {
            addZipEntries();
            createMissingFolderEntries();
            this.size = this.file.getSize();
            this.lastModified = this.file.getLastModified();
            this.loadedAtLeastOnce = true;
        }

        protected final ZipInputStream openZipInputStream() {
            return new ZipInputStream(new BufferedInputStream(this.file.getContent().asInputStream()));
        }

        /**
         * Add entries from the zip file.
         * 
         * @throws IOException
         */
        private void addZipEntries() throws IOException {
            ZipInputStream zipInputStream = openZipInputStream();
            try {
                ZipEntry entry = zipInputStream.getNextEntry();
                while (entry != null) {
                    ResourcePath path = new ResourcePath().get(entry.getName());
                    this.entries.put(path, new ZipEntryZipFileDetailsEntry(path, entry));
                    entry = zipInputStream.getNextEntry();
                }
            } finally {
                zipInputStream.close();
            }
        }

        /**
         * Create any missing folder entries. This can occur if a zip file does include entries for parent folders.
         */
        private void createMissingFolderEntries() {
            List<MissingZipFileDetailsEntry> missingEntries = new ArrayList<>();
            Set<ResourcePath> paths = this.entries.keySet();
            for (ResourcePath path : paths) {
                path = path.getParent();
                while (path != null) {
                    if (!this.entries.containsKey(path)) {
                        missingEntries.add(new MissingZipFileDetailsEntry(path));
                    }
                    path = path.getParent();
                }
            }
            for (MissingZipFileDetailsEntry missingEntry : missingEntries) {
                this.entries.put(missingEntry.getPath(), missingEntry);
            }
        }

        @Override
        public int hashCode() {
            return this.file.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            ZipFile other = (ZipFile) obj;
            return ObjectUtils.nullSafeEquals(this.file, other.file);
        }

        /**
         * Details for a single entry from the {@link }.
         */
        private abstract class ZipFileDetailsEntry {

            private List<String> list;

            private final ResourcePath path;

            public ZipFileDetailsEntry(ResourcePath path) {
                this.path = path;
            }

            public abstract boolean isFolder();

            public abstract InputStream getInputStream() throws IOException;

            public abstract long getSize();

            public Iterable<String> list() {
                if (this.list == null) {
                    this.list = new ArrayList<>();
                    if (isFolder()) {
                        for (ResourcePath entryPath : ZipFile.this.entries.keySet()) {
                            if (isParent(entryPath)) {
                                this.list.add(entryPath.getName());
                            }
                        }
                    }
                }
                return this.list;
            }

            private boolean isParent(ResourcePath path) {
                return path != null && this.path.equals(path.getParent());
            }

            public ResourcePath getPath() {
                return this.path;
            }
        }

        private class MissingZipFileDetailsEntry extends ZipFileDetailsEntry {

            public MissingZipFileDetailsEntry(ResourcePath path) {
                super(path);
            }

            @Override
            public boolean isFolder() {
                return true;
            }

            @Override
            public InputStream getInputStream() {
                return null;
            }

            @Override
            public long getSize() {
                return 0;
            }
        }

        private class ZipEntryZipFileDetailsEntry extends ZipFileDetailsEntry {

            private final ZipEntry entry;

            private WeakReference<byte[]> contents;

            public ZipEntryZipFileDetailsEntry(ResourcePath path, ZipEntry entry) {
                super(path);
                this.entry = entry;
            }

            @Override
            public boolean isFolder() {
                return this.entry.isDirectory();
            }

            @Override
            public InputStream getInputStream() throws IOException {
                byte[] bytes = this.contents == null ? null : this.contents.get();
                try {
                    if (bytes == null) {
                        bytes = getZipEntryBytes();
                        this.contents = new WeakReference<>(bytes);
                    }
                    return new ByteArrayInputStream(bytes);
                } finally {
                    this.contents = null;
                }
            }

            private byte[] getZipEntryBytes() throws IOException {
                ZipInputStream zipInputStream = openZipInputStream();
                try {
                    ZipEntry zipEntry = zipInputStream.getNextEntry();
                    while (zipEntry != null) {
                        if (!zipEntry.isDirectory()) {
                            ResourcePath path = new ResourcePath().get(zipEntry.getName());
                            if (getPath().equals(path)) {
                                return IOUtils.toByteArray(new NoCloseInputStream(zipInputStream));
                            }
                        }
                        zipEntry = zipInputStream.getNextEntry();
                    }
                    throw new IllegalStateException("Unable to find ZipEntry for " + getPath());
                } finally {
                    WMIOUtils.closeSilently(zipInputStream);
                }
            }

            @Override
            public long getSize() {
                return this.entry.getSize();
            }
        }
    }
}
