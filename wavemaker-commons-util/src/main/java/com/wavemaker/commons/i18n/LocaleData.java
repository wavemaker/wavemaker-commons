/*******************************************************************************
 * Copyright (C) 2022-2023 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.wavemaker.commons.i18n;

import java.util.Collections;
import java.util.Map;

/**
 * Created by srujant on 3/9/18.
 */
public class LocaleData {

    private Map<String, String> messages = Collections.emptyMap();
    private Map<String, String> formats = Collections.emptyMap();
    private Map<String, String> files = Collections.emptyMap();
    private Map<String, Map<String, String>> prefabMessages = Collections.emptyMap();

    public LocaleData() {
    }

    public LocaleData(Map<String, String> messages, Map<String, String> formats) {
        this.messages = messages;
        this.formats = formats;
    }

    public LocaleData(Map<String, String> messages, Map<String, String> formats, Map<String, Map<String, String>> prefabMessages) {
        this.messages = messages;
        this.formats = formats;
        this.prefabMessages = prefabMessages;
    }

    public LocaleData(Map<String, String> messages, Map<String, String> formats, Map<String, String> files, Map<String, Map<String, String>> prefabMessages) {
        this.messages = messages;
        this.formats = formats;
        this.files = files;
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

    public Map<String, Map<String, String>> getPrefabMessages() {
        return prefabMessages;
    }

    public void setPrefabMessages(Map<String, Map<String, String>> prefabMessages) {
        this.prefabMessages = prefabMessages;
    }
}
