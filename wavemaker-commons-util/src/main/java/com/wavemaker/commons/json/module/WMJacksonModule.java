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

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 3/10/18
 */
public class WMJacksonModule extends Module {

    private final boolean failOnCircularReferences;

    public WMJacksonModule(final boolean failOnCircularReferences) {
        this.failOnCircularReferences = failOnCircularReferences;
    }


    @Override
    public String getModuleName() {
        return "wm_module";
    }

    @Override
    public Version version() {
        return PackageVersion.VERSION;
    }

    @Override
    public void setupModule(final SetupContext setupContext) {

        BeanSerializerModifier serializerModifier = new WMBeanSerializerModifier(this);

        setupContext.addBeanSerializerModifier(serializerModifier);
    }

    public boolean isFailOnCircularReferences() {
        return failOnCircularReferences;
    }
}
