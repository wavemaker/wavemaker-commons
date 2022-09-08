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
package com.wavemaker.commons.web.filter;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Uday Shankar
 */

public class ServerTimingMetricTest {
    
    @Test(dataProvider = "testDataProvider")
    private void parseTest(String s, String name, Long processingTime, String description, Class throwableClass, String expectedAsString) {
        if (throwableClass != null) {
            Assert.assertThrows(throwableClass, () -> ServerTimingMetric.parse(s));    
        } else {
            ServerTimingMetric serverTimingMetric = ServerTimingMetric.parse(s);
            Assert.assertEquals(serverTimingMetric.getName(), name);
            Assert.assertEquals(serverTimingMetric.getProcessingTime(), processingTime);
            Assert.assertEquals(serverTimingMetric.getDescription(), description);
            Assert.assertEquals(serverTimingMetric.asString(), expectedAsString);
        }
    }

    @DataProvider(name = "testDataProvider")
    public Object[][] testDataProvider() {
        return new Object[][] {
                new Object[] {"cache;desc=\"Cache Read\";dur=23", "cache", 23l, "Cache Read", null, "cache(Cache Read);dur=23"},
                new Object[] {"cache;desc=Cache Read;dur=23","cache", 23l, "Cache Read", null, "cache(Cache Read);dur=23"},
                new Object[] {"cache;dur=23","cache", 23l, null, null, "cache;dur=23"},
                new Object[] {"cache;desc=Cache Read","cache", null, "Cache Read", null, "cache(Cache Read)"},
                new Object[] {"cache;desc=\"","cache", null, "Cache Read", IllegalArgumentException.class, null},
                new Object[] {"cache","cache", null, null, null, "cache"},
                new Object[] {" cache","cache", null, null, null, "cache"},
                new Object[] {"cache ","cache", null, null, null, "cache"},
                new Object[] {" cache ","cache", null, null, null, "cache"},
                new Object[] {null, null, null, null, IllegalArgumentException.class, null},
                new Object[] {"====", null, null, null, IllegalArgumentException.class, null},
                new Object[] {"   cache     ;   desc=     \"Cache Read\"    ;   dur  =    23    ", "cache", 23l, "Cache Read", null, "cache(Cache Read);dur=23"},
                new Object[] {"   cache     ;desc=     \"Cache Read\"    ;   dur  =    23  ; abcd = ndhcsj  ", "cache", 23l, "Cache Read", null, "cache(Cache Read);" +
                        "dur=23"},
        };
    }
}
