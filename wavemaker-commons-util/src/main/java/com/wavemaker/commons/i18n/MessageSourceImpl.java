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
package com.wavemaker.commons.i18n;

import java.util.Arrays;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;

import com.wavemaker.commons.MessageResource;

public class MessageSourceImpl implements MessageSource {

    private LocaleMessageProviderImpl localeMessageProvider = new LocaleMessageProviderImpl();
    private DefaultLocaleProvider defaultLocaleProvider = new DefaultLocaleProvider();

    @Override
    public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        MessageResource messageResource = MessageResource.create(code);
        return localeMessageProvider.getLocaleMessage(defaultLocaleProvider.getLocales(), messageResource, defaultMessage, args);
    }

    @Override
    public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
        MessageResource messageResource = MessageResource.create(code);
        return localeMessageProvider.getLocaleMessage(defaultLocaleProvider.getLocales(), messageResource, code, args);
    }

    @Override
    public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
        MessageResource messageResource = MessageResource.create(Arrays.toString(resolvable.getCodes()));
        return localeMessageProvider.getLocaleMessage(defaultLocaleProvider.getLocales(), messageResource, messageResource.getMessageKey(), resolvable
                .getArguments());
    }
}