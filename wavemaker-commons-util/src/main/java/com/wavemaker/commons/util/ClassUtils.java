/**
 * Copyright © 2013 - 2017 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wavemaker.commons.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.wavemaker.commons.WMRuntimeException;

/**
 * Utility methods that work with Class instances (see ObjectUtils, as well).
 * 
 * @author Matt Small
 */
public class ClassUtils {

    public static Object newInstance(Class<?> c) {
        try {
            return c.newInstance();
        } catch (InstantiationException ex) {
            String s = ex.getMessage();
            if (s == null || s.equals("")) {
                s = "Failed to instantiate " + c.getName();
            }
            throw new WMRuntimeException(s, ex);
        } catch (IllegalAccessException ex) {
            throw new WMRuntimeException(ex);
        }
    }

    /**
     * Get all public methods of a class, except for methods contained in Object.
     */
    public static List<Method> getPublicMethods(Class<?> c) {

        Method[] allMethods = c.getMethods();
        List<Method> ret = new ArrayList<>(allMethods.length);

        for (Method method : allMethods) {
            if (!method.getDeclaringClass().equals(Object.class)) {
                ret.add(method);
            }
        }

        return ret;
    }

    public static List<Field> getPublicFields(Class<?> c) {
        return getPublicFields(c, null);
    }

    public static List<Field> getPublicFields(Class<?> c, Class<?> fieldType) {

        List<Field> rtn = new ArrayList<>();

        Field[] allFields = c.getFields();

        for (Field field : allFields) {
            if (fieldType != null) {
                if (!fieldType.isAssignableFrom(field.getType())) {
                    continue;
                }
            }
            rtn.add(field);
        }
        return rtn;
    }

    public static String getPropertyGetterName(String propertyName) {
        return "get" + StringUtils.upperCaseFirstLetter(propertyName);
    }

    public static String getAltPropertyGetterName(String propertyName) {
        return "is" + StringUtils.upperCaseFirstLetter(propertyName);
    }

    public static String getPropertySetterName(String propertyName) {
        return "set" + StringUtils.upperCaseFirstLetter(propertyName);
    }

    public static boolean isPrimitivesWrapper(Type type) {
        if (!(type instanceof Class) || ((Class) type).isArray()) {
            return false;
        }
        Class<?> klass = (Class) type;
        List<PropertyDescriptor> propertyDescriptors = getPropertyDescriptors(klass);
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            Class<?> propertyType = propertyDescriptor.getPropertyType();
            if(!TypeConversionUtils.isPrimitive(propertyType.getName()) && !propertyType.isEnum()) {
                return false;
            }
        }
        return true;
    }

    public static List<PropertyDescriptor> getPropertyDescriptors(Class klass) {
        try {
            PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(klass).getPropertyDescriptors();
            List<PropertyDescriptor> propertyDescriptorList = new ArrayList<>();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                Method method = propertyDescriptor.getReadMethod();
                if (method == null) {
                    method = propertyDescriptor.getWriteMethod();
                }
                if(Object.class.equals(method.getDeclaringClass())) {
                    continue;
                }
                propertyDescriptorList.add(propertyDescriptor);
            }
            return propertyDescriptorList;
        } catch (IntrospectionException e) {
            throw new WMRuntimeException("Failed to introspect class [" + klass + "]", e);
        }
    }

    private ClassUtils() {
    }
}