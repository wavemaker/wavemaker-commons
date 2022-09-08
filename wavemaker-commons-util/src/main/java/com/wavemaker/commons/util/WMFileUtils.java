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

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.wavemaker.commons.MessageResource;
import com.wavemaker.commons.WMRuntimeException;
import com.wavemaker.commons.io.Resource;

/**
 * @author Uday Shankar
 */
public class WMFileUtils {

    private WMFileUtils() {
    }

    public static String readFileToString(File file) throws IOException {
        return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
    }

    public static void writeStringToFile(File file, String data) throws IOException {
        FileUtils.writeStringToFile(file, data, StandardCharsets.UTF_8, false);
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
            throw new WMRuntimeException(MessageResource.create("com.wavemaker.commons.failed.to.find.matched.ignore.patterns"), e, pattern);
        }
    }

    public static boolean isExists(Resource resource) {
        return resource != null && resource.exists();
    }

   public static void renameFile(Resource resource, String newFileName) {
        Path path = WMIOUtils.getJavaIOFile(resource).toPath();
        try {
            Files.move(path, path.resolveSibling(newFileName));
        } catch (IOException e) {
            throw new WMRuntimeException(MessageResource.create("com.wavemaker.commons.failed.to.rename.file"), e, resource.getName());
        }
    }
}
