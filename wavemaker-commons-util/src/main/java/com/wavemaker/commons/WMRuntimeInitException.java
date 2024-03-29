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

/**
 * @author Simon Toens
 */
@SuppressWarnings("serial")
public class WMRuntimeInitException extends WMRuntimeException {

    private final String detailedMessage;

    private final Integer msgId;

    public WMRuntimeInitException(String message) {
        this(message, null);
    }

    public WMRuntimeInitException(String message, String detailedMessage) {
        this(message, detailedMessage, null);
    }

    public WMRuntimeInitException(String message, String detailedMessage, Throwable cause) {
        this(message, detailedMessage, null, cause);
    }

    public WMRuntimeInitException(String message, String detailedMessage, Integer msgId, Throwable cause) {
        super(message, cause);
        this.detailedMessage = detailedMessage;
        this.msgId = msgId;
    }

}
