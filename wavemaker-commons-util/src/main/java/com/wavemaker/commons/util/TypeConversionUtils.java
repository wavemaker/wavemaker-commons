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
package com.wavemaker.commons.util;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wavemaker.commons.json.deserializer.WMDateDeSerializer;
import com.wavemaker.commons.json.deserializer.WMLocalDateTimeDeSerializer;
import com.wavemaker.commons.wrapper.BooleanWrapper;
import com.wavemaker.commons.wrapper.ByteWrapper;
import com.wavemaker.commons.wrapper.CharacterWrapper;
import com.wavemaker.commons.wrapper.DateWrapper;
import com.wavemaker.commons.wrapper.DoubleWrapper;
import com.wavemaker.commons.wrapper.FloatWrapper;
import com.wavemaker.commons.wrapper.IntegerWrapper;
import com.wavemaker.commons.wrapper.LongWrapper;
import com.wavemaker.commons.wrapper.ShortWrapper;
import com.wavemaker.commons.wrapper.StringWrapper;

/**
 * @author Simon Toens
 */
public abstract class TypeConversionUtils {

    private TypeConversionUtils() {
    }

    private static final Map<String, Class<?>> PRIMITIVES = new HashMap<>(8);

    /**
     * List of primitive wrappers (Integer, etc), including Atomic numbers. All standard subclasses of Number are
     * included, and Boolean.
     */
    private static final Collection<Class<?>> PRIMITIVE_WRAPPERS = new HashSet<>(11);
    private static final Map<String, Class<?>> PRIMITIVE_ARRAYS = new HashMap<>(11);
    private static final Map<String, Class<?>> WM_PRIMITIVE_WRAPPERS = new HashMap<>(8);

    private static final Set<String> PRIMITIVE_DATA_TYPES = new HashSet<>();

    private static final Set<String> SERVLET_CLASSES = new HashSet<>();

