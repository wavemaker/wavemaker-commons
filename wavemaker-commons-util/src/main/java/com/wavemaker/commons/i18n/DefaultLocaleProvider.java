package com.wavemaker.commons.i18n;

/**
 * Created by prakashb on 11/7/18.
 */
public class DefaultLocaleProvider implements LocaleProvider {

    @Override
    public String getLocale() {
        return "en";
    }
}
