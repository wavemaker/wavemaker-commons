package com.wavemaker.commons.i18n;

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
        MessageResource messageResource = MessageResource.create(resolvable.getCodes().toString());
        return localeMessageProvider.getLocaleMessage(defaultLocaleProvider.getLocales(), messageResource, messageResource.getMessageKey(), resolvable
                .getArguments());
    }
}