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

public class TestObjectProcessor implements ObjectProcessor {
    private static final int PROCESSED_INT = 100;
    private static final String PROCESSED_STRING = "processedString";

    @Override
    public void processObject(ObjectInfo objectInfo) {
        Object object = objectInfo.getObject();
        if (object instanceof Integer) {
            objectInfo.updateObject(PROCESSED_INT);
        } else if (object instanceof String) {
            objectInfo.updateObject(PROCESSED_STRING);
        }
    }
}
