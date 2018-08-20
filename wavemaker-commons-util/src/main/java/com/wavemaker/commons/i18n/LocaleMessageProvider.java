package com.wavemaker.commons.i18n;

import com.wavemaker.commons.MessageResource;

/**
 * Created by prakashb on 19/7/18.
 */
public interface LocaleMessageProvider {

    String getLocaleMessage(String[] locales, MessageResource messageResource);

    String getLocaleMessage(String[] locales, MessageResource messageResource, Object[] args);
}
