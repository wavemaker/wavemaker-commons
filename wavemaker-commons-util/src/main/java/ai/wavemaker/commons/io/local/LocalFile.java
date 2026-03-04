/*******************************************************************************
 * Copyright (C) 2024-2025 WaveMaker, Inc.
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
package ai.wavemaker.commons.io.local;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.BasicFileAttributes;

import ai.wavemaker.commons.io.File;
import ai.wavemaker.commons.io.store.FileStore;
import ai.wavemaker.commons.io.store.StoredFile;

/**
 * A {@link File} implementation backed by standard {@link File java.io.File}s.
 *
 * @author Phillip Webb
 * @see LocalFolder
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

    public long getCreationTime() {
        long creationTime = 0L;
        try {
            creationTime = Files.readAttributes(getLocalFile().toPath(), BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS).creationTime().toMillis();
        } catch (IOException ignored) {
        }
        return creationTime;
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
