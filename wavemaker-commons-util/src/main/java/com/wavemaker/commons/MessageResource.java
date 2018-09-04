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
package com.wavemaker.commons;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wavemaker.commons.i18n.LocaleMessageProvider;
import com.wavemaker.commons.i18n.LocaleProvider;
import com.wavemaker.commons.i18n.MessageFactory;
import com.wavemaker.commons.i18n.ResourceConstraint;
import com.wavemaker.commons.util.ClassUtils;

/**
 * All known resources defined in our resource bundles. These constants are meant to be used when instantiating a
 * WM(Runtime)Exception. The underlying message can be accessed using getMessage/getDetailMessage.
 *
 * @author Simon Toens
 */
public class MessageResource {

    @ResourceConstraint(numArgs = 1, hasDetailMsg = false)
    public static final MessageResource INVALID_FIELD_VALUE = new MessageResource("com.wavemaker.runtime.json$InvalidFieldValue");

    @ResourceConstraint(numArgs = 1, hasDetailMsg = false)
    public static final MessageResource INVALID_OBJECT = new MessageResource("com.wavemaker.commons.json$InvalidObject");

    @ResourceConstraint(numArgs = 0, hasDetailMsg = false)
    public static final MessageResource CLASS_NOT_FOUND = new MessageResource("com.wavemaker.runtime.data$ClassNotFound");

    @ResourceConstraint(numArgs = 1, hasDetailMsg = false)
    public static final MessageResource UNKNOWN_FIELD_NAME = new MessageResource("com.wavemaker.runtime.data$UnknownFieldName");

    @ResourceConstraint(numArgs = 1, hasDetailMsg = false)
    public static final MessageResource UNEXPECTED_ERROR = new MessageResource("com.wavemaker.commons.json$UnexpectedError");

    @ResourceConstraint(numArgs = 1, hasDetailMsg = false)
    public static final MessageResource DYNAMIC_ERROR_MESSAGE = new MessageResource("com.wavemaker.commons.json$dynamicErrorMessage");

    @ResourceConstraint(numArgs = 1, hasDetailMsg = false)
    public static final MessageResource UNRECOGNIZED_FIELD = new MessageResource("com.wavemaker.commons.json$UnrecognizedField");

    @ResourceConstraint(numArgs = 1, hasDetailMsg = false)
    public static final MessageResource INVALID_JSON = new MessageResource("com.wavemaker.commons.json$InvalidJsonObject");

    @ResourceConstraint(numArgs = 1, hasDetailMsg = false)
    public static final MessageResource MESSAGE_NOT_READABLE = new MessageResource("com.wavemaker.commons.json$MessageNotReadable");

    @ResourceConstraint(numArgs = 1, hasDetailMsg = false)
    public static final MessageResource ACCESS_DENIED = new MessageResource("com.wavemaker.runtime.json$AccessDenied");

    @ResourceConstraint(numArgs = 1, hasDetailMsg = false)
    public static final MessageResource UTIL_FILEUTILS_PATHDNE = new MessageResource("com.wavemaker.commons.util$FileUtils_PathDNE");

    @ResourceConstraint(numArgs = 1, hasDetailMsg = false)
    public static final MessageResource UTIL_FILEUTILS_PATHNOTDIR = new MessageResource("com.wavemaker.commons.util$FileUtils_PathNotDir");

    @ResourceConstraint(numArgs = 0, hasDetailMsg = false)
    public static final MessageResource NULL_CLASS = new MessageResource("com.wavemaker.commons.util$NullClass");

    @ResourceConstraint(numArgs = 2, hasDetailMsg = false)
    public static final MessageResource UTIL_FILEUTILS_REACHEDROOT = new MessageResource("com.wavemaker.commons.util$FileUtils_ReachedRoot");

    @ResourceConstraint(numArgs = 0, hasDetailMsg = false)
    public static final MessageResource SEMAPHORE_ACQUISITION_TIMEOUT = new MessageResource("com.wavemaker.commons.util$SemaphoreAcquisitionTimeout");

    @ResourceConstraint(numArgs = 2, hasDetailMsg = false)
    public static final MessageResource TYPE_MAPPING_FAILURE = new MessageResource(
            "com.wavemaker.runtime.data$TypeMappingFailure");

    @ResourceConstraint(numArgs = 0, hasDetailMsg = false)
    public static final MessageResource DATABASE_CONNECTION_EXCEPTION = new MessageResource("com.wavemaker.runtime.data$DBConnectionException");

    @ResourceConstraint(numArgs = 2, hasDetailMsg = false)
    public static final MessageResource REST_SERVICE_INVOKE_FAILED = new MessageResource("com.wavemaker.runtime.$RestServiceInvokeFailed");

    @ResourceConstraint(numArgs = 1, hasDetailMsg = false)
    public static final MessageResource DATA_INTEGRITY_VIOALATION = new MessageResource("com.wavemaker.runtime.data$DataIntegrityViolation");

    @ResourceConstraint(numArgs = 0, hasDetailMsg = false)
    public static final MessageResource ENTITY_NOT_FOUND = new MessageResource("com.wavemaker.runtime.data$EntityNotFound");

    @ResourceConstraint(numArgs = 0, hasDetailMsg = false)
    public static final MessageResource RELATION_WITH_IDENTITY_COLUMN_NOT_POSSIBLE = new MessageResource("com.wavemaker.runtime.data$RelationWithIdentityColumnNotPossible");

    @ResourceConstraint(numArgs = 0, hasDetailMsg = false)
    public static final MessageResource UNIQUE_KEY_FOR_PRIMARY_KEY = new MessageResource(
            "com.wavemaker.runtime.data$UniqueKeyForPrimaryKey");

    @ResourceConstraint(numArgs = 1, hasDetailMsg = false)
    public static final MessageResource DUPLICATE_CONSTRAINT = new MessageResource(
            "com.wavemaker.runtime.data$DuplicateConstraint");

    @ResourceConstraint(numArgs = 2, hasDetailMsg = false)
    public static final MessageResource INVALID_TYPE_AS_UNIQUE = new MessageResource(
            "com.wavemaker.runtime.data$InvalidTypeAsUnique");

    @ResourceConstraint(numArgs = 1, hasDetailMsg = false)
    public static final MessageResource INVALID_INPUT = new MessageResource("com.wavemaker.runtime.data$InvalidInput");

    private static final Map<MessageResource, ResourceConstraint> annotations = new HashMap<>();

    static {
        List<Field> fields = ClassUtils.getPublicFields(MessageResource.class, MessageResource.class);
        populateAnnotationsMap(fields);
    }

    protected static void populateAnnotationsMap(List<Field> fields) {
        try {
            for (Field f : fields) {
                annotations.put((MessageResource) f.get(null), f.getAnnotation(ResourceConstraint.class));
            }
        } catch (IllegalAccessException ex) {
            throw new AssertionError(ex);
        }
    }

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
