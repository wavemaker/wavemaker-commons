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

import java.util.Objects;
import java.util.Set;

public class SetPojo {
    private final Set<String> stringSet;
    private final Set<Set<String>> nestedStringSet;

    public SetPojo(Set<String> stringSet, Set<Set<String>> nestedStringSet) {
        this.stringSet = stringSet;
        this.nestedStringSet = nestedStringSet;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SetPojo setPojo = (SetPojo) object;
        return setsAreEqual(stringSet, setPojo.stringSet) && nestedSetsAreEqual(nestedStringSet, setPojo.nestedStringSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stringSet, nestedStringSet);
    }

    private boolean setsAreEqual(Set<String> set1, Set<String> set2) {
        if (set1 == null && set2 == null) {
            return true;
        }
        return set1 != null && set2 != null && set1.size() == set2.size() && set1.containsAll(set2) && set2.containsAll(set1);
    }

    private boolean nestedSetsAreEqual(Set<Set<String>> set1, Set<Set<String>> set2) {
        if (set1 == null && set2 == null) {
            return true;
        }
        return set1 != null && set2 != null && set1.size() == set2.size() && set1.stream().allMatch(set1Element ->
            set2.stream().anyMatch(set2Element -> setsAreEqual(set1Element, set2Element)));
    }
}
