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
