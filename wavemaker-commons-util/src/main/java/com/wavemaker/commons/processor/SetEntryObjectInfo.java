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

import java.util.LinkedHashSet;
import java.util.Set;

public class SetEntryObjectInfo extends ObjectInfo {
    public SetEntryObjectInfo(Object object, ObjectInfo parentObjectInfo) {
        super(object, parentObjectInfo);
    }

    @Override
    public void updateObject(Object updatedObject) {
        Object oldElement = this.getObject();
        super.updateObject(updatedObject);
        Set set = (Set) this.getParentObjectInfo().getObject();
        try {
            updateElementInSet(set, oldElement, updatedObject);
        } catch (UnsupportedOperationException e) {
            Set newSet = new LinkedHashSet<>(set);
            this.getParentObjectInfo().updateObject(newSet);
            updateElementInSet(newSet, oldElement, updatedObject);
        }
    }

    private void updateElementInSet(Set set, Object oldElement, Object newElement) {
        set.remove(oldElement);
        set.add(newElement);
    }
}
