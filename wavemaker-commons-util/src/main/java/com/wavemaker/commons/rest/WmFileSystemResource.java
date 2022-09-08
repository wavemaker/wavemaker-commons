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
package com.wavemaker.commons.rest;

import java.io.File;

import org.springframework.core.io.FileSystemResource;

/**
 * Created by srujant on 14/2/17.
 */
public class WmFileSystemResource extends FileSystemResource {

    private String contentType;

    public WmFileSystemResource(File file, String contentType) {
        super(file);
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

}


