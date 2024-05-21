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

import java.util.ArrayList;
import java.util.List;

public class ListEntryObjectInfo extends ObjectInfo {
    private final int index;

    public ListEntryObjectInfo(Object object, ObjectInfo parentObjectInfo, int index) {
        super(object, parentObjectInfo);
        this.index = index;
    }

    @Override
    public void updateObject(Object updatedObject) {
        super.updateObject(updatedObject);
        List list = (List) this.getParentObjectInfo().getObject();
        try {
            updateElementInList(list, updatedObject);
        } catch (UnsupportedOperationException e) {
            List newArrayList = new ArrayList<>(list);
            this.getParentObjectInfo().updateObject(newArrayList);
            updateElementInList(newArrayList, updatedObject);
        }
    }

    private void updateElementInList(List list, Object newElement) {
        list.set(index, newElement);
    }
}
