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

        BeanSerializerModifier serializerModifier = new WMBeanSerializerModifer(this);

        setupContext.addBeanSerializerModifier(serializerModifier);
    }

    public boolean isFailOnCircularReferences() {
        return failOnCircularReferences;
    }
}
