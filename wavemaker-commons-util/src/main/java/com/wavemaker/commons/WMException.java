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
package com.wavemaker.commons;

/**
 * @author Simon Toens
 */
public abstract class WMException extends Exception implements WMCommonException {

    private static final long serialVersionUID = 1L;

    private MessageResourceHolder messageResourceHolder = new MessageResourceHolder();

    public WMException(Throwable cause) {
        super(cause);
    }

    public WMException(MessageResource resource, Throwable cause) {
        this(cause);
        messageResourceHolder.setMessageResource(resource);
    }

    public WMException(MessageResource resource, Object... args) {
        messageResourceHolder.setMessageResource(resource);
        messageResourceHolder.setArgs(args);
    }

    public WMException(MessageResource resource, Throwable cause, Object... args) {
        this(cause);
        messageResourceHolder.setMessageResource(resource);
        messageResourceHolder.setArgs(args);
    }

    @Override
    public MessageResourceHolder getMessageResourceHolder() {
        return messageResourceHolder;
    }

    public String getMessage() {
        String message;
        MessageResource messageResource = messageResourceHolder.getMessageResource();
        if (messageResource != null) {
            message = messageResource.getMessage(messageResourceHolder.getArgs());
        } else {
            message = messageResourceHolder.getMessage();
        }
        return (message == null) ? "" : message;
    }
}
