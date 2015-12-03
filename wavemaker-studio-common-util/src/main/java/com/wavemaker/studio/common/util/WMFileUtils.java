/**
 * Copyright © 2015 WaveMaker, Inc.
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
package com.wavemaker.studio.common.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;

/**
 * @author Uday Shankar
 */
public class WMFileUtils {

    public static final Charset UTF_8_ENCODING = Charset.forName("UTF-8");

    public static String readFileToString(File file) throws IOException {
        return FileUtils.readFileToString(file, UTF_8_ENCODING);
    }

    public static void writeStringToFile(File file, String data) throws IOException {
        FileUtils.writeStringToFile(file, data, UTF_8_ENCODING, false);
    }
}
