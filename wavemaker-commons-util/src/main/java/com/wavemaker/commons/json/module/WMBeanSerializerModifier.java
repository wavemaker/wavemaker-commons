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
package com.wavemaker.commons.json.module;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanSerializer;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 3/10/18
 */
public class WMBeanSerializerModifier extends BeanSerializerModifier {

    private final WMJacksonModule module;

    public WMBeanSerializerModifier(WMJacksonModule module) {
        this.module = module;
    }

    @Override
    public JsonSerializer<?> modifySerializer(
            final SerializationConfig config, final BeanDescription beanDesc, final JsonSerializer<?> serializer) {
        if (serializer instanceof BeanSerializer) {
            return new CircularLoopHandlingSerializer<>((BeanSerializer) serializer,
                    module.isFailOnCircularReferences());
        } else {
            return serializer;
        }
    }
}