    static {
        PRIMITIVES.put(boolean.class.getName(), boolean.class);
        PRIMITIVES.put(byte.class.getName(), byte.class);
        PRIMITIVES.put(char.class.getName(), char.class);
        PRIMITIVES.put(double.class.getName(), double.class);
        PRIMITIVES.put(float.class.getName(), float.class);
        PRIMITIVES.put(int.class.getName(), int.class);
        PRIMITIVES.put(long.class.getName(), long.class);
        PRIMITIVES.put(short.class.getName(), short.class);

        WM_PRIMITIVE_WRAPPERS.put(boolean.class.getName(), BooleanWrapper.class);
        WM_PRIMITIVE_WRAPPERS.put(Boolean.class.getName(), BooleanWrapper.class);
        WM_PRIMITIVE_WRAPPERS.put(byte.class.getName(), ByteWrapper.class);
        WM_PRIMITIVE_WRAPPERS.put(Byte.class.getName(), ByteWrapper.class);
        WM_PRIMITIVE_WRAPPERS.put(char.class.getName(), CharacterWrapper.class);
        WM_PRIMITIVE_WRAPPERS.put(Character.class.getName(), CharacterWrapper.class);
        WM_PRIMITIVE_WRAPPERS.put(double.class.getName(), DoubleWrapper.class);
        WM_PRIMITIVE_WRAPPERS.put(Double.class.getName(), DoubleWrapper.class);
        WM_PRIMITIVE_WRAPPERS.put(float.class.getName(), FloatWrapper.class);
        WM_PRIMITIVE_WRAPPERS.put(Float.class.getName(), FloatWrapper.class);
        WM_PRIMITIVE_WRAPPERS.put(int.class.getName(), IntegerWrapper.class);
        WM_PRIMITIVE_WRAPPERS.put(Integer.class.getName(), IntegerWrapper.class);
        WM_PRIMITIVE_WRAPPERS.put(long.class.getName(), LongWrapper.class);
        WM_PRIMITIVE_WRAPPERS.put(Long.class.getName(), LongWrapper.class);
        WM_PRIMITIVE_WRAPPERS.put(short.class.getName(), ShortWrapper.class);
        WM_PRIMITIVE_WRAPPERS.put(Short.class.getName(), ShortWrapper.class);
        WM_PRIMITIVE_WRAPPERS.put("String", StringWrapper.class);
        WM_PRIMITIVE_WRAPPERS.put(String.class.getName(), StringWrapper.class);
        WM_PRIMITIVE_WRAPPERS.put("Date", DateWrapper.class);
        WM_PRIMITIVE_WRAPPERS.put(Date.class.getName(), DateWrapper.class);

        PRIMITIVE_ARRAYS.put(boolean[].class.getName(), boolean[].class);
        PRIMITIVE_ARRAYS.put(byte[].class.getName(), byte[].class);
        PRIMITIVE_ARRAYS.put(char[].class.getName(), char[].class);
        PRIMITIVE_ARRAYS.put(double[].class.getName(), double[].class);
        PRIMITIVE_ARRAYS.put(float[].class.getName(), float[].class);
        PRIMITIVE_ARRAYS.put(int[].class.getName(), int[].class);
        PRIMITIVE_ARRAYS.put(long[].class.getName(), long[].class);
        PRIMITIVE_ARRAYS.put(short[].class.getName(), short[].class);

        PRIMITIVE_WRAPPERS.add(AtomicInteger.class);
        PRIMITIVE_WRAPPERS.add(AtomicLong.class);
        PRIMITIVE_WRAPPERS.add(BigDecimal.class);
        PRIMITIVE_WRAPPERS.add(BigInteger.class);
        PRIMITIVE_WRAPPERS.add(Boolean.class);
        PRIMITIVE_WRAPPERS.add(Byte.class);
        PRIMITIVE_WRAPPERS.add(Character.class);
        PRIMITIVE_WRAPPERS.add(Double.class);
        PRIMITIVE_WRAPPERS.add(Float.class);
        PRIMITIVE_WRAPPERS.add(Integer.class);
        PRIMITIVE_WRAPPERS.add(Long.class);
        PRIMITIVE_WRAPPERS.add(Short.class);

        PRIMITIVE_DATA_TYPES.add("int");
        PRIMITIVE_DATA_TYPES.add("Integer");
        PRIMITIVE_DATA_TYPES.add("java.lang.Integer");

        PRIMITIVE_DATA_TYPES.add("String");
        PRIMITIVE_DATA_TYPES.add("java.lang.String");

        PRIMITIVE_DATA_TYPES.add("float");
        PRIMITIVE_DATA_TYPES.add("Float");
        PRIMITIVE_DATA_TYPES.add("java.lang.Float");

        PRIMITIVE_DATA_TYPES.add("boolean");
        PRIMITIVE_DATA_TYPES.add("Boolean");
        PRIMITIVE_DATA_TYPES.add("java.lang.Boolean");

        PRIMITIVE_DATA_TYPES.add("char");
        PRIMITIVE_DATA_TYPES.add("Character");
        PRIMITIVE_DATA_TYPES.add("java.lang.Character");

        PRIMITIVE_DATA_TYPES.add("byte");
        PRIMITIVE_DATA_TYPES.add("Byte");
        PRIMITIVE_DATA_TYPES.add("java.lang.Byte");

        PRIMITIVE_DATA_TYPES.add("short");
        PRIMITIVE_DATA_TYPES.add("Short");
        PRIMITIVE_DATA_TYPES.add("java.lang.Short");

        PRIMITIVE_DATA_TYPES.add("long");
        PRIMITIVE_DATA_TYPES.add("Long");
        PRIMITIVE_DATA_TYPES.add("java.lang.Long");

        PRIMITIVE_DATA_TYPES.add("double");
        PRIMITIVE_DATA_TYPES.add("Double");
        PRIMITIVE_DATA_TYPES.add("java.lang.Double");

        PRIMITIVE_DATA_TYPES.add("Date");
        PRIMITIVE_DATA_TYPES.add("java.util.Date");

        PRIMITIVE_DATA_TYPES.add("java.math.BigDecimal");
        PRIMITIVE_DATA_TYPES.add("java.math.BigInteger");
        PRIMITIVE_DATA_TYPES.add("java.sql.Timestamp");
        PRIMITIVE_DATA_TYPES.add("java.sql.Time");
        PRIMITIVE_DATA_TYPES.add("java.sql.Date");

        PRIMITIVE_DATA_TYPES.add("org.springframework.data.domain.Pageable");

        //servlet related classes...
        SERVLET_CLASSES.add("ServletContext");
        SERVLET_CLASSES.add("HttpServletRequest");
        SERVLET_CLASSES.add("HttpServletResponse");
        SERVLET_CLASSES.add("HttpSession");
        SERVLET_CLASSES.add("MultipartHttpServletRequest");

        SERVLET_CLASSES.add("javax.servlet.ServletContext");
        SERVLET_CLASSES.add("javax.servlet.http.HttpServletRequest");
        SERVLET_CLASSES.add("javax.servlet.http.HttpServletResponse");
        SERVLET_CLASSES.add("javax.servlet.http.HttpSession");
        SERVLET_CLASSES.add("org.springframework.web.multipart.MultipartHttpServletRequest");
    }

