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

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.apache.commons.lang3.ClassUtils;

/**
 * The RecursiveObjectProcessor class is responsible for recursively processing an object
 * and its child objects using a specified object processor. It supports the handling
 * of primitive types, wrappers, and collections.
 */
public class RecursiveObjectProcessor {

    private final ObjectProcessor objectProcessor;
    private final ChildObjectRetriever childObjectRetriever;
    private final ProcessContext processContext;
    private final Predicate<Object> objectPredicate;

    /**
     * Constructs a RecursiveObjectProcessor with the specified object processor,
     * child object retriever, and process context.
     *
     * @param objectProcessor the processor to apply to each object
     * @param childObjectRetriever the retriever to get child objects
     * @param processContext the context for processing
     */
    public RecursiveObjectProcessor(ObjectProcessor objectProcessor, ChildObjectRetriever childObjectRetriever, ProcessContext processContext) {
        this(objectProcessor, childObjectRetriever, processContext, aClass -> true);
    }

    /**
     * Constructs a RecursiveObjectProcessor with the specified object processor,
     * child object retriever, process context, and object predicate.
     *
     * @param objectProcessor the processor to apply to each object
     * @param childObjectRetriever the retriever to get child objects
     * @param processContext the context for processing
     * @param objectPredicate the predicate to test objects and only processes the object and gets the child objects if true
     */
    public RecursiveObjectProcessor(ObjectProcessor objectProcessor, ChildObjectRetriever childObjectRetriever, ProcessContext processContext,
                                    Predicate<Object> objectPredicate) {
        this.objectProcessor = objectProcessor;
        this.childObjectRetriever = childObjectRetriever;
        this.processContext = processContext;
        this.objectPredicate = objectPredicate;
    }

    /**
     * Processes the root object and returns the processed object.
     *
     * @param object the root object to process
     * @return the processed root object
     */
    public Object processRootObject(Object object) {
        ObjectInfo objectInfo = new ObjectInfo(object, null);
        processObject(objectInfo, processContext.getKnownObjects());
        return objectInfo.getObject();
    }

    private void processObject(ObjectInfo objectInfo, Set<Object> knownObjects) {
        Object object = objectInfo.getObject();
        if (object != null && objectPredicate.test(object)) {
            if (ClassUtils.isPrimitiveOrWrapper(object.getClass()) || object instanceof String) {
                objectProcessor.processObject(objectInfo);
            } else if (!knownObjects.contains(object)) {
                knownObjects.add(object);
                objectProcessor.processObject(objectInfo);
                processChildObjects(objectInfo, knownObjects);
            }
        }
    }

    private void processChildObjects(ObjectInfo objectInfo, Set<Object> knownObjects) {
        List<ObjectInfo> childObjectsInfo = childObjectRetriever.getChildObjects(objectInfo);
        for (ObjectInfo childObjectInfo : childObjectsInfo) {
            processObject(childObjectInfo, knownObjects);
        }
    }
}