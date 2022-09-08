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

import com.wavemaker.commons.io.File;
import com.wavemaker.commons.io.store.FileStore;
import com.wavemaker.commons.io.store.StoredFile;

/**
 * A {@link File} implementation backed by standard {@link File java.io.File}s.
 * 
 * @see LocalFolder
 * 
 * @author Phillip Webb
 */
public class LocalFile extends StoredFile {

    private final LocalResourceStore.LocalFileStore store;

    /**
     * Package scope constructor, files should only be accessed via the {@link LocalFolder},
     * 
     * @param store the file store
     */
    LocalFile(LocalResourceStore.LocalFileStore store) {
        this.store = store;
    }

    @Override
    protected FileStore getStore() {
        return this.store;
    }

    /**
     * Returns access to the underlying local {@link File}.
     * 
     * @return the underlying {@link File}
     */
    public java.io.File getLocalFile() {
        return this.store.getFile();
    }
}
