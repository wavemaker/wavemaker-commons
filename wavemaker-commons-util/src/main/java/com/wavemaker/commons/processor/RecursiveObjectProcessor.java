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

public class RecursiveObjectProcessor {

    private final ObjectProcessor objectProcessor;
    private final ChildObjectRetriever childObjectRetriever;
    private final ProcessContext processContext;
    private final Predicate<Object> objPredicate;

    public RecursiveObjectProcessor(ObjectProcessor objectProcessor, ChildObjectRetriever childObjectRetriever, ProcessContext processContext) {
        this(objectProcessor, childObjectRetriever, processContext, aClass -> true);
    }

    public RecursiveObjectProcessor(ObjectProcessor objectProcessor, ChildObjectRetriever childObjectRetriever, ProcessContext processContext,
                                    Predicate<Object> objPredicate) {
        this.objectProcessor = objectProcessor;
        this.childObjectRetriever = childObjectRetriever;
        this.processContext = processContext;
        this.objPredicate = objPredicate;
    }

    public Object processRootObject(Object object) {
        ObjectInfo objectInfo = new ObjectInfo(object, null);
        processObject(objectInfo, processContext.getKnownObjects());
        return objectInfo.getObject();
    }

    private void processObject(ObjectInfo objectInfo, Set<Object> knownObjects) {
        Object object = objectInfo.getObject();
        if (object != null && objPredicate.test(object)) {
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
