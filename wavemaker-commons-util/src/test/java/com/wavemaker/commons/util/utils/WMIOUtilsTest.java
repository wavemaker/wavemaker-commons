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
package com.wavemaker.commons.util.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wavemaker.commons.util.FileAccessException;
import com.wavemaker.commons.util.WMFileUtils;
import com.wavemaker.commons.util.WMIOUtils;
import com.wavemaker.commons.util.WMTestUtils;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @author Matt Small
 */
public class WMIOUtilsTest {

    private File tempDir;

    @BeforeClass
    public void setUp() throws Exception {
        this.tempDir = WMIOUtils.createTempDirectory();
    }

    @AfterClass
    public void tearDown() throws Exception {

        WMIOUtils.deleteRecursive(this.tempDir);
    }

    @Test
    public void createTempDirectoryTest() throws Exception {

        File newTempDir = WMIOUtils.createTempDirectory();
        assertTrue(newTempDir.exists());
        newTempDir.delete();
        assertFalse(newTempDir.exists());
    }

    @Test
    public void deleteOneLevelTest() throws Exception {

        File newTempDir = WMIOUtils.createTempDirectory();
        assertTrue(newTempDir.exists());
        WMIOUtils.deleteRecursive(newTempDir);
        assertFalse(newTempDir.exists());
    }

    @Test
    public void deleteTwoLevelsTest() throws Exception {

        File newTempDir = WMIOUtils.createTempDirectory();
        assertTrue(newTempDir.exists());
        File newTempDir2 = new File(newTempDir, "foobar");
        newTempDir2.mkdir();
        File newTempFile = new File(newTempDir2, "foo.txt");
        newTempFile.createNewFile();
        WMIOUtils.deleteRecursive(newTempDir);
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

        WMIOUtils.makeDirectories(wantToCreate.getParentFile(), this.tempDir);

        assertTrue(wantToCreate.getParentFile().exists());

        wantToCreate.createNewFile();

        File alreadyExists = new File(this.tempDir, "bar");
        alreadyExists.mkdir();
        WMIOUtils.makeDirectories(alreadyExists, wantToCreate);
    }

