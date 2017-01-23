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
package com.wavemaker.studio.common.io;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import com.wavemaker.studio.common.io.JailedResourcePath;
import com.wavemaker.studio.common.io.ResourcePath;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link JailedResourcePath}.
 * 
 * @author Phillip Webb
 */
public class JailedResourcePathTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ResourcePath pathA = new ResourcePath().get("a");

    private final ResourcePath pathB = new ResourcePath().get("b");

    @Test
    public void shouldNeedJail() throws Exception {
        this.thrown.expect(IllegalArgumentException.class);
        this.thrown.expectMessage("JailPath must not be null");
        new JailedResourcePath(null, new ResourcePath());
    }

    @Test
    public void shouldNeedPath() throws Exception {
        this.thrown.expect(IllegalArgumentException.class);
        this.thrown.expectMessage("Path must not be null");
        new JailedResourcePath(new ResourcePath(), null);
    }

    @Test
    public void shouldCreateFromJailAndPath() throws Exception {
        JailedResourcePath jailedResourcePath = new JailedResourcePath(this.pathA, this.pathB);
        assertThat(jailedResourcePath.getJailPath(), Matchers.is(this.pathA));
        assertThat(jailedResourcePath.getPath(), Matchers.is(this.pathB));
    }

    @Test
    public void shouldCreateRoot() throws Exception {
        JailedResourcePath jailedResourcePath = new JailedResourcePath();
        assertThat(jailedResourcePath.getJailPath().isRootPath(), Matchers.is(true));
        assertThat(jailedResourcePath.getPath().isRootPath(), Matchers.is(true));
    }

    @Test
    public void shouldGetNullParent() throws Exception {
        JailedResourcePath jailedResourcePath = new JailedResourcePath(this.pathA, new ResourcePath());
        assertThat(jailedResourcePath.getParent(), Matchers.is(Matchers.nullValue()));
    }

    @Test
    public void shouldGetParent() throws Exception {
        JailedResourcePath jailedResourcePath = new JailedResourcePath(this.pathA, new ResourcePath().get("b/c"));
        JailedResourcePath expected = new JailedResourcePath(this.pathA, this.pathB);
        assertThat(jailedResourcePath.getParent(), Matchers.is(Matchers.equalTo(expected)));
    }

    @Test
    public void shouldGet() throws Exception {
        JailedResourcePath jailedResourcePath = new JailedResourcePath(this.pathA, new ResourcePath());
        jailedResourcePath = jailedResourcePath.get("b/c");
        JailedResourcePath expected = new JailedResourcePath(this.pathA, new ResourcePath().get("b/c"));
        assertThat(jailedResourcePath, Matchers.is(Matchers.equalTo(expected)));

    }

    @Test
    public void shouldGetUnjailedRootPath() throws Exception {
        JailedResourcePath jailedResourcePath = new JailedResourcePath(new ResourcePath(), this.pathA);
        assertThat(jailedResourcePath.getUnjailedPath(), Matchers.is(Matchers.equalTo(new ResourcePath().get("a"))));
    }

    @Test
    public void shouldGetUnjailedPath() throws Exception {
        JailedResourcePath jailedResourcePath = new JailedResourcePath(this.pathA, this.pathB);
        assertThat(jailedResourcePath.getUnjailedPath(), Matchers.is(Matchers.equalTo(new ResourcePath().get("a/b"))));
    }

    @Test
    public void shouldUsePathToString() throws Exception {
        JailedResourcePath jailedResourcePath = new JailedResourcePath(this.pathA, this.pathB);
        assertThat(jailedResourcePath.toString(), Matchers.is(Matchers.equalTo("/b")));
    }
}
