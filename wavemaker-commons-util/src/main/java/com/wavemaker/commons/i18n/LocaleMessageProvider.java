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

import com.wavemaker.commons.MessageResource;

/**
 * Created by prakashb on 19/7/18.
 */
public interface LocaleMessageProvider {

    String getLocaleMessage(String[] locales, MessageResource messageResource, String defaultMessage);

    String getLocaleMessage(String[] locales, MessageResource messageResource, String defaultMessage, Object[] args);
}
