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

import java.util.LinkedHashMap;
import java.util.Map;

public class MapEntryKeyObjectInfo extends ObjectInfo {

    private final Object value;

    public MapEntryKeyObjectInfo(Object key, Object value, ObjectInfo parentObjectInfo) {
        super(key, parentObjectInfo);
        this.value = value;
    }

    @Override
    public void updateObject(Object updatedKey) {
        Object oldKey = this.getObject();
        super.updateObject(updatedKey);
        Map map = (Map) this.getParentObjectInfo().getObject();
        try {
            updateKeyInMap(map, oldKey, updatedKey);
        } catch (UnsupportedOperationException e) {
            Map newMap = new LinkedHashMap<>(map);
            this.getParentObjectInfo().updateObject(newMap);
            updateKeyInMap(newMap, oldKey, updatedKey);
        }
    }

    private void updateKeyInMap(Map map, Object oldKey, Object newKey) {
        map.remove(oldKey);
        map.put(newKey, value);
    }
}
