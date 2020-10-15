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
package com.wavemaker.commons.util;

import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.nio.file.FileVisitResult.CONTINUE;

/**
 * Created by kishorer on 9/5/16.
 */
public class FilePatternMatchVisitor extends SimpleFileVisitor<Path> {

    private final PathMatcher matcher;
    private List<Path> matchedPaths = new ArrayList<>();
    private Path basePath;

    public FilePatternMatchVisitor(String pattern, String basePath) {
        matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
        this.basePath = Paths.get(basePath);
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        addToListIfMatching(file);
        return CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        addToListIfMatching(dir);
        return CONTINUE;
    }

    public Collection<Path> getMatchedPaths() {
        return matchedPaths;
    }

    private void addToListIfMatching(Path file) {
        Path name = basePath.relativize(file);
        if (name != null && matcher.matches(name)) {
            matchedPaths.add(name);
        }
    }
}
