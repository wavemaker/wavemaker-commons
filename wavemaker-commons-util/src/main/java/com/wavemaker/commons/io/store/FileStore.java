/**
 * Copyright (C) 2020 WaveMaker, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wavemaker.commons.io.store;

import java.io.InputStream;
import java.io.OutputStream;

import com.wavemaker.commons.io.File;

/**
 * Store for a single {@link File}.
 * 
 * @see StoredFile
 * 
 * @author Phillip Webb
 */
public interface FileStore extends ResourceStore {

    /**
     * Access the file content as an input stream.
     * 
     * @return an input stream to read content
     */
    InputStream getInputStream();

    /**
     * Access the file content as an output stream.
     * 
     * @return an output stream to write content
     */
    OutputStream getOutputStream();

    /**
     * @param append if <code>true</code>, open the file OutputStream in append mode, otherwise as new file
     * Access the file content as an output stream.
     *
     * @return an output stream to write content
     */
    OutputStream getOutputStream(boolean append);

    /**
     * Return the size of the file.
     * 
     * @return the file size
     */
    long getSize();

    /**
     * Return the date/time that the file was last modified.
     * 
     * @return the last modified timestamp
     */
    long getLastModified();

    /**
     * Touch the file to update the {@link #getLastModified()} date.
     */
    void touch();
}