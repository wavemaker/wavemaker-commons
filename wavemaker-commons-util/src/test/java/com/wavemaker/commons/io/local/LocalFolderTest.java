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
package com.wavemaker.commons.io.local;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.wavemaker.commons.io.File;
import com.wavemaker.commons.io.FilterOn;
import com.wavemaker.commons.io.Folder;
import com.wavemaker.commons.io.Resource;
import com.wavemaker.commons.io.Resources;
import com.wavemaker.commons.io.exception.ResourceException;
import com.wavemaker.commons.util.WMIOUtils;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link com.wavemaker.commons.io.local.LocalFolder}.
 *
 * @author Phillip Webb
 */
public class LocalFolderTest {

    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    @Rule
    public TemporaryFolder dest = new TemporaryFolder();

    private LocalFolder root;

    @Before
    public void setup() {
        this.root = new LocalFolder(this.temp.getRoot());
        this.root.getFile("/a/b/c.txt").getContent().write("c");
        this.root.getFile("/d/e/f.txt").getContent().write("d");
        this.root.getFile("/g.txt").getContent().write("g");
    }

    @Test
    public void shouldFind() throws Exception {
        List<Resource> all = this.root.find().fetchAll();
        Set<String> actual = getNames(all);
        Set<String> expected = new HashSet<>();
        expected.add("/a/");
        expected.add("/a/b/");
        expected.add("/a/b/c.txt");
        expected.add("/d/");
        expected.add("/d/e/");
        expected.add("/d/e/f.txt");
        expected.add("/g.txt");
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldFindSingle() throws Exception {
        List<Resource> all = this.root.getFolder("a/b").find().fetchAll();
        Set<String> actual = getNames(all);
        Set<String> expected = new HashSet<>();
        expected.add("/a/b/c.txt");
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldFindFiles() throws Exception {
        List<File> all = this.root.find().files().fetchAll();
        Set<String> actual = getNames(all);
        Set<String> expected = new HashSet<>();
        expected.add("/a/b/c.txt");
        expected.add("/d/e/f.txt");
        expected.add("/g.txt");
        assertThat(actual, is(expected));
    }

    @Test
    public void shouldFindFilesTwice() throws Exception {
        // WM-4280
        Resources<File> files = this.root.find().files();
        List<File> all1 = files.fetchAll();
        List<File> all2 = files.fetchAll();
        assertThat(all1.size(), is(all2.size()));
    }

    @Test
    public void shouldCopy() throws Exception {
        Folder destination = new LocalFolder(this.dest.getRoot());
        this.root.find().files().exclude(FilterOn.names().starting("f")).copyTo(destination);
        Set<String> actual = getNames(destination.find());
        Set<String> expected = new HashSet<>();
        expected.add("/a/");
        expected.add("/a/b/");
        expected.add("/a/b/c.txt");
        expected.add("/g.txt");
        assertThat(actual, is(expected));
    }

    private Set<String> getNames(Iterable<? extends Resource> resources) {
        Set<String> allNames = new HashSet<>();
        for (Resource resource : resources) {
            allNames.add(resource.toString());
        }
        return allNames;
    }

    @Test
    public void shouldUseUnderlyingResourceForEqualsAndHashCode() throws Exception {
        Folder folder1 = new LocalFolder(this.temp.getRoot()).getFolder("folder");
        folder1.createIfMissing();
        Folder folder2 = new LocalFolder(new java.io.File(this.temp.getRoot(), "folder"));
        Folder folder3 = new LocalFolder(this.temp.getRoot()).getFolder("xfolder");
        File file1 = folder1.getFile("file");
        File file2 = folder2.getFile("file");
        File file3 = folder3.getFile("file");
        file1.createIfMissing();
        file2.createIfMissing();
        file3.createIfMissing();

        assertThat(folder1, is(equalTo(folder1)));
        assertThat(folder1, is(equalTo(folder2)));
        assertThat(folder1, is(not(equalTo(folder3))));
        assertThat(file1, is(equalTo(file1)));
        assertThat(file1, is(equalTo(file2)));
        assertThat(file1, is(not(equalTo(file3))));

        assertThat(folder1.hashCode(), is(equalTo(folder1.hashCode())));
        assertThat(folder1.hashCode(), is(equalTo(folder2.hashCode())));
        assertThat(folder1.hashCode(), is(not(equalTo(folder3.hashCode()))));
        assertThat(file1.hashCode(), is(equalTo(file1.hashCode())));
        assertThat(file1.hashCode(), is(equalTo(file2.hashCode())));
        assertThat(file1.hashCode(), is(not(equalTo(file3.hashCode()))));
    }

    @Test
    public void shouldNotCreateMissingFileWhenGettingAsString() throws Exception {
        // WM-4290
        Folder folder = this.root.getFolder("test");
        File file = folder.getFile("test.txt");
        assertThat(folder.exists(), is(false));
        try {
            file.getContent().asString();
        } catch (ResourceException e) {
        }
        assertThat(folder.exists(), is(false));
    }

    @Test
    public void testNewLocalFolder() {
        Folder folder = new LocalFolder(this.temp.getRoot() + java.io.File.separator + "test");
        Folder childFolder = folder.getFolder("abcd");
        File file = childFolder.getFile("jfsdnfdjk");
        file.createIfMissing();

        File file1 = folder.getFile("njvkdf nhvd.txt");
        file1.createIfMissing();
        Assert.assertTrue("Could not create a local file", file1.exists());
    }

    @Test
    public void testDeleteFolder() throws IOException {
        Folder folder = new LocalFolder(this.temp.getRoot() + java.io.File.separator + UUID.randomUUID());
        Folder aFolder = folder.getFolder("a");
        File fileA = aFolder.getFile("a.txt");
        fileA.getContent().write("Hello world");

        Folder cFolder = folder.getFolder("c");
        File fileC = cFolder.getFile("c.txt");
        fileC.getContent().write("Hello world");

        Folder bFolder = folder.getFolder("b");
        bFolder.createIfMissing();

        File b1File = bFolder.getFile("b1.txt");
        Files.createSymbolicLink(WMIOUtils.getJavaIOFile(b1File).toPath(), WMIOUtils.getJavaIOFile(fileA).toPath());//Creating a valid symlink

        File b2File = bFolder.getFile("b2.txt");
        Files.createSymbolicLink(WMIOUtils.getJavaIOFile(b2File).toPath(), WMIOUtils.getJavaIOFile(fileC).toPath());
        cFolder.delete();//Create a missing symlink

        folder.delete();
        Assert.assertFalse("folder is not deleted", folder.exists());
    }
}