    @Test
    public void badMakeDirectoriesTest() {

        File wantToCreate = new File("/_foobarblahgoo_/bar");

        boolean gotException = false;
        try {
            WMIOUtils.makeDirectories(wantToCreate.getParentFile(), this.tempDir);
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
        WMIOUtils.touch(f);
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

        WMIOUtils.touch(f);

        assertTrue(lastModified < f.lastModified());
    }

    @Test
    public void touchDirTest() throws Exception {

        File f = null;

        try {
            f = WMIOUtils.createTempDirectory();

            long lastModified = f.lastModified();

            // UNIX counts in seconds
            Thread.sleep(3000);

            WMIOUtils.touch(f);

            assertTrue(lastModified < f.lastModified());
        } finally {
            if (f != null) {
                WMIOUtils.deleteRecursive(f);
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

        WMIOUtils.copy(source, dest);
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

        List<String> excludes = new ArrayList<>();
        excludes.add(source.getName());
        WMIOUtils.copy(source, dest, excludes);
        assertFalse(dest.exists());
    }

    @Test
    public void copyDirectoriesTest() throws Exception {

        File source = WMIOUtils.createTempDirectory("copyDirectoriesSrcTest");
        File dest = WMIOUtils.createTempDirectory("copyDirectoriesSrcTest");

        try {
            WMIOUtils.deleteRecursive(dest);
            assertTrue(source.exists());
            assertTrue(source.isDirectory());
            assertFalse(dest.exists());

            File sourceFile = new File(source, "foo");
            WMFileUtils.writeStringToFile(sourceFile, "foo");
            File sourceDir = new File(source, "bar");
            sourceDir.mkdir();
            File sourceDirFile = new File(sourceDir, "barfile");
            WMFileUtils.writeStringToFile(sourceDirFile, "foobarbaz");

            WMIOUtils.copy(source, dest);
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
            WMIOUtils.deleteRecursive(source);
            WMIOUtils.deleteRecursive(dest);
        }
    }

    @Test
    public void copyDirectoriesExcludesTest() throws Exception {

        String excludeName = "DoNotCopy";
        List<String> excludes = new ArrayList<>();
        excludes.add(excludeName);

        File source = WMIOUtils.createTempDirectory("copyDirectoriesSrcTest");
        File dest = WMIOUtils.createTempDirectory("copyDirectoriesSrcTest");

        try {
            WMIOUtils.deleteRecursive(dest);
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

            WMIOUtils.copy(source, dest, excludes);
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
            WMIOUtils.deleteRecursive(source);
            WMIOUtils.deleteRecursive(dest);
        }
    }

    @Test(dataProvider = "streamProvider")
    public void copyStreamTest(String inputString) throws IOException {
        ByteArrayInputStream byteArrayInputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(inputString.getBytes());
            byteArrayOutputStream = new ByteArrayOutputStream();
            int size = WMIOUtils.copy(byteArrayInputStream, byteArrayOutputStream);
            Assert.assertEquals(size, inputString.length());
            Assert.assertEquals(byteArrayOutputStream.toString(), inputString);
        } finally {
            WMIOUtils.closeSilently(byteArrayInputStream);
            WMIOUtils.closeSilently(byteArrayOutputStream);
        }
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
        WMIOUtils.closeSilently(byteArrayInputStream);
        assertTrue(isClosed[0]);

    }

    @Test(dataProvider = "streamProvider")
    public void closeByLoggingTest(String inputString) {
        final boolean[] isClosed = {false};
        ByteArrayInputStream byteArrayInputStream = getByteArrayInputStream(inputString, isClosed);
        WMIOUtils.closeByLogging(byteArrayInputStream);
        assertTrue(isClosed[0]);

    }

    @Test
    public void testTail() throws IOException {
        File file = new File("target", "testFileOne.txt");
        WMIOUtils.write(file, "adding data to a file\ndata1\ndata2");
        Assert.assertEquals(WMIOUtils.tail(file, 2), "data1\ndata2\n");

    }

    @Test
    public void testCompare() throws IOException {
        File file = new File("target", "testFileOne.txt");
        WMIOUtils.write(file, "adding data to a file\ndata1\ndata2");
        File tempFile = new File("target", "temporary.txt");
        WMIOUtils.write(tempFile, "it is a temporary file");
        Assert.assertTrue(WMIOUtils.compare(new FileInputStream(file), new FileInputStream(file)));
        Assert.assertFalse(WMIOUtils.compare(new FileInputStream(file), new FileInputStream(tempFile)));
    }

    @Test
    public void testCopy() throws IOException {
        File sourceFile = new File("target", "source.txt");
        File destFile = new File("target", "dest.txt");
        WMIOUtils.write(sourceFile, "temporary file inputs fo copy test");
        WMIOUtils.copy(sourceFile, destFile, "noExcludeInput", "noExcludePattern");
        Assert.assertTrue(WMIOUtils.compare(new FileInputStream(sourceFile), new FileInputStream(destFile)));
    }

    @Test
    public void testUtf8CharWriteToFile() throws IOException {
        File file = new File("target", "testUtf8Char.txt");
        WMIOUtils.write(file, "కొన్ని రాండమ్ డాటాతో ఫైల్ను జనాదరణ పొందింది\nలైన్ 1\nలైన్ 2");
        Assert.assertEquals(WMIOUtils.read(file), "కొన్ని రాండమ్ డాటాతో ఫైల్ను జనాదరణ పొందింది\nలైన్ 1\nలైన్ 2");
        Assert.assertEquals(WMIOUtils.tail(file, 2), "లైన్ 1\nలైన్ 2\n");
    }

    @Test
    public void testUtf8CharWriteToOutputStream() throws IOException {
        File file = new File("target", "testUtf8Char.txt");
        WMIOUtils.write(new FileOutputStream(file), "నమూనా డేటా");
        Assert.assertEquals(WMIOUtils.read(file), "నమూనా డేటా");
    }

    @Test
    public void testUtf8CharCopyStream() throws IOException {
        final boolean[] isClosed = {false};
        File file = new File("target", "testUtf8Char.txt");
        WMIOUtils.copy(getByteArrayInputStream("నమూనా డేటా", isClosed), new FileOutputStream(file));
        Assert.assertEquals(WMIOUtils.read(file), "నమూనా డేటా");
    }

    @Test
    public void testUtf8CharCopy() throws IOException {
        File file = new File("target", "testUtf8Char.txt");
        FileWriter fileWriter = new FileWriter(file);
        WMIOUtils.copy(new StringReader("నమూనా డేటా"), fileWriter);
        fileWriter.close();
        Assert.assertEquals(WMIOUtils.read(file), "నమూనా డేటా");
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
