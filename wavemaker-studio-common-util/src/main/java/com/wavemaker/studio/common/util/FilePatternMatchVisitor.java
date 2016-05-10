package com.wavemaker.studio.common.util;

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
public class FilePatternMatchVisitor extends SimpleFileVisitor<Path>{

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
