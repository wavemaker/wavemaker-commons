package com.wavemaker.commons.util;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Uday Shankar
 */
public class VersionTest {
    
    
    @Test(dataProvider = "compareToDataProvider")
    public void testCompareTo(String version1, String version2, Boolean expectedValueLessThanZero) {
        int compareTo = new Version(version1).compareTo(new Version(version2));
        if (expectedValueLessThanZero==null) {
            Assert.assertTrue(compareTo == 0);
        } else if (expectedValueLessThanZero) {
            Assert.assertTrue(compareTo < 0);
        } else if (!expectedValueLessThanZero) {
            Assert.assertTrue(compareTo > 0);
        }
    }

    @DataProvider(name = "compareToDataProvider")
    public Object[][] propertyData() {
        Object[][] obj = new Object[10][3];
        obj[0] = new Object[] {"8", "9", true};
        obj[1] = new Object[] {"9", "10", true};
        obj[2] = new Object[] {"9.5.0", "9.4.1", false};
        obj[3] = new Object[] {"8.0", "8.1", true};
        obj[4] = new Object[] {"8.0", "9", true};
        obj[5] = new Object[] {"9", "8.2", false};
        obj[6] = new Object[] {"9.4.0.1", "9.5", true};
        obj[7] = new Object[] {"9.4.0.1", "10.0.0", true};
        obj[8] = new Object[] {"9.4.0.1", "9.4.0.2", true};
        obj[9] = new Object[] {"9.4.0.1", "9.4.0.1", null};
        return obj;
    }
}
