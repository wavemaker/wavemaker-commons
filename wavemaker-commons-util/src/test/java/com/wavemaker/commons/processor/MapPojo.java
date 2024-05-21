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

import java.util.Map;
import java.util.Objects;

public class MapPojo {
    private final Map<String, String> stringMap;
    private final Map<String, Map<String, String>> nestedStringMap;
    private final Map<PrimitivePojo, PrimitivePojo> primitivePojoMap;

    public MapPojo(Map<String, String> stringMap, Map<String, Map<String, String>> nestedStringMap, Map<PrimitivePojo, PrimitivePojo> primitivePojoMap) {
        this.stringMap = stringMap;
        this.nestedStringMap = nestedStringMap;
        this.primitivePojoMap = primitivePojoMap;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        MapPojo mapPojo = (MapPojo) object;
        return Objects.equals(stringMap, mapPojo.stringMap) && Objects.equals(nestedStringMap, mapPojo.nestedStringMap) && Objects.equals(primitivePojoMap, mapPojo.primitivePojoMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stringMap, nestedStringMap, primitivePojoMap);
    }
}
