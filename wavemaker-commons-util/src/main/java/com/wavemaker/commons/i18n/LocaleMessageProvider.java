package com.wavemaker.commons.i18n;

import com.wavemaker.commons.MessageResource;

/**
 * Created by prakashb on 19/7/18.
 */
public interface LocaleMessageProvider {

    String getMessage(String locale, MessageResource messageResource, Object[] args);
}
