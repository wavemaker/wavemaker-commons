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

import com.wavemaker.commons.io.exception.ResourceDoesNotExistException;

/**
 * A file {@link Resource} that may be stored on a physical disk or using some other mechanism.
 *
 * @author Phillip Webb
 * @see Folder
 * @see MutableFile
 */
public interface File extends Resource {

    @Override
    File moveTo(Folder folder);

    @Override
    File copyTo(Folder folder);

    @Override
    File rename(String name);

    /**
     * Returns the size in bytes of the virtual file.
     *
     * @return the size in bytes
     */
    long getSize();

    /**
     * Update the {@link #getLastModified() last modified timestamp} of the file to now.
     *
     * @throws ResourceDoesNotExistException if the resource does not exist
     */
    void touch();

    /**
     * Provides access to file content. Calling any method on a file that does not {@link #exists() exist} will cause it
     * to be created.
     *
     * @return the file content
     */
    FileContent getContent();

    /**
     * RCopy this file to the specified folder if this file is newer than the destination.
     *
     * @param folder the folder to copy the file to
     *
     * @return a new resource (the current resource will no longer {@link #exists() exist}
     *
     * @throws ResourceDoesNotExistException if this resource no longer exists
     */
    Resource copyToIfNewer(Folder folder);
}
