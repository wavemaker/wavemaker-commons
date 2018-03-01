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

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.wavemaker.commons.WMRuntimeException;

/**
 * @author Simon Toens
 */
public abstract class ObjectUtils {

    // only use for logging - not guaranteed to be unique
    public static String getId(Object o) {
        if (o == null) {
            return "null";
        }

        return o.getClass().getName() + "@" + System.identityHashCode(o);
    }

    public static boolean isNullOrEmpty(String s) {
        if (s == null) {
            return true;
        }

        if (s.trim().length() == 0) {
            return true;
        }

        return false;
    }

    public static boolean isNullOrEmpty(Object[] o) {
        if (o == null) {
            return true;
        }

        if (o.length == 0) {
            return true;
        }

        return false;
    }

    public static boolean isNullOrEmpty(List<?> l) {
        if (l == null) {
            return true;
        }

        if (l.isEmpty()) {
            return true;
        }

        return false;
    }

    /**
     * Return new array that contains the elements of all input arrays.
     */
    public static Object[] addArrays(Object[]... o) {

        Class<?> rtnType = o[0].getClass().getComponentType();
        for (int i = 1; i < o.length; i++) {
            if (!o[i].getClass().getComponentType().equals(rtnType)) {
                rtnType = Object.class;
                break;
            }
        }

        int totalLength = 0;
        for (Object[] param : o) {
            totalLength += param.length;
        }

        Object[] rtn = (Object[]) Array.newInstance(rtnType, totalLength);

        int destPos = 0;
        for (Object[] param : o) {
            System.arraycopy(param, 0, rtn, destPos, param.length);
            destPos += param.length;
        }

        return rtn;
    }

    /**
     * Like Collection.toArray, but without "type checking" warnings if the Collection instance is not typed using
     * generics.
     */
    public static Object[] toArray(Collection<?> c, Class<?> arrayType) {
        Object[] rtn = (Object[]) Array.newInstance(arrayType, c.size());
        int i = 0;
        for (Object param : c) {
            rtn[i++] = param;
        }
        return rtn;
    }

    public static String toString(Object[] array) {
            return toString(array, ", ");
    }

    public static String toString(Object[] array, String sep) {
        if (array == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(String.valueOf(array[i]));
            if (i < array.length - 1) {
                sb.append(sep);
            }
        }
        return sb.toString();
    }

    public static <T> String toString(Collection<T> c) {
        return toString(c, ", ");
    }

    public static <T> String toString(Collection<T> c, String sep) {
        StringBuilder sb = new StringBuilder();
        for (Iterator<T> iter = c.iterator(); iter.hasNext();) {
            sb.append(String.valueOf(iter.next()));
            if (iter.hasNext()) {
                sb.append(sep);
            }
        }
        return sb.toString();
    }

    /**
     * Inspired by python's map function.
     */
    public static Collection<?> map(String[] funcNames, Collection<?> c) {
        Collection<Object> rtn = new ArrayList<>(c.size());
        Method[] methods = new Method[funcNames.length];
        for (Object param : c) {
            for (int i = 0; i < funcNames.length; i++) {
                if (methods[i] == null) {
                    try {
                        methods[i] = param.getClass().getMethod(funcNames[i], (Class[]) null);
                    } catch (Exception ex) {
                        throw new WMRuntimeException(ex);
                    }
                }

                try {
                    rtn.add(methods[i].invoke(param, (Object[]) null));
                } catch (Exception ex) {
                    throw new WMRuntimeException(ex);
                }
            }
        }
        return rtn;
    }

    /**
     * Simple Object diffing for debugging.
     */
    public static String diffObjects(Object o1, Object o2) {
        if (o1.getClass() != o2.getClass()) {
            throw new IllegalArgumentException("Arguments o1 and o2 must " + "be of same type");
        }

        StringBuilder rtn = new StringBuilder();

        List<?> getters = getSimpleGetters(o1.getClass());

        for (Object getter : getters) {
            Method m = (Method) getter;
            try {
                Object r1 = m.invoke(o1, (Object[]) null);
                Object r2 = m.invoke(o2, (Object[]) null);
                if (!WMUtils.areObjectsEqual(r1, r2)) {
                    rtn.append(m.getName()).append(": ").append(r1).append(" != ").append(r2).append("\n");
                }
            } catch (Exception ex) {
                throw new WMRuntimeException(ex);
            }

        }

        return rtn.toString();
    }

    private static final Class<?>[] simpleTypes = new Class<?>[] { int.class, Integer.class, String.class, Date.class };

    private static List<?> getSimpleGetters(Class<?> c) {
        return filterMethods(c.getDeclaredMethods(), new String[] { "get" }, simpleTypes);
    }

    private static List<Method> filterMethods(Method[] methods, String[] names, Class<?>[] rtnTypes) {

        List<Method> rtn = new ArrayList<>();

        for (Method method : methods) {
            for (int n = 0; n < names.length; n++) {
                if (method.getName().startsWith(names[n])) {
                    for (int t = 0; t < rtnTypes.length; t++) {
                        if (method.getReturnType() == rtnTypes[t]) {
                            rtn.add(method);
                        }
                    }
                }
            }
        }

        return rtn;
    }

    private static Class<?> getArrayType_ClassMatch(Class<?> oldC, Class<?> newC) {

        Class<?> ret;

        if (oldC == null) {
            ret = newC;
        } else if (oldC.equals(newC)) {
            ret = oldC;
        } else {
            ret = null;
        }

        return ret;
    }

    /**
     * Get the type of an array or collection (passed in as obj). If the array isn't homogeneous, return null. If obj is
     * not an array or a collection, a runtime exception is thrown. All Collections and Object[] are checked for
     * homogeneity, but nothing else.
     *
     * @param array
     * @return The type of a homogeneous array or Collection, or null if the array or Collection is not homogeneous.
     * @throws IllegalArgumentException If the argument array is not an array or Collection.
     */
    public static Class<?> getArrayType(Object array) {

        if (array == null) {
            throw new IllegalArgumentException("Argument must be an array or a collection, not null");
        }

        Class<?> arrayType;

        if (array.getClass().isArray()) {
            arrayType = array.getClass().getComponentType();

            // if the arrayType is Object, do some deeper checking
            if (arrayType.equals(Object.class)) {
                arrayType = null;

                for (Object elem : (Object[]) array) {
                    arrayType = getArrayType_ClassMatch(arrayType, elem.getClass());
                    if (arrayType == null) {
                        break;
                    }
                }
            }
        } else if (array instanceof Collection) {

            Collection<?> c = (Collection<?>) array;

            if (c.isEmpty()) {
                // empty collection, return dummy type
                return String.class;
            }

            arrayType = null;

            for (Object elem : c) {
                arrayType = getArrayType_ClassMatch(arrayType, elem.getClass());
                if (arrayType == null) {
                    break;
                }
            }
        } else {
            throw new IllegalArgumentException("Argument must be an array or a collection, not " + array.getClass());
        }

        return arrayType;
    }

    public static Collection<String> getKeysStartingWith(String prefix, Map<String, ?> m) {

        Collection<String> rtn = new HashSet<>();
        for (String s : m.keySet()) {
            if (s.startsWith(prefix)) {
                rtn.add(s);
            }
        }
        return rtn;
    }

    public static boolean strCmp(Object o1, Object o2) {
        return String.valueOf(o1).equals(String.valueOf(o2));
    }

    public static boolean isBlankOrEquals(Object o1, Object o2) {
        return ((o1 == null || o1.equals("")) && (o2 == null || o2.equals(""))) || Objects.equals(o1, o2);
    }

    private ObjectUtils() {
    }
}
