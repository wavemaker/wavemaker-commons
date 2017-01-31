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
package com.wavemaker.commons.util.utils;


import com.wavemaker.commons.util.WMTestUtils;
import com.wavemaker.commons.util.FileAccessException;
import com.wavemaker.commons.util.IOUtils;
import com.wavemaker.commons.util.SpringUtils;
import com.wavemaker.commons.util.WMFileUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @author Matt Small
 */
public class IOUtilsTest {

    private File tempDir;

    @BeforeClass
    public void setUp() throws Exception {

        try {
            this.tempDir = IOUtils.createTempDirectory();
            SpringUtils.initSpringConfig();
        } catch (RuntimeException e) {
            IOUtils.deleteRecursive(this.tempDir);
            throw e;
        }
    }

    public void tearDown() throws Exception {

        IOUtils.deleteRecursive(this.tempDir);
    }

    @Test
    public void createTempDirectory_shortPrefixTest() throws Exception {

        File newTempDir = IOUtils.createTempDirectory("a", "bcd");
        assertTrue(newTempDir.getName().startsWith("aaa"));
    }

    @Test
    public void createTempDirectoryTest() throws Exception {

        File newTempDir = IOUtils.createTempDirectory();
        assertTrue(newTempDir.exists());
        newTempDir.delete();
        assertFalse(newTempDir.exists());
    }

    @Test
    public void deleteOneLevelTest() throws Exception {

        File newTempDir = IOUtils.createTempDirectory();
        assertTrue(newTempDir.exists());
        IOUtils.deleteRecursive(newTempDir);
        assertFalse(newTempDir.exists());
    }

    @Test
    public void deleteTwoLevelsTest() throws Exception {

        File newTempDir = IOUtils.createTempDirectory();
        assertTrue(newTempDir.exists());
        File newTempDir2 = new File(newTempDir, "foobar");
        newTempDir2.mkdir();
        File newTempFile = new File(newTempDir2, "foo.txt");
        newTempFile.createNewFile();
        IOUtils.deleteRecursive(newTempDir);
        assertFalse(newTempDir.exists());
    }

    @Test
    public void makeDirectoriesTest() throws Exception {

        File wantToCreate = new File(this.tempDir, "foo/bar/baz.txt");

        boolean gotException = false;
        try {
            wantToCreate.createNewFile();
        } catch (Exception e) {
            gotException = true;
        }
        assertTrue(gotException);

        IOUtils.makeDirectories(wantToCreate.getParentFile(), this.tempDir);

        assertTrue(wantToCreate.getParentFile().exists());

        wantToCreate.createNewFile();

        File alreadyExists = new File(this.tempDir, "bar");
        alreadyExists.mkdir();
        IOUtils.makeDirectories(alreadyExists, wantToCreate);
    }

    @Test
    public void badMakeDirectoriesTest() {

        File wantToCreate = new File("/_foobarblahgoo_/bar");

        boolean gotException = false;
        try {
            IOUtils.makeDirectories(wantToCreate.getParentFile(), this.tempDir);
        } catch (FileAccessException ex) {
            gotException = true;
            assertTrue(ex.getMessage().startsWith("Reached filesystem root"), "got message: " + ex.getMessage());
        }
        assertTrue(gotException);
    }

    @Test
    public void touchDNETest() throws Exception {

        File f = File.createTempFile("touchDNE", "tmp");
        f.delete();
        f.deleteOnExit();
        assertTrue(!f.exists());
        IOUtils.touch(f);
        assertTrue(f.exists());
    }

    @Test
    public void touchTest() throws Exception {

        File f = File.createTempFile("touch", "tmp");
        f.deleteOnExit();
        assertTrue(f.exists());

        long lastModified = f.lastModified();

        // UNIX counts in seconds
        Thread.sleep(3000);

        IOUtils.touch(f);

        assertTrue(lastModified < f.lastModified());
    }

    @Test
    public void touchDirTest() throws Exception {

        File f = null;

        try {
            f = IOUtils.createTempDirectory();

            long lastModified = f.lastModified();

            // UNIX counts in seconds
            Thread.sleep(3000);

            IOUtils.touch(f);

            assertTrue(lastModified < f.lastModified());
        } finally {
            if (f != null) {
                IOUtils.deleteRecursive(f);
            }
        }
    }

    @Test
    public void copyFilesTest() throws Exception {

        File source = File.createTempFile("copyFilesSrcTest", ".tmp");
        File dest = File.createTempFile("copyFilesDestTest", ".tmp");
        source.deleteOnExit();
        dest.delete();
        dest.deleteOnExit();
        assertFalse(dest.exists());

        WMFileUtils.writeStringToFile(source, "foo");

        IOUtils.copy(source, dest);
        assertTrue(dest.exists());
        WMTestUtils.assertEquals(source, dest);
    }

    @Test
    public void copyFilesExcludesTest() throws Exception {

        File source = File.createTempFile("testCopyFilesSrc", ".tmp");
        File dest = File.createTempFile("testCopyFilesDest", ".tmp");
        source.deleteOnExit();
        dest.delete();
        dest.deleteOnExit();
        assertFalse(dest.exists());

        WMFileUtils.writeStringToFile(source, "foo");

        List<String> excludes = new ArrayList<String>();
        excludes.add(source.getName());
        IOUtils.copy(source, dest, excludes);
        assertFalse(dest.exists());
    }

