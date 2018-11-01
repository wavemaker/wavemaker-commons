package com.wavemaker.commons.i18n;

import java.util.Map;

public class FinalLocaleData {
    private Map<String, String> messages;
    private Map<String, String> formats;
    private Map<String, String> files;
    private Map<String, Map<String, String>> prefabMessages;

    public FinalLocaleData() {
    }

    public FinalLocaleData(Map<String, String> messages, Map<String, String> formats, Map<String, String> files, Map<String, Map<String, String>> prefabMessages) {
        this.messages = messages;
        this.formats = formats;
        this.files = files;
        this.prefabMessages = prefabMessages;
    }

    public Map<String, Map<String, String>> getPrefabMessages() {
        return prefabMessages;
    }

    public void setPrefabMessages(Map<String, Map<String, String>> prefabMessages) {
        this.prefabMessages = prefabMessages;
    }

    public Map<String, String> getFiles() {
        return files;
    }

    public void setFiles(Map<String, String> files) {
        this.files = files;
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
