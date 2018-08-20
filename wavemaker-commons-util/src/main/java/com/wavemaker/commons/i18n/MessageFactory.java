package com.wavemaker.commons.i18n;

/**
 * Created by prakashb on 7/7/18.
 */
public class MessageFactory {

    private MessageFactory(){
    }
    
    private static MessageFactory INSTANCE = new MessageFactory();

    private LocaleProvider localeProvider;
    private LocaleMessageProvider localeMessageProvider;

    public LocaleProvider getLocaleProvider() {
        if (localeProvider == null) {
            localeProvider = new DefaultLocaleProvider();
        }
        return localeProvider;
    }

    public void setLocaleProvider(LocaleProvider localeProvider) {
        this.localeProvider = localeProvider;
    }

    public LocaleMessageProvider getLocaleMessageProvider() {
        if (localeMessageProvider == null) {
            localeMessageProvider = new LocaleMessageProviderImpl();
        }
        return localeMessageProvider;
    }

    public void setLocaleMessageProvider(LocaleMessageProvider localeMessageProvider) {
        this.localeMessageProvider = localeMessageProvider;
    }
    
    public static MessageFactory getInstance() {
        return INSTANCE;
    }
}
