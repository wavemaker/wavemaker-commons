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

import java.util.Map;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

public class PropertiesToMapConverterTest {
    @Test
    public void testPropertiesToMapConverter1() {
        Properties properties = new Properties();
        properties.put("a.b.c", "1");
        properties.put("a.b.d", "2");
        properties.put("a.b.e", "3");

        Map<String, Object> map = PropertiesToMapConverter.propertiesToMapConverter(properties);
        Assert.assertEquals(1, map.size());
        Map aMap = (Map) map.get("a");
        Assert.assertEquals(1, aMap.size());
        Map bMap = (Map) aMap.get("b");
        Assert.assertEquals(3, bMap.size());
        Assert.assertEquals("3", bMap.get("e"));
    }

    @Test
    public void testPropertiesToMapConverter2() {
        Properties properties = new Properties();
        properties.put("security.general.ssl.enabled", "false");
        properties.put("security.general.ssl.port", "443");
        properties.put("security.general.csp.enabled", "false");

        Map<String, Object> map = PropertiesToMapConverter.propertiesToMapConverter(properties);
        Assert.assertEquals(1, map.size());
        Map securityMap = (Map) map.get("security");
        Assert.assertEquals(1, securityMap.size());
        Map generalMap = (Map) securityMap.get("general");
        Assert.assertEquals(2, generalMap.size());
        Map sslMap = (Map) generalMap.get("ssl");
        Assert.assertEquals(2, sslMap.size());
    }
}