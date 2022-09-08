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

package com.wavemaker.commons.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.wavemaker.commons.WMRuntimeException;

public class FileValidationUtilsTest {

    private List<String> positiveCase;
    private List<String> negativeCase;

    @Before
    public void initialize() {
        positiveCase = new ArrayList<>();
        positiveCase.add("/etc/master.passwd");
        positiveCase.add("/master.passwd");
        positiveCase.add("etc/passwd");

        negativeCase = new ArrayList<>();
        negativeCase.add("../../etc/passwd");
        negativeCase.add("../../../administrator/inbox");
        negativeCase.add("../_config.php%00");
    }

    @Test
    public void validateFilePath() {
        positiveCase.forEach(FileValidationUtils::validateFilePath);
    }

    @Test(expected = WMRuntimeException.class)
    public void validateFilePathThrowsWMRuntimeExceptionWhenPathContainsDots() {
        negativeCase.forEach(FileValidationUtils::validateFilePath);
    }
}