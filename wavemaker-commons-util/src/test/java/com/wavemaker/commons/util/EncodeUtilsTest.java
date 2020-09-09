/**
 * Copyright (C) 2020 WaveMaker, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wavemaker.commons.util;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class EncodeUtilsTest {

    private String actualString;
    private String encodedString;

    public EncodeUtilsTest(String actualString, String encodedString) {
        this.actualString = actualString;
        this.encodedString = encodedString;
    }

    @Parameterized.Parameters()
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
                {"company name", "company+name"}, {"a+b", "a%2Bb"}, {"a%ddf", "a%25ddf"}, {"as%2Csdxfcv", "as%252Csdxfcv"},
                {"this is 'random',\\\"text\\\"2Check(encoding&{decoding})", "this+is+%27random%27%2C%5C%22text%5C%222Check%28encoding%26%7Bdecoding%7D%29"}
        });
    }

    @Test
    public void encode() {
        Assert.assertEquals(encodedString, EncodeUtils.encode(actualString));
    }

    @Test
    public void decode() {
        Assert.assertEquals(actualString, EncodeUtils.decode(encodedString));
    }
}