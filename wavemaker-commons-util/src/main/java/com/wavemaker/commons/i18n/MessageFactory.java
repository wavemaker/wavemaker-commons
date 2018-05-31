package com.wavemaker.commons.i18n;

/**
 * Created by prakashb on 7/7/18.
 */
public class MessageFactory {

    private static LocaleProvider localeProvider = new DefaultLocaleProvider();
    private static LocaleMessageProvider localeMessageProvider = new LocaleMessageProviderImpl();

    public static LocaleProvider getLocaleProvider() {
        return localeProvider;
    }

    public static void setLocaleProvider(LocaleProvider localeProvider) {
        MessageFactory.localeProvider = localeProvider;
    }

    public static LocaleMessageProvider getLocaleMessageProvider() {
        return localeMessageProvider;
    }

    public static void setLocaleMessageProvider(LocaleMessageProvider localeMessageProvider) {
        MessageFactory.localeMessageProvider = localeMessageProvider;
    }
}
