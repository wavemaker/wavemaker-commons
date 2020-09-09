/**
 * Copyright (C) 2020 WaveMaker, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
