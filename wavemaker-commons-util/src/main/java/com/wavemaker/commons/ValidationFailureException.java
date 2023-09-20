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

package com.wavemaker.commons;

import org.springframework.http.HttpStatus;

/**
 * @author Uday Shankar
 **/
public class ValidationFailureException extends WMRuntimeException {

    private HttpStatus httpStatus;

    public ValidationFailureException(String message) {
        this(MessageResource.create("com.wavemaker.validation.failed"), message);
    }

    public ValidationFailureException(MessageResource resource, Object... args) {
        this(resource, HttpStatus.BAD_REQUEST, args);
    }

    public ValidationFailureException(MessageResource resource, HttpStatus httpStatus, Object... args) {
        super(resource, args);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
