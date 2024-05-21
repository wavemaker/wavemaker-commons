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

public class ObjectInfo {
    private Object object;
    private ObjectInfo parentObjectInfo;

    public ObjectInfo(Object object, ObjectInfo parentObjectInfo) {
        this.object = object;
        this.parentObjectInfo = parentObjectInfo;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public ObjectInfo getParentObjectInfo() {
        return parentObjectInfo;
    }

    public void updateObject(Object updatedObject) {
        this.setObject(updatedObject);
    }
}
