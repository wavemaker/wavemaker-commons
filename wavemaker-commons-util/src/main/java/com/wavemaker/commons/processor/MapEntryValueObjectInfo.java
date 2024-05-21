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

public class MapEntryValueObjectInfo extends ObjectInfo {
    private final MapEntryKeyObjectInfo keyObjectInfo;

    public MapEntryValueObjectInfo(MapEntryKeyObjectInfo keyObjectInfo, Object value, ObjectInfo parentObjectInfo) {
        super(value, parentObjectInfo);
        this.keyObjectInfo = keyObjectInfo;
    }

    @Override
    public void updateObject(Object updatedObject) {
        super.updateObject(updatedObject);
        Map map = (Map) this.getParentObjectInfo().getObject();
        try {
            updateValueInMap(map, keyObjectInfo.getObject(), updatedObject);
        } catch (UnsupportedOperationException e) {
            Map newMap = new LinkedHashMap<>(map);
            this.getParentObjectInfo().updateObject(newMap);
            updateValueInMap(newMap, keyObjectInfo.getObject(), updatedObject);
        }
    }

    private void updateValueInMap(Map map, Object key, Object newValue) {
        map.put(key, newValue);
    }
}
