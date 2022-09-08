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
package com.wavemaker.commons.io;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Uday Shankar
 */
public class ReplaceOperation implements ResourceOperation<File> {

    private final String fromExpression;

    private final String toExpression;

    public ReplaceOperation(String fromExpression, String toExpression) {
        this.fromExpression = fromExpression;
        this.toExpression = toExpression;
    }

    @Override
    public void perform(File file) {
        String originalContent = file.getContent().asString();
        String updatedContent = originalContent.replace(this.fromExpression, this.toExpression);
        if (!StringUtils.equals(originalContent, updatedContent)) {
            file.getContent().write(updatedContent);
        }
    }
}
