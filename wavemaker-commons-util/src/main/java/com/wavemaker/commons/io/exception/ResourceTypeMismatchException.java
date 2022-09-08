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
package com.wavemaker.commons.io.exception;

import com.wavemaker.commons.MessageResource;
import com.wavemaker.commons.io.Resource;
import com.wavemaker.commons.io.ResourcePath;

/**
 * Exception thrown when a {@link Resource} is requested as a particular type but an existing resource of another type
 * already exists.
 *
 * @author Phillip Webb
 */
public class ResourceTypeMismatchException extends ResourceException {

    private static final long serialVersionUID = 1L;

    private final ResourcePath path;

    /**
     * Create a new {@link ResourceTypeMismatchException} instance.
     *
     * @param path     the path begin accessed
     * @param actual   the actual type
     * @param expected the expected type
     */
    public ResourceTypeMismatchException(ResourcePath path, boolean accessingAsFolder) {
        super(MessageResource.create("com.wavemaker.commons.unable.to.access.resource"), path, accessingAsFolder ? "folder" : "file");
        this.path = path;
    }

    public ResourcePath getPath() {
        return this.path;
    }
}
