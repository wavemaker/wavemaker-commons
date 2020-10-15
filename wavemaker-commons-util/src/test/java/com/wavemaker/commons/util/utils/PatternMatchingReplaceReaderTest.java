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
package com.wavemaker.commons.util.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;

import com.wavemaker.commons.util.PatternMatchingReplaceReader;

public class PatternMatchingReplaceReaderTest {


    private static Environment env;

    @BeforeClass
    public static void beforeClass() {
        env = new StandardEnvironment();// after deployment in tomcat it takes StandardServletEnvironment();
    }

    @Test
    public void testWithSimpleTextFile() throws IOException {
        String fileName = "pattern-matching-reader/pattern-file1.txt";
        System.setProperty("rest.googleapis.basepath", "my-app-path");
        System.setProperty("rest.googleapis.scheme","my-app-scheme");
        System.setProperty("rest.google-app_store.drive","google-drive");

        String resolvedString = getResolvedStringForFile(fileName, "${{", "}}");
        String expectedOutput = IOUtils.resourceToString("pattern-matching-reader/expected-file1.txt", Charset.forName("UTF-8"), PatternMatchingReplaceReaderTest.class.getClassLoader());
        Assert.assertEquals(expectedOutput, resolvedString);
    }

    @Test
    public void testWithXmlFile() throws IOException
    {
        String fileName= "pattern-matching-reader/pattern-file2.xml";
        System.setProperty("jdbc.driver","driver1");
        System.setProperty("jdbc.db-user_name","user1");
        System.setProperty("jdbc.db-password_1","pass123");
        System.setProperty("app.class","com.java.Person");
        System.setProperty("app.name","Person Info");
        System.setProperty("app.version","19.23");

        String resolvedString=getResolvedStringForFile(fileName, "${", "}");
        String expectedOutput = IOUtils.resourceToString("pattern-matching-reader/expected-file2.xml", Charset.forName("UTF-8"), PatternMatchingReplaceReaderTest.class.getClassLoader());
        Assert.assertEquals(expectedOutput, resolvedString);
    }

    private String getResolvedStringForFile(String fileName, String prefix, String suffix) throws IOException {
        Reader pmr = null;
        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream(fileName);
            Reader isr = new InputStreamReader(is);
            pmr = new PatternMatchingReplaceReader(isr, prefix, suffix, key -> env.getProperty(key));

            StringWriter stringWriter = new StringWriter();
            IOUtils.copy(pmr, stringWriter);
            return stringWriter.toString();
        } finally {
            if (pmr != null) {
                pmr.close();
            }
        }
    }
}