    @Test
    public void copyDirectoriesTest() throws Exception {

        File source = IOUtils.createTempDirectory("copyDirectoriesSrcTest", "");
        File dest = IOUtils.createTempDirectory("copyDirectoriesSrcTest", "");

        try {
            IOUtils.deleteRecursive(dest);
            assertTrue(source.exists());
            assertTrue(source.isDirectory());
            assertFalse(dest.exists());

            File sourceFile = new File(source, "foo");
            WMFileUtils.writeStringToFile(sourceFile, "foo");
            File sourceDir = new File(source, "bar");
            sourceDir.mkdir();
            File sourceDirFile = new File(sourceDir, "barfile");
            WMFileUtils.writeStringToFile(sourceDirFile, "foobarbaz");

            IOUtils.copy(source, dest);
            assertTrue(dest.exists());
            assertTrue(dest.isDirectory());

            File destFile = new File(dest, "foo");
            File destDir = new File(dest, "bar");
            File destDirFile = new File(destDir, "barfile");
            assertTrue(destFile.exists());
            WMTestUtils.assertEquals(sourceFile, destFile);
            assertTrue(destDir.exists());
            assertTrue(destDir.isDirectory());
            assertTrue(destDirFile.exists());
            WMTestUtils.assertEquals(sourceDirFile, destDirFile);
        } finally {
            IOUtils.deleteRecursive(source);
            IOUtils.deleteRecursive(dest);
        }
    }

    @Test
    public void copyDirectoriesExcludesTest() throws Exception {

        String excludeName = "DoNotCopy";
        List<String> excludes = new ArrayList<String>();
        excludes.add(excludeName);

        File source = IOUtils.createTempDirectory("copyDirectoriesSrcTest", "");
        File dest = IOUtils.createTempDirectory("copyDirectoriesSrcTest", "");

        try {
            IOUtils.deleteRecursive(dest);
            assertTrue(source.exists());
            assertTrue(source.isDirectory());
            assertFalse(dest.exists());

            File sourceFile = new File(source, "foo");
            WMFileUtils.writeStringToFile(sourceFile, "foo");
            File sourceDir = new File(source, "bar");
            sourceDir.mkdir();
            File sourceDirFile = new File(sourceDir, "barfile");
            WMFileUtils.writeStringToFile(sourceDirFile, "foobarbaz");
            File sourceExcludeDir = new File(source, excludeName);
            sourceExcludeDir.mkdir();
            File sourceExcludeFile = new File(sourceDir, excludeName);
            WMFileUtils.writeStringToFile(sourceExcludeFile, "a");

            IOUtils.copy(source, dest, excludes);
            assertTrue(dest.exists());
            assertTrue(dest.isDirectory());

            File destFile = new File(dest, "foo");
            File destDir = new File(dest, "bar");
            File destDirFile = new File(destDir, "barfile");
            File destExcludeDir = new File(dest, excludeName);
            File destExcludeFile = new File(destDir, excludeName);
            assertTrue(destFile.exists());
            WMTestUtils.assertEquals(sourceFile, destFile);
            assertTrue(destDir.exists());
            assertTrue(destDir.isDirectory());
            assertTrue(destDirFile.exists());
            WMTestUtils.assertEquals(sourceDirFile, destDirFile);
            assertFalse(destExcludeDir.exists());
            assertFalse(destExcludeFile.exists());
        } finally {
            IOUtils.deleteRecursive(source);
            IOUtils.deleteRecursive(dest);
        }
    }

    @Test
    public void exclusionByExactMatchTest() {

        assertTrue(IOUtils.excludeByExactMatch(new File("/foo/bar/" + IOUtils.DEFAULT_EXCLUSION.get(0))));
        assertFalse(IOUtils.excludeByExactMatch(new File("/foo/bar/" + IOUtils.DEFAULT_EXCLUSION.get(0) + ".foo")));
    }

    @Test(dataProvider = "streamProvider")
    public void copyStreamTest(String inputString) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(inputString.getBytes());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int size = IOUtils.copy(byteArrayInputStream, byteArrayOutputStream, true, true);
        Assert.assertEquals(size, inputString.length());
        Assert.assertEquals(byteArrayOutputStream.toString(), inputString);
    }


    @DataProvider
    public Object[][] streamProvider() {
        Object[][] obj = new Object[2][1];
        obj[0][0] = UUID.randomUUID().toString();
        obj[1][0] = "";

        return obj;
    }

    @Test(dataProvider = "streamProvider")
    public void closeSilentlyTest(String inputString) {
        final boolean[] isClosed = {false};
        ByteArrayInputStream byteArrayInputStream = getByteArrayInputStream(inputString, isClosed);
        IOUtils.closeSilently(byteArrayInputStream);
        assertTrue(isClosed[0]);

    }

    @Test(dataProvider = "streamProvider")
    public void closeByLoggingTest(String inputString) {
        final boolean[] isClosed = {false};
        ByteArrayInputStream byteArrayInputStream = getByteArrayInputStream(inputString, isClosed);
        IOUtils.closeByLogging(byteArrayInputStream);
        assertTrue(isClosed[0]);

    }

    private ByteArrayInputStream getByteArrayInputStream(final String inputString, final boolean[] isClosed) {
        return new ByteArrayInputStream(inputString.getBytes()) {

            @Override
            public void close() throws IOException {
                super.close();
                isClosed[0] = true;
            }
        };
    }


}