    public static boolean isServletClass(String className) {
        return SERVLET_CLASSES.contains(className);
    }

    public static boolean isPrimitive(String dataType) {
        return PRIMITIVE_DATA_TYPES.contains(dataType);
    }

    public static Class<?> primitiveForName(String className) {
        return PRIMITIVES.get(className);
    }

    public static Class<?> primitiveArraysForName(String className) {
        return PRIMITIVE_ARRAYS.get(className);
    }

    /**
     * Method to check Multi-Dimensional Array for import.
     * @param className
     * @return
     */
    public static String checkAndReturnForMultiDimensionalArrays(String className) {
        // Matches the pattern like [[Ljava,lang.String
        Pattern pattern = Pattern.compile("(\\[)*[L][\\w\\W]*");
        final Matcher matcher = pattern.matcher(className);
        if (matcher.matches()) {
            // removes for e.g. [[L from [[Ljava,lang.String
            return className.replaceFirst("(\\[)*[L]", "");
        }
        return null;
    }

    public static boolean checkPrimitiveAndPrimitiveArrays(String className) {
        return TypeConversionUtils.primitiveArraysForName(className) == null &&
                TypeConversionUtils.primitiveForName(className) == null;
    }


    public static Class<?> primitiveWrapperClassByName(String className) {
        for (Class klass : PRIMITIVE_WRAPPERS) {
            if (klass.getSimpleName().equals(className)) {
                return klass;
            }
        }
        return null;
    }

    public static Class<?> wmPrimitiveWrapperClassByName(String className) {
        return WM_PRIMITIVE_WRAPPERS.get(className);
    }

