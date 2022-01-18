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

import com.wavemaker.commons.i18n.LocaleMessageProvider;
import com.wavemaker.commons.i18n.LocaleProvider;
import com.wavemaker.commons.i18n.MessageFactory;

/**
 * All known resources defined in our resource bundles. These constants are meant to be used when instantiating a
 * WM(Runtime)Exception. The underlying message can be accessed using getMessage/getDetailMessage.
 *
 * @author Simon Toens
 */
public class MessageResource {

    public static final MessageResource INVALID_FIELD_VALUE = new MessageResource("com.wavemaker.runtime.json$InvalidFieldValue");

    public static final MessageResource INVALID_OBJECT = new MessageResource("com.wavemaker.commons.json$InvalidObject");

    public static final MessageResource CLASS_NOT_FOUND = new MessageResource("com.wavemaker.runtime.data$ClassNotFound");

    public static final MessageResource UNKNOWN_FIELD_NAME = new MessageResource("com.wavemaker.runtime.data$UnknownFieldName");

    public static final MessageResource UNEXPECTED_ERROR = new MessageResource("com.wavemaker.commons.json$UnexpectedError");

    public static final MessageResource DYNAMIC_ERROR_MESSAGE = new MessageResource("com.wavemaker.commons.json$dynamicErrorMessage");

    public static final MessageResource UNRECOGNIZED_FIELD = new MessageResource("com.wavemaker.commons.json$UnrecognizedField");

    public static final MessageResource INVALID_JSON = new MessageResource("com.wavemaker.commons.json$InvalidJsonObject");

    public static final MessageResource MESSAGE_NOT_READABLE = new MessageResource("com.wavemaker.commons.json$MessageNotReadable");

    public static final MessageResource ACCESS_DENIED = new MessageResource("com.wavemaker.runtime.json$AccessDenied");

    public static final MessageResource UTIL_FILEUTILS_PATHDNE = new MessageResource("com.wavemaker.commons.util$FileUtils_PathDNE");

    public static final MessageResource UTIL_FILEUTILS_PATHNOTDIR = new MessageResource("com.wavemaker.commons.util$FileUtils_PathNotDir");

    public static final MessageResource NULL_CLASS = new MessageResource("com.wavemaker.commons.util$NullClass");

    public static final MessageResource UTIL_FILEUTILS_REACHEDROOT = new MessageResource("com.wavemaker.commons.util$FileUtils_ReachedRoot");

    public static final MessageResource SEMAPHORE_ACQUISITION_TIMEOUT = new MessageResource("com.wavemaker.commons.util$SemaphoreAcquisitionTimeout");

    public static final MessageResource TYPE_MAPPING_FAILURE = new MessageResource("com.wavemaker.runtime.data$TypeMappingFailure");

    public static final MessageResource DATABASE_CONNECTION_EXCEPTION = new MessageResource("com.wavemaker.runtime.data$DBConnectionException");

    public static final MessageResource REST_SERVICE_INVOKE_FAILED = new MessageResource("com.wavemaker.runtime.$RestServiceInvokeFailed");

    public static final MessageResource DATA_INTEGRITY_VIOALATION = new MessageResource("com.wavemaker.runtime.data$DataIntegrityViolation");

    public static final MessageResource ENTITY_NOT_FOUND = new MessageResource("com.wavemaker.runtime.data$EntityNotFound");

    public static final MessageResource RELATION_WITH_IDENTITY_COLUMN_NOT_POSSIBLE = new MessageResource("com.wavemaker.runtime.data$RelationWithIdentityColumnNotPossible");

    public static final MessageResource UNIQUE_KEY_FOR_PRIMARY_KEY = new MessageResource("com.wavemaker.runtime.data$UniqueKeyForPrimaryKey");

    public static final MessageResource DUPLICATE_CONSTRAINT = new MessageResource("com.wavemaker.runtime.data$DuplicateConstraint");

    public static final MessageResource INVALID_TYPE_AS_UNIQUE = new MessageResource("com.wavemaker.runtime.data$InvalidTypeAsUnique");

    public static final MessageResource INVALID_INPUT = new MessageResource("com.wavemaker.runtime.data$InvalidInput");

    private final String key;

    protected MessageResource(String key) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        this.key = key;
    }

    public String getMessageKey() {
        return this.key;
    }
    
    public static MessageResource create(String messageKey) {
        return new MessageResource(messageKey);
    }

    public String getMessage(Object... args) {
        LocaleProvider localeProvider = MessageFactory.getInstance().getLocaleProvider();
        String[] locales = localeProvider.getLocales();
        LocaleMessageProvider localeMessageProvider = MessageFactory.getInstance().getLocaleMessageProvider();
        return localeMessageProvider.getLocaleMessage(locales, this, this.key, args);
    }

    public String getMessageWithPlaceholders() {
        LocaleProvider localeProvider = MessageFactory.getInstance().getLocaleProvider();
        String[] locales = localeProvider.getLocales();
        LocaleMessageProvider localeMessageProvider = MessageFactory.getInstance().getLocaleMessageProvider();
        return localeMessageProvider.getLocaleMessage(locales, this, this.key);
    }

}
