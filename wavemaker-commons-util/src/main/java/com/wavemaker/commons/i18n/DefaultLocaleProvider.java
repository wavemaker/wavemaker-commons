package com.wavemaker.commons.i18n;

/**
 * Created by prakashb on 11/7/18.
 */
public class DefaultLocaleProvider implements LocaleProvider {
    
    private final String[] defaultLocales = new String[] {"en"}; 

    @Override
    public String[] getLocales() {
        return defaultLocales;
    }
}
