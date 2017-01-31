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
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.wavemaker.commons.WMRuntimeException;

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

    public static Collection<String> findMatchedRelativePaths(String pattern, String basePath) {
        FilePatternMatchVisitor filePatternMatchVisitor = new FilePatternMatchVisitor(pattern, basePath);
        try {
            Files.walkFileTree(Paths.get(basePath), filePatternMatchVisitor);
            Collection<Path> matchedFiles = filePatternMatchVisitor.getMatchedPaths();
            List<String> matchedFilePaths = new ArrayList<>(matchedFiles.size());
            for (Path path : matchedFiles) {
                matchedFilePaths.add(path.toString());
            }
            return matchedFilePaths;
        } catch (IOException e) {
            throw new WMRuntimeException("Failed to find matched ignore patterns for " + pattern, e);
        }
    }
}
