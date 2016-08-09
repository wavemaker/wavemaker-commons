package com.wavemaker.runtime.data.util;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.commons.lang3.StringUtils;

import com.wavemaker.runtime.data.Types;

/**
 * @author <a href="mailto:anusha.dharmasagar@wavemaker.com">Anusha Dharmasagar</a>
 * @since 27/5/16
 */
public class TypeMapBuilder {


    public static HashMap<String, Types> buildFieldNameVsTypeMap(String className) {
        return _buildFieldNameVsTypeMap(className, "", true);
    }


    public static HashMap<String, Types> _buildFieldNameVsTypeMap(String entityName, String fieldPrefix, boolean loopOnce) {
        try {
            Class entity = Class.forName(entityName);
            HashMap<String, Types> fieldNameVsTypeMap = new LinkedHashMap<>();
            for (Field field : entity.getDeclaredFields()) {
                Class fieldType = field.getType();
                String fieldName = field.getName();
                if (Collection.class != fieldType) {
                    String typeClassName = fieldType.getName();
                    Types types = Types.valueFor(typeClassName);
                    if (types != null) {
                        if (StringUtils.isNotBlank(fieldPrefix)) {
                            fieldName = fieldPrefix + "." + fieldName;
                        }
                        fieldNameVsTypeMap.put(fieldName, types);
                    } else if (loopOnce) {
                        fieldNameVsTypeMap.putAll(_buildFieldNameVsTypeMap(typeClassName, fieldName, false));
                    }
                }
            }
            return fieldNameVsTypeMap;
        } catch (Exception e) {
            throw new RuntimeException("error while mapping fieldNames with typeNames", e);
        }
    }
}