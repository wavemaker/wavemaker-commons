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

/**
 * Context for {@link ResourceFilter}s.
 * 
 * @author Phillip Webb
 */
public interface ResourceFilterContext {

    /**
     * Returns the source folder that triggered the filter. Useful when matching paths.
     * 
     * @return the source folder
     * @see Resources#getSource()
     */
    Folder getSource();
}
