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
package com.wavemaker.commons.io;

import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertThat;

/**
 * Tests for {@link ResourcePath}.
 *
 * @author Phillip Webb
 */
@Ignore
public class ResourcePathTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldCreateRoot() throws Exception {
        ResourcePath path = new ResourcePath();
        assertThat(path.getName(), Matchers.is(""));
        assertThat(path.toString(), Matchers.is(""));
    }

    @Test
    public void shouldNotCreateNullNested() throws Exception {
        this.thrown.expect(IllegalArgumentException.class);
        this.thrown.expectMessage("Path must not be empty");
        new ResourcePath().get(null);
    }

    @Test
    public void shouldNotCreateEmptyNested() throws Exception {
        this.thrown.expect(IllegalArgumentException.class);
        this.thrown.expectMessage("Path must not be empty");
        new ResourcePath().get("");
    }

    @Test
    public void shouldGetSimpleNested() throws Exception {
        ResourcePath path = new ResourcePath().get("a");
        assertThat(path.getName(), Matchers.is("a"));
        assertThat(path.toString(), Matchers.is("/a"));
    }

    @Test
    public void shouldGetDoubleNested() throws Exception {
        ResourcePath path = new ResourcePath().get("a").get("b");
        assertThat(path.getName(), Matchers.is("b"));
        assertThat(path.toString(), Matchers.is("/a/b"));
    }

    @Test
    public void shouldGetNestedString() throws Exception {
        ResourcePath path = new ResourcePath().get("a/b");
        assertThat(path.getName(), Matchers.is("b"));
        assertThat(path.toString(), Matchers.is("/a/b"));
    }

    @Test
    public void shouldGetRelative() throws Exception {
        ResourcePath path = new ResourcePath().get("a/b/../c");
        assertThat(path.getName(), Matchers.is("c"));
        assertThat(path.toString(), Matchers.is("/a/c"));
    }

    @Test
    public void shouldGetRelativeAtEnd() throws Exception {
        ResourcePath path = new ResourcePath().get("a/b/c/d/../..");
        assertThat(path.getName(), Matchers.is("b"));
        assertThat(path.toString(), Matchers.is("/a/b"));

    }

    @Test
    public void shouldNotAllowRelativePastRoot() throws Exception {
        this.thrown.expect(IllegalStateException.class);
        new ResourcePath().get("a/b/../../..");
    }

    @Test
    public void shouldSupportSlashAtFront() throws Exception {
        ResourcePath path = new ResourcePath().get("a").get("/b");
        assertThat(path.getName(), Matchers.is("b"));
        assertThat(path.toString(), Matchers.is("/b"));
    }

    @Test
    public void shouldSupportSlashAtEnd() throws Exception {
        ResourcePath path = new ResourcePath().get("a/b/");
        assertThat(path.getName(), Matchers.is("b"));
        assertThat(path.toString(), Matchers.is("/a/b"));
    }

    @Test
    public void shouldIgnoreDoubleSlash() throws Exception {
        ResourcePath path = new ResourcePath().get("a///b/");
        assertThat(path.getName(), Matchers.is("b"));
        assertThat(path.toString(), Matchers.is("/a/b"));
    }

    @Test
    public void shouldGetParent() throws Exception {
        ResourcePath path = new ResourcePath().get("a/b/").getParent();
        assertThat(path.getName(), Matchers.is("a"));
        assertThat(path.toString(), Matchers.is("/a"));
    }

    @Test
    public void shouldBeRootPath() throws Exception {
        ResourcePath path = new ResourcePath();
        assertThat(path.isRootPath(), Matchers.is(true));
    }

    @Test
    public void shouldNoteBeRootPath() throws Exception {
        ResourcePath path = new ResourcePath().get("a");
        assertThat(path.isRootPath(), Matchers.is(false));
    }

    @Test
    public void shouldAppendPath() throws Exception {
        ResourcePath root = new ResourcePath();
        ResourcePath ab = new ResourcePath().get("a/b");
        ResourcePath cd = new ResourcePath().get("c/d");
        assertThat(root.append(ab), Matchers.is(Matchers.equalTo(ab)));
        assertThat(ab.append(root), Matchers.is(Matchers.equalTo(ab)));
        assertThat(ab.append(cd), Matchers.is(Matchers.equalTo(new ResourcePath().get("a/b/c/d"))));
    }

    @Test
    public void shouldReturnToStringRelativeToOther() throws Exception {
        ResourcePath abcd = new ResourcePath().get("a/b/c/d");
        assertThat(abcd.toStringRelativeTo("/a/b/"), Matchers.is("c/d"));
    }

    @Test
    public void shouldReturnEmptyToStringRelativeToSelf() throws Exception {
        ResourcePath ab = new ResourcePath().get("a/b");
        assertThat(ab.toStringRelativeTo("/a/b/"), Matchers.is(""));
    }

    @Test
    public void shouldNotReturnToStringRelativeToNonParent() throws Exception {
        ResourcePath abcd = new ResourcePath().get("a/b/c/d");
        this.thrown.expect(IllegalArgumentException.class);
        this.thrown.expectMessage("Source '/a/c' must be a parent of '/a/b/c/d'");
        abcd.toStringRelativeTo("/a/c/");
    }

}
