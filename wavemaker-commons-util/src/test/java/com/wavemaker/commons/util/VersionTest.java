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
