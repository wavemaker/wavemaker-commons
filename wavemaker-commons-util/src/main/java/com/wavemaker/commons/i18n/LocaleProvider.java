package com.wavemaker.commons.i18n;

/**
 * Created by prakashb on 11/7/18.
 */
public interface LocaleProvider {

    /**
     * Will return the list of locales with descending priroty for the current request
     * @return
     */
    String[] getLocales();
}
