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

package com.wavemaker.commons.processor;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

/**
 * The DefaultChildObjectRetriever class is responsible for retrieving child objects
 * from a given parent object. It supports handling of Lists, Maps, Sets, Arrays(primitive and non-primitive), and
 * custom objects.
 */
public class DefaultChildObjectRetriever implements ChildObjectRetriever {
    private static final Logger logger = LoggerFactory.getLogger(DefaultChildObjectRetriever.class);
    private final Predicate<Field> fieldPredicate;

    /**
     * Constructs a DefaultChildObjectRetriever with a field predicate that always returns true.
     */
    public DefaultChildObjectRetriever() {
        this.fieldPredicate = field -> true;
    }

    /**
     * Constructs a DefaultChildObjectRetriever with the specified field predicate.
     *
     * @param fieldPredicate the predicate to test fields and a field is considered as child only if it is true
     */
    public DefaultChildObjectRetriever(Predicate<Field> fieldPredicate) {
        this.fieldPredicate = fieldPredicate;
    }

    /**
     * Retrieves the child objects of a given parent object.
     *
     * @param objectInfo the parent object information
     *
     * @return a list of child object information
     */
    @Override
    public List<ObjectInfo> getChildObjects(ObjectInfo objectInfo) {
        Object object = objectInfo.getObject();
        List<ObjectInfo> childObjectsInfo = new ArrayList<>();
        if (object instanceof List) {
            processList((List) object, objectInfo, childObjectsInfo);
        } else if (object instanceof Map) {
            processMap((Map) object, objectInfo, childObjectsInfo);
        } else if (object instanceof Set) {
            processSet((Set) object, objectInfo, childObjectsInfo);
        } else if (object.getClass().isArray()) {
            processArray(object, objectInfo, childObjectsInfo);
        } else if (object.getClass().getClassLoader() != null) {
            processCustomObject(object, objectInfo, childObjectsInfo);
        }
        return childObjectsInfo;
    }

    private void processList(List list, ObjectInfo parentObjectInfo, List<ObjectInfo> childObjectsInfo) {
        try {
            for (int index = 0; index < list.size(); index++) {
                ObjectInfo childObjectInfo = new ListEntryObjectInfo(list.get(index), parentObjectInfo, index);
                childObjectsInfo.add(childObjectInfo);
            }
        } catch (Exception e) {
            //Handling lazy loaded hibernate PersistentBag
            logger.debug("Failed to retrieve elements of a list of class {}", list.getClass(), e);
        }
    }

    private void processMap(Map map, ObjectInfo parentObjectInfo, List<ObjectInfo> childObjectsInfo) {
        try {
            map.forEach((k, v) -> {
                MapEntryKeyObjectInfo mapEntryKeyObjectInfo = new MapEntryKeyObjectInfo(k, v, parentObjectInfo);
                ObjectInfo mapEntryValueObjectInfo = new MapEntryValueObjectInfo(mapEntryKeyObjectInfo, v, parentObjectInfo);
                childObjectsInfo.add(mapEntryKeyObjectInfo);
                childObjectsInfo.add(mapEntryValueObjectInfo);
            });
        } catch (Exception e) {
            logger.debug("Failed to retrieve elements of a map", e);
        }
    }

    private void processSet(Set set, ObjectInfo parentObjectInfo, List<ObjectInfo> childObjectsInfo) {
        try {
            set.forEach(setElement -> {
                ObjectInfo setEntryObjectInfo = new SetEntryObjectInfo(setElement, parentObjectInfo);
                childObjectsInfo.add(setEntryObjectInfo);
            });
        } catch (Exception e) {
            logger.debug("Failed to retrieve elements of a set", e);
        }
    }

    private void processArray(Object object, ObjectInfo parentObjectInfo, List<ObjectInfo> childObjectsInfo) {
        try {
            for (int index = 0; index < Array.getLength(object); index++) {
                ObjectInfo arrayEntryObjectInfo = new ArrayEntryObjectInfo(Array.get(object, index), parentObjectInfo, index);
                childObjectsInfo.add(arrayEntryObjectInfo);
            }
        } catch (Exception e) {
            logger.debug("Failed to retrieve elements of a array", e);
        }
    }

    private void processCustomObject(Object object, ObjectInfo parentObjectInfo, List<ObjectInfo> childObjectsInfo) {
        ReflectionUtils.doWithFields(object.getClass(), field -> {
            if (fieldPredicate.test(field) && !Modifier.isStatic(field.getModifiers())) {
                try {
                    ReflectionUtils.makeAccessible(field);
                    Object o = ReflectionUtils.getField(field, object);
                    ObjectInfo fieldObjectInfo = new FieldObjectInfo(o, parentObjectInfo, field);
                    childObjectsInfo.add(fieldObjectInfo);
                } catch (Exception e) {
                    logger.debug("Cannot access object from the field {} in class {}", field.getName(), field.getDeclaringClass().getName());
                }
            }
        });
    }
}
