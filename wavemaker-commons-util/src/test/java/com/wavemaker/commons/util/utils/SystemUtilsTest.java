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
package com.wavemaker.commons.util.utils;

import org.testng.annotations.Test;

import com.wavemaker.commons.util.SystemUtils;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @author Matt Small
 */
public class SystemUtilsTest{

    @Test
    public void cipherTest() {
        String s = "Rock Band";
        String e = SystemUtils.encrypt(s);
        assertTrue(SystemUtils.isEncrypted(e));
        assertEquals(s, SystemUtils.decrypt(e));

        s = "WAveMaKER rocks awesome great job!";
        e = SystemUtils.encrypt(s);
        assertTrue(SystemUtils.isEncrypted(e));
        assertEquals(s, SystemUtils.decrypt(e));
    }
}
