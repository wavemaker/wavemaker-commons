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

import java.util.Arrays;
import java.util.Objects;

public class ArrayPojo {
    private final int[] primitiveArray;
    private final Integer[] wrapperArray;
    private final String[] stringArray;
    private final PrimitivePojo[] nonPrimitiveArray;

    public ArrayPojo(int[] primitiveArray, Integer[] wrapperArray, String[] stringArray, PrimitivePojo[] nonPrimitiveArray) {
        this.primitiveArray = primitiveArray;
        this.wrapperArray = wrapperArray;
        this.stringArray = stringArray;
        this.nonPrimitiveArray = nonPrimitiveArray;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ArrayPojo arrayPojo = (ArrayPojo) object;
        return Objects.deepEquals(primitiveArray, arrayPojo.primitiveArray) && Objects.deepEquals(wrapperArray, arrayPojo.wrapperArray) && Objects.deepEquals(stringArray, arrayPojo.stringArray) && Objects.deepEquals(nonPrimitiveArray, arrayPojo.nonPrimitiveArray);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(primitiveArray), Arrays.hashCode(wrapperArray), Arrays.hashCode(stringArray), Arrays.hashCode(nonPrimitiveArray));
    }
}
