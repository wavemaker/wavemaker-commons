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
package com.wavemaker.commons;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.wavemaker.commons.util.SpringUtils;
import com.wavemaker.commons.util.WMFileUtils;
import com.wavemaker.commons.util.WMIOUtils;
import junit.framework.TestCase;

/**
 * @author Simon Toens
 */
public abstract class WMTestCase extends TestCase {

    private static final Level DEFAULT_LEVEL = Level.INFO;

    private static final Logger logger = Logger.global;
    static {
        logger.setLevel(DEFAULT_LEVEL);
    }

    protected WMTestCase() {
        super();
    }

    protected WMTestCase(String methodName) {
        super(methodName);
    }

    protected WMTestCase(String methodName, Level logLevel) {
        super(methodName);
        logger.setLevel(logLevel);
    }

    protected WMTestCase(Level logLevel) {
        super();
        logger.setLevel(logLevel);
    }

    protected void info(Object o) {
        logger.info(String.valueOf(o));
    }

    protected void warn(Object o) {
        logger.warning(String.valueOf(o));
    }

    protected File createTempDir() {
        try {
            return WMIOUtils.createTempDirectory();
        } catch (IOException ex) {
            throw new WMRuntimeException(ex);
        }
    }

    /**
     * By default, initialize Spring (so we can get our error messages).
     */
    @Override
    public void setUp() throws Exception {
        SpringUtils.initSpringConfig();
    }

    /**
     * Compare the contents of two files; display the message.
     * 
     * @param expected The File containing the expected contents.
     * @param actual The File containing the resulting contents.
     * @throws IOException
     */
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

            assertEquals(msg, expectedStr, actualStr);
        } else if (expected.isDirectory() && actual.isDirectory()) {
            assertEquals(msg, expected.getAbsolutePath(), actual.getAbsolutePath());
        } else {
            fail(msg);
        }
    }
}
