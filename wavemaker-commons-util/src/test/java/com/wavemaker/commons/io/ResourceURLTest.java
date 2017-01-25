/**
 * Copyright (C) 2015 WaveMaker, Inc.
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
package com.wavemaker.commons.io;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import com.wavemaker.commons.io.local.LocalFolder;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link ResourceURL}.
 * 
 * @author Phillip Webb
 */
public class ResourceURLTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Folder root;

    @Before
    public void setup() throws Exception {
        this.root = new LocalFolder(this.temporaryFolder.newFolder("fs"));
        this.root.getFile("/jail/a/b/c.txt").getContent().write("c");
        this.root.getFile("/jail/x/y/z.txt").getContent().write("z");
    }

    @Test
    public void shouldGetFileURL() throws Exception {
        URL url = ResourceURL.get(this.root.getFile("/jail/a/b/c.txt"));
        assertThat(url.toString(), Matchers.is(Matchers.equalTo("rfs:/jail/a/b/c.txt")));
    }

    @Test
    public void shouldGetFolderURL() throws Exception {
        URL url = ResourceURL.get(this.root.getFolder("/jail/a/b"));
        assertThat(url.toString(), Matchers.is(Matchers.equalTo("rfs:/jail/a/b/")));
    }

    @Test
    public void shouldGetJailedURL() throws Exception {
        Folder jail = this.root.getFolder("jail").jail();
        URL url = ResourceURL.get(jail.getFile("/a/b/c.txt"));
        assertThat(url.toString(), Matchers.is(Matchers.equalTo("rfs:/a/b/c.txt")));
    }

    @Test
    public void shouldOpenStream() throws Exception {
        URL url = ResourceURL.get(this.root.getFile("/jail/a/b/c.txt"));
        assertThat(IOUtils.toString(url.openStream()), Matchers.is(Matchers.equalTo("c")));
    }

    @Test
    public void shouldOpenJailedStream() throws Exception {
        Folder jail = this.root.getFolder("jail").jail();
        URL url = ResourceURL.get(jail.getFile("/a/b/c.txt"));
        assertThat(IOUtils.toString(url.openStream()), Matchers.is(Matchers.equalTo("c")));
    }

    @Test
    public void shouldCreateRelativeToFolder() throws Exception {
        Folder jail = this.root.getFolder("jail").jail();
        URL ab = ResourceURL.get(jail.getFolder("a/b"));
        URL url = new URL(ab, "c.txt");
        assertThat(url.toString(), Matchers.is(Matchers.equalTo("rfs:/a/b/c.txt")));
        assertThat(IOUtils.toString(url.openStream()), Matchers.is(Matchers.equalTo("c")));
    }

    @Test
    public void shouldCreateExactToFolder() throws Exception {
        Folder jail = this.root.getFolder("jail").jail();
        URL ab = ResourceURL.get(jail.getFolder("a/b"));
        URL url = new URL(ab, "/x/y/z.txt");
        assertThat(url.toString(), Matchers.is(Matchers.equalTo("rfs:/x/y/z.txt")));
        assertThat(IOUtils.toString(url.openStream()), Matchers.is(Matchers.equalTo("z")));
    }

    @Test
    public void shouldCreateRelativeToFile() throws Exception {
        Folder jail = this.root.getFolder("jail").jail();
        URL abc = ResourceURL.get(jail.getFile("a/b/c.txt"));
        URL url = new URL(abc, "/x/y/z.txt");
        assertThat(url.toString(), Matchers.is(Matchers.equalTo("rfs:/x/y/z.txt")));
        assertThat(IOUtils.toString(url.openStream()), Matchers.is(Matchers.equalTo("z")));
    }

    @Test
    public void shouldThrowIOExceptionIfFileDoesNotExist() throws Exception {
        URL url = ResourceURL.get(this.root.getFile("doesnotexist.txt"));
        this.thrown.expect(IOException.class);
        this.thrown.expectMessage("File '/doesnotexist.txt' does not exist");
        url.openConnection();
    }

    @Test
    public void shouldThrowIOOpeningFolder() throws Exception {
        URL url = ResourceURL.get(this.root.getFolder("/jail/a/b"));
        this.thrown.expect(IOException.class);
        this.thrown.expectMessage("Unable to open URL connection to folder '/jail/a/b/'");
        url.openConnection();
    }

    @Test
    public void shouldWorkViaClassLoader() throws Exception {
        Folder jail = this.root.getFolder("jail").jail();
        URLClassLoader classLoader = new URLClassLoader(new URL[] { ResourceURL.get(jail) });
        assertThat(IOUtils.toString(classLoader.getResourceAsStream("/a/b/c.txt")), Matchers.is(Matchers.equalTo("c")));
        assertThat(classLoader.getResource("/x/y/z.txt").toString(), Matchers.is(Matchers.equalTo("rfs:/x/y/z.txt")));
    }

    @Test
    public void shouldGetForCollection() throws Exception {
        List<Folder> folders = new ArrayList<Folder>();
        folders.add(this.root.getFolder("/jail/a"));
        folders.add(this.root.getFolder("/jail/a/b"));
        List<URL> url = ResourceURL.getForResources(folders);
        assertThat(url.size(), Matchers.is(2));
        assertThat(url.get(0).toString(), Matchers.is(Matchers.equalTo("rfs:/jail/a/")));
        assertThat(url.get(1).toString(), Matchers.is(Matchers.equalTo("rfs:/jail/a/b/")));
    }
}
