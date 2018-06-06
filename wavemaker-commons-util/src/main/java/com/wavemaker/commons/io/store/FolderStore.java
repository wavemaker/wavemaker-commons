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

import com.wavemaker.commons.io.Folder;

/**
 * Store for a {@link Folder}.
 * 
 * @see StoredFolder
 * @author Phillip Webb
 */
public interface FolderStore extends ResourceStore {

    /**
     * List the contents of the folder.
     * 
     * @return the folder contents
     */
    Iterable<String> list();


    /**
     * Return the date/time that the file was last modified.
     *
     * @return the last modified timestamp
     */
    long getLastModified();

}