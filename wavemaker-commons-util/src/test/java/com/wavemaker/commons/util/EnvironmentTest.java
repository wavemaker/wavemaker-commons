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

import org.testng.annotations.Test;

/**
 * Used to debug CC failures. Prints out some information about the environment of this test run.
 *
 * @author Matt Small
 */
public class EnvironmentTest {

    @Test
    public void environmentTest() throws Exception {

        Runtime runtime = Runtime.getRuntime();
        System.out.println("maxMemory: " + runtime.maxMemory() + ", totalMemory: " + runtime.totalMemory() + ", freeMemory: " + runtime.freeMemory());
    }
}