/**
 * Copyright © 2013 - 2017 WaveMaker, Inc.
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
package com.wavemaker.commons.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.testng.Assert;

public class WMTestUtils {

    public static void assertEquals(File expected, File actual) throws IOException {
        assertEquals(null, expected, actual);
    }

    /**
     * Compare the contents of two files; display the message.
     *
     * @param message The message to display.
     * @param expected The File containing the expected contents.
     * @param actual The File containing the resulting contents.
     * @throws IOException
     */
    public static void assertEquals(String message, File expected, File actual) throws IOException {

        String msg = "mismatch between expected file \"" + expected + "\" and actual \"" + actual + "\"";
        if (message != null) {
            msg = message + ": " + msg;
        }

        if (expected.equals(actual)) {
            // pass
        } else if (expected.isFile() && actual.isFile()) {
            String expectedStr = WMFileUtils.readFileToString(expected);
            String actualStr = WMFileUtils.readFileToString(actual);

            Assert.assertEquals(actualStr, expectedStr, msg);
        } else if (expected.isDirectory() && actual.isDirectory()) {
            Assert.assertEquals(msg, expected.getAbsolutePath(), actual.getAbsolutePath());
        } else {
            Assert.fail(msg);
        }
    }

    public static InputStream getResourceFileAsStream(String resourcePath) {
        return WMTestUtils.class.getClassLoader().getResourceAsStream(resourcePath);
    }

    public static File getResourceFileAsLocalFile(String resourcePath) {
        return new File(WMTestUtils.class.getClassLoader().getResource(resourcePath).getFile());
    }
}
