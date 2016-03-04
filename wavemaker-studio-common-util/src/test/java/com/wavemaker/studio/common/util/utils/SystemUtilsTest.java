/**
 * Copyright © 2013 - 2016 WaveMaker, Inc.
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
package com.wavemaker.studio.common.util.utils;

import com.wavemaker.infra.WMTestUtils;
import com.wavemaker.studio.common.util.SpringUtils;
import com.wavemaker.studio.common.util.SystemUtils;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * @author Matt Small
 */
public class SystemUtilsTest{

    public void setUp() throws Exception {
        SpringUtils.initSpringConfig();
    }
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
