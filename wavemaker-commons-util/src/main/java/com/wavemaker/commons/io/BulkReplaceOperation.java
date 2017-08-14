/**
 * Copyright © 2013 - 2017 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wavemaker.commons.io;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This operation can be used for bulk replace of tokens. The operation has a single write at the end.
 *
 * Created by ArjunSahasranam on 12/10/15.
 */
public class BulkReplaceOperation implements ResourceOperation<File> {

    private final Map<String, String> map;

    public BulkReplaceOperation() {
        map = new HashMap<>();
    }

    public void add(String from, String to) {
        map.put(from, to);
    }

    public void addAll(Map<String, String> values) {
        map.putAll(values);
    }

    @Override
    public void perform(final File resource) {
        String originalContent = resource.getContent().asString();
        String content = originalContent;
        for (String from : map.keySet()) {
            content = content.replace(from, map.get(from));
        }
        if (!Objects.equals(content, originalContent)) {
            resource.getContent().write(content);
        }
    }
}
