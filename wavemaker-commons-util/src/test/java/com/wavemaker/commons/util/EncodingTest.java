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

import static org.testng.Assert.assertEquals;

/**
 * Some sanity tests.
 *
 * @author Simon Toens
 */
public class EncodingTest {

    @Test
    public void test1() throws Exception {
        String str = "\u00F6";
        assertEquals("ö", str);
        // UTF8 is c3b6, LATIN1 is f6

        assertEquals("c3b6", Long.toHexString(SystemUtils.getUnsignedValue(str.getBytes("UTF8"))));
        assertEquals("f6", Long.toHexString(SystemUtils.getUnsignedValue(str.getBytes("LATIN1"))));
    }

    @Test
    public void test2() throws Exception {
        String str = "\u00C9";
        assertEquals("É", str);
        // UTF8 is c389
        assertEquals("c389", Long.toHexString(SystemUtils.getUnsignedValue(str.getBytes("UTF8"))));
    }

    @Test
    public void test3() throws Exception {
        String str = "\u00E9";
        assertEquals("é", str);
        // UTF8 is c3a9
        assertEquals("c3a9", Long.toHexString(SystemUtils.getUnsignedValue(str.getBytes("UTF8"))));
    }

    @Test
    public void getValueTest() {
        assertEquals(1, SystemUtils.getUnsignedValue(new byte[]{1}));

        byte b = 0;
        b = (byte) (b | 128);
        b = (byte) (b | 1);
        // 128 + 1 = 129
        assertEquals(129, SystemUtils.getUnsignedValue(new byte[]{b}));

        b = 0;
        b = (byte) (b | 255);
        assertEquals(255, SystemUtils.getUnsignedValue(new byte[]{b}));
        assertEquals(256, SystemUtils.getUnsignedValue(new byte[]{1, 0}));

        b = 0;
        b = (byte) (b | 256); // overflow
        assertEquals(0, SystemUtils.getUnsignedValue(new byte[]{b}));
    }
}
