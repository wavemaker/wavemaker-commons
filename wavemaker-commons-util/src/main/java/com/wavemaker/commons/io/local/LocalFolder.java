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
package com.wavemaker.commons.io.local;

import java.io.File;

import com.wavemaker.commons.io.Folder;
import com.wavemaker.commons.io.JailedResourcePath;
import com.wavemaker.commons.io.store.FolderStore;
import com.wavemaker.commons.io.store.StoredFolder;

/**
 * A {@link Folder} implementation backed by standard {@link File java.io.File}s.
 *
 * @author Phillip Webb
 * @see LocalFile
 */
public class LocalFolder extends StoredFolder {

    private final LocalResourceStore.LocalFolderStore store;

    /**
     * Package level constructor used when accessing nested folders.
     *
     * @param store the store
     */
    LocalFolder(LocalResourceStore.LocalFolderStore store) {
        this.store = store;
    }

    /**
     * Create a new {@link LocalFolder} for the specified folder.
     *
     * @param folder the underlying folder, eg '/home/username'
     */
    public LocalFolder(String folder) {
        this(new java.io.File(folder));
    }

    /**
     * Create a new {@link LocalFolder} for the specified folder.
     *
     * @param folder the underlying folder
     */
    public LocalFolder(java.io.File folder) {
        this.store = new LocalResourceStore.LocalFolderStore(folder, new JailedResourcePath());
    }

    @Override
    protected FolderStore getStore() {
        return this.store;
    }

    /**
     * Returns access to the underlying local {@link File}.
     *
     * @return the underlying {@link File}
     */
    public File getLocalFile() {
        return this.store.getFile();
    }
}