    public static boolean isPrimitiveOrEnum(Type type) {
        if (type instanceof Class && !((Class) type).isArray()) {
            Class klass = (Class) type;
            if (isPrimitive(klass.getName()) || klass.isEnum()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true iff the Class clazz represents a primitive (boolean, int) or a primitive wrapper (Integer),
     * including Big{Integer,Decimal} and Atomic{Integer,Long}. Also, Strings and Dates are included.
     *
     * @param clazz
     * @return
     */
    public static boolean isPrimitiveOrWrapper(Class<?> clazz) {

        if (clazz.isPrimitive()) {
            return true;
        }

        if (clazz.equals(String.class)) {
            return true;
        }

        if (Date.class.isAssignableFrom(clazz)) {
            return true;
        }

        if (LocalDateTime.class.isAssignableFrom(clazz)) {
            return true;
        }

        return PRIMITIVE_WRAPPERS.contains(clazz);

    }

    /**
     * Return true iff the parameter is an Array or a Collection.
     *
     * @param clazz
     * @return
     */
    public static boolean isArray(Class<?> clazz) {

        return clazz != null && (Collection.class.isAssignableFrom(clazz) || clazz.isArray());
    }

    /**
     * Return true iff the parameter is a Map.
     *
     * @param clazz
     * @return
     */
    public static boolean isMap(Class<?> clazz) {

        return clazz != null && Map.class.isAssignableFrom(clazz);
    }

    public static Object fromString(Class<?> type, String s) {
        return fromString(type, s, false);
    }

    public static Object fromString(Class<?> type, String s, boolean isList) {

        if (isList || !isPrimitiveOrWrapper(type)) {
            if (s == null) {
                return null;
            }
            ObjectLiteralParser p = new ObjectLiteralParser(s, type);
            return p.parse();
        }

        if (s == null) {
            return null;
        } else if (type == AtomicInteger.class) {
            return null;
        } else if (type == AtomicLong.class) {
            return null;
        } else if (type == BigDecimal.class) {
            return new BigDecimal(s);
        } else if (type == BigInteger.class) {
            return new BigDecimal(s);
        } else if (type == Boolean.class || type == boolean.class) {
            return Boolean.valueOf(s);
        } else if (type == Byte.class || type == byte.class) {
            return Byte.valueOf(s);
        } else if (type == Date.class) {
            if (StringUtils.isNumber(s)) {
                return new Date(Long.parseLong(s));
            } else {
                throw new IllegalArgumentException("Unable to convert " + s + " to " + Date.class.getName());
            }
        } else if (type == java.sql.Date.class) {
            return WMDateDeSerializer.getDate(s);
        } else if (type == Time.class) {
            return WMDateDeSerializer.getDate(s);
        } else if (type == Timestamp.class) {
            if (StringUtils.isNumber(s)) {
                return new Timestamp(Long.parseLong(s));
            } else {
                throw new IllegalArgumentException("Unable to convert " + s + " to " + Timestamp.class.getName());
            }
        } else if (type == LocalDateTime.class) {
            return WMLocalDateTimeDeSerializer.getLocalDateTime(s);
        } else if (type == Double.class || type == double.class) {
            return Double.valueOf(s);
        } else if (type == Float.class || type == float.class) {
            return Float.valueOf(s);
        } else if (type == Integer.class || type == int.class) {
            return Integer.valueOf(s);
        } else if (type == Long.class || type == long.class) {
            return Long.valueOf(s);
        } else if (type == Short.class || type == short.class) {
            return Short.valueOf(s);
        } else if (type == String.class || type == StringBuffer.class) {
            return s;
        } else if (type == Character.class || type == char.class) {
            return s.charAt(0);
        } else {
            throw new AssertionError("Unable to convert \"" + s + "\" to " + type + " - unknown type: " + type);
        }
    }

    public static String getValueString(Class<?> type, String s) {

        if (s == null) {
            return "null";
        } else if (type == String.class || type == StringBuffer.class) {
            return "'" + s + "'";
        } else if (type == Date.class || type == java.sql.Date.class || type == Timestamp.class || type == Time.class) {
            return "'" + s + "'";
        } else {
            return s;
        }
    }

    public static boolean primitivesMatch(Class<?> p1, Class<?> p2) {

        if (!p1.isPrimitive() && !p2.isPrimitive()) {
            return false;
        }

        if (compare(p1, p2, Boolean.class, boolean.class)) {
            return true;
        }
        if (compare(p1, p2, Byte.class, byte.class)) {
            return true;
        }
        if (compare(p1, p2, Double.class, double.class)) {
            return true;
        }
        if (compare(p1, p2, Float.class, float.class)) {
            return true;
        }
        if (compare(p1, p2, Integer.class, int.class)) {
            return true;
        }
        if (compare(p1, p2, Long.class, long.class)) {
            return true;
        }
        if (compare(p1, p2, Short.class, short.class)) {
            return true;
        }
        return compare(p1, p2, Character.class, char.class);

    }

    private static boolean compare(Class<?> p1, Class<?> p2, Class<?> t1, Class<?> t2) {

        if (p1 == t1 && p2 == t2) {
            return true;
        }

        return p1 == t2 && p2 == t1;

    }
}