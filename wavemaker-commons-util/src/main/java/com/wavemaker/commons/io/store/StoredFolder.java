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
package com.wavemaker.commons.io.store;

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.springframework.util.Assert;

import com.wavemaker.commons.io.AbstractResources;
import com.wavemaker.commons.io.File;
import com.wavemaker.commons.io.Folder;
import com.wavemaker.commons.io.JailedResourcePath;
import com.wavemaker.commons.io.Resource;
import com.wavemaker.commons.io.ResourcePath;
import com.wavemaker.commons.io.ResourceStringFormat;
import com.wavemaker.commons.io.Resources;
import com.wavemaker.commons.io.ResourcesCollection;
import com.wavemaker.commons.io.exception.ResourceDoesNotExistException;
import com.wavemaker.commons.io.exception.ResourceExistsException;

/**
 * A {@link Folder} that is backed by a {@link FolderStore}. Allows developers to use the simpler {@link FolderStore}
 * interface to provide a full {@link Folder} implementation. Subclasses must provide a suitable {@link FolderStore}
 * implementation via the {@link #getStore()} method.
 * 
 * @see FolderStore
 * @see StoredFile
 * 
 * @author Phillip Webb
 */
public abstract class StoredFolder extends StoredResource implements Folder {

    private static final String NAME_EMPTY_MESSAGE = "Name must not be empty";

    @Override
    protected abstract FolderStore getStore();

    @Override
    public long getLastModified() {
        return getStore().getLastModified();
    }

    @Override
    public Resource getExisting(String name) throws ResourceDoesNotExistException {
        Assert.hasLength(name, NAME_EMPTY_MESSAGE);
        JailedResourcePath resourcePath = getPath().get(name);
        Resource resource = getStore().getExisting(resourcePath);
        if (resource == null) {
            throw new ResourceDoesNotExistException(this, name);
        }
        return resource;
    }

    @Override
    public boolean hasExisting(String name) {
        Assert.hasLength(name, NAME_EMPTY_MESSAGE);
        JailedResourcePath resourcePath = getPath().get(name);
        Resource existing = getStore().getExisting(resourcePath);
        return existing != null;
    }

    @Override
    public Folder getFolder(String name) {
        Assert.hasLength(name, NAME_EMPTY_MESSAGE);
        JailedResourcePath folderPath = getPath().get(name);
        return getStore().getFolder(folderPath);
    }

    @Override
    public File getFile(String name) {
        Assert.hasLength(name, NAME_EMPTY_MESSAGE);
        JailedResourcePath filePath = getPath().get(name);
        return getStore().getFile(filePath);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Resource> T get(String name, Class<T> resourceType) {
        Assert.hasLength(name, NAME_EMPTY_MESSAGE);
        Assert.notNull(resourceType, "ResourceType must not be null");
        if (resourceType.equals(Folder.class)) {
            return (T) getFolder(name);
        }
        if (resourceType.equals(File.class)) {
            return (T) getFile(name);
        }
        return (T) getExisting(name);
    }

    @Override
    public Iterator<Resource> iterator() {
        return list().iterator();
    }

    @Override
    public Resources<Resource> list() {
        if (!exists()) {
            return new ResourcesCollection<>(this);
        }
        return new ChildResources(() -> new ChildResourceIterator(StoredFolder.this));
    }

    @Override
    public Resources<Resource> find() {
        if (!exists()) {
            return new ResourcesCollection<>(this);
        }
        return new ChildResources(() -> new RecursiveChildResourceIterator(StoredFolder.this));
    }

    @Override
    public Folder copyTo(Folder folder) {
        Assert.notNull(folder, "Folder must not be empty");
        ensureExists();
        Assert.state(getPath().getParent() != null, "Unable to copy a root folder");
        Folder destination = createDestinationFolder(folder);
        for (Resource child : list()) {
            child.copyTo(destination);
        }
        return destination;
    }

    @Override
    public Resources<Resource> copyContentsTo(Folder folder) {
        return list().copyTo(folder);
    }

    @Override
    public Folder moveTo(Folder folder) {
        Assert.notNull(folder, "Folder must not be empty");
        ensureExists();
        Assert.state(getPath().getParent() != null, "Unable to move a root folder");
        Folder destination = createDestinationFolder(folder);
        for (Resource child : list()) {
            child.moveTo(destination);
        }
        return destination;
    }

    @Override
    public Resources<Resource> moveContentsTo(Folder folder) {
        return list().moveTo(folder);
    }




    private Folder createDestinationFolder(Folder folder) {
        Folder destination = folder.getFolder(getName());
        destination.createIfMissing();
        return destination;
    }

    @Override
    public Folder rename(String name) throws ResourceExistsException {
        return (Folder) super.rename(name);
    }

    @Override
    public void delete() {
        if (exists()) {
            for (Resource child : list()) {
                child.delete();
            }
            getStore().delete();
        }
    }

    @Override
    public void createIfMissing() {
        if (!exists()) {
            createParentIfMissing();
            getStore().create();
        }
    }

    @Override
    public Folder jail() {
        JailedResourcePath jailedPath = new JailedResourcePath(getPath().getUnjailedPath(), new ResourcePath());
        return getStore().getFolder(jailedPath);
    }

    @Override
    public String toString(ResourceStringFormat format) {
        return super.toString(format) + "/";
    }

    private static class ChildResourceIterator implements Iterator<Resource> {

        private final StoredFolder folder;

        private final Iterator<String> childNames;

        private Resource next;

        public ChildResourceIterator(StoredFolder folder) {
            this.folder = folder;
            Iterable<String> list = folder.getStore().list();
            this.childNames = list == null ? Collections.<String> emptyList().iterator() : list.iterator();
        }

        @Override
        public boolean hasNext() {
            ensureNextHasBeenFetched();
            return this.next != null;
        }

        @Override
        public Resource next() {
            try {
                ensureNextHasBeenFetched();
                if (this.next == null) {
                    throw new NoSuchElementException();
                }
                return this.next;
            } finally {
                this.next = null;
            }
        }

        private void ensureNextHasBeenFetched() {
            while (this.next == null && this.childNames.hasNext()) {
                String name = this.childNames.next();
                JailedResourcePath path = this.folder.getStore().getPath().get(name);
                Resource resource = this.folder.getStore().getExisting(path);
                if (resource != null) {
                    this.next = resource;
                }
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class RecursiveChildResourceIterator implements Iterator<Resource> {

        private final Iterator<Resource> iterator;

        private Iterator<Resource> current;

        public RecursiveChildResourceIterator(StoredFolder folder) {
            this.iterator = new ChildResourceIterator(folder);
        }

        @Override
        public boolean hasNext() {
            return this.current != null && this.current.hasNext() || this.iterator.hasNext();
        }

        @Override
        public Resource next() {
            if (this.current != null && this.current.hasNext()) {
                return this.current.next();
            }
            this.current = null;
            Resource next = this.iterator.next();
            if (next instanceof StoredFolder) {
                StoredFolder folder = (StoredFolder) next;
                this.current = new RecursiveChildResourceIterator(folder);
            }
            return next;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class ChildResources extends AbstractResources<Resource> {

        private final Iterable<Resource> iterable;

        public ChildResources(Iterable<Resource> iterable) {
            Assert.notNull(iterable, "Iterable must not be null");
            this.iterable = iterable;
        }

        @Override
        public Folder getSource() {
            return StoredFolder.this;
        }

        @Override
        public Iterator<Resource> iterator() {
            return this.iterable.iterator();
        }

    }

}
