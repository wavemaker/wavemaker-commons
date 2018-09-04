package com.wavemaker.commons.i18n;

import java.util.Collections;
import java.util.Map;

/**
 * Created by srujant on 3/9/18.
 */
public class LocaleData {

    private Map<String, String> messages = Collections.emptyMap();
    private Map<String, String> formats = Collections.emptyMap();

    public LocaleData() {
    }

    public LocaleData(Map<String, String> messages, Map<String, String> formats) {
        this.messages = messages;
        this.formats = formats;
    }

    public Map<String, String> getMessages() {
        return messages;
    }

    public void setMessages(Map<String, String> messages) {
        this.messages = messages;
    }

    public Map<String, String> getFormats() {
        return formats;
    }

    public void setFormats(Map<String, String> formats) {
        this.formats = formats;
    }
}
