package com.wavemaker.commons.web.filter;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Uday Shankar
 */

public class ServerTimingMetricTest {
    
    @Test(dataProvider = "testDataProvider")
    private void parseTest(String s, String name, Long processingTime, String description, Class throwableClass) {
        if (throwableClass != null) {
            Assert.assertThrows(throwableClass, () -> ServerTimingMetric.parse(s));    
        } else {
            ServerTimingMetric serverTimingMetric = ServerTimingMetric.parse(s);
            Assert.assertEquals(serverTimingMetric.getName(), name);
            Assert.assertEquals(serverTimingMetric.getProcessingTime(), processingTime);
            Assert.assertEquals(serverTimingMetric.getDescription(), description);
        }
    }

    @DataProvider(name = "testDataProvider")
    public Object[][] testDataProvider() {
        return new Object[][] {
                new Object[] {"cache;desc=\"Cache Read\";dur=23", "cache", 23l, "\"Cache Read\"", null},
                new Object[] {"cache;desc=Cache Read;dur=23","cache", 23l, "Cache Read", null},
                new Object[] {"cache;dur=23","cache", 23l, null, null},
                new Object[] {"cache;desc=Cache Read","cache", null, "Cache Read", null},
                new Object[] {"cache","cache", null, null, null},
                new Object[] {" cache","cache", null, null, null},
                new Object[] {"cache ","cache", null, null, null},
                new Object[] {" cache ","cache", null, null, null},
                new Object[] {null, null, null, null, IllegalArgumentException.class},
                new Object[] {"====", null, null, null, IllegalArgumentException.class},
                new Object[] {"   cache     ;   desc=     \"Cache Read\"    ;   dur  =    23    ", "cache", 23l, "\"Cache Read\"", null},
                new Object[] {"   cache     ;desc=     \"Cache Read\"    ;   dur  =    23  ; abcd = ndhcsj  ", "cache", 23l, "\"Cache Read\"", null},
        };
    }
}
