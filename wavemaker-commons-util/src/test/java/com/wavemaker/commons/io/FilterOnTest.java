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
package com.wavemaker.commons.io;


import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.wavemaker.commons.io.FilterOn.PathStyle;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;

/**
 * Tests for {@link FilterOn}.
 *
 * @author Phillip Webb
 */
public class FilterOnTest {

    @Mock
    private ResourceFilterContext context;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldSupportCompoundFilters() throws Exception {
        ResourceFilter filter = FilterOn.names().starting("~").ending(".tmp", ".bak").notContaining("keep");
        assertThat(filter.match(this.context, fileWithName("~file.tmp")), is(true));
        assertThat(filter.match(this.context, fileWithName("~file.bak")), is(true));
        assertThat(filter.match(this.context, fileWithName("file.tmp")), is(false));
        assertThat(filter.match(this.context, fileWithName("~file.dat")), is(false));
        assertThat(filter.match(this.context, fileWithName("~xxkeepxx.bak")), is(false));
    }

    @Test
    public void shouldFilterHiddenResources() throws Exception {
        ResourceFilter filter = FilterOn.hidden();
        assertThat(filter.match(this.context, fileWithName(".hidden")), is(true));
        assertThat(filter.match(this.context, fileWithName("nothidden")), is(false));
        assertThat(filter.match(this.context, folderWithName(".hidden")), is(true));
        assertThat(filter.match(this.context, folderWithName("nothidden")), is(false));
    }

    @Test
    public void shouldFilterNonHiddenResources() throws Exception {
        ResourceFilter filter = FilterOn.nonHidden();
        assertThat(filter.match(this.context, fileWithName(".hidden")), is(false));
        assertThat(filter.match(this.context, fileWithName("nothidden")), is(true));
        assertThat(filter.match(this.context, folderWithName(".hidden")), is(false));
        assertThat(filter.match(this.context, folderWithName("nothidden")), is(true));
    }

    @Test
    public void shouldFilterOnPatternWithFullPathStyle() throws Exception {
        ResourceFilter filter = FilterOn.antPattern(PathStyle.FULL, "/dojo/**/tests/**");
        assertThat(filter.match(this.context, folderWithPath("/dojo/folder/tests/file.js")), is(true));
        assertThat(filter.match(this.context, folderWithPath("/dojo/some/folder/tests/file.js")), is(true));
        assertThat(filter.match(this.context, folderWithPath("/dojo/some/folder/tests/another/file.js")), is(true));
    }

    @Test
    public void shouldFilterOnPattern() throws Exception {
        Folder source = folderWithPath("/x/");
        BDDMockito.given(this.context.getSource()).willReturn(source);
        ResourceFilter filter = FilterOn.antPattern("dojo/**/tests/**");
        assertThat(filter.match(this.context, folderWithPath("/x/dojo/folder/tests/file.js")), is(true));
        assertThat(filter.match(this.context, folderWithPath("/x/dojo/some/folder/tests/file.js")), is(true));
        assertThat(filter.match(this.context, folderWithPath("/x/dojo/some/folder/tests/another/file.js")), is(true));
    }

    @Test
    public void shouldFilterOnPatternWithExtraSlash() throws Exception {
        Folder source = folderWithPath("/");
        BDDMockito.given(this.context.getSource()).willReturn(source);
        ResourceFilter filter = FilterOn.antPattern("/dojo/**/tests/**");
        assertThat(filter.match(this.context, folderWithPath("/dojo/folder/tests/file.js")), is(true));
        assertThat(filter.match(this.context, folderWithPath("/dojo/some/folder/tests/file.js")), is(true));
        assertThat(filter.match(this.context, folderWithPath("/dojo/some/folder/tests/another/file.js")), is(true));
    }

    @Test
    public void testCaseSensitiveNames() throws Exception {
        ResourceFilter filter = FilterOn.caseSensitiveNames().starting("~").ending(".tmp", ".bak");
        assertThat(filter.match(this.context, fileWithName("~file.tmp")), is(true));
        assertThat(filter.match(this.context, fileWithName("~file.bak")), is(true));
        assertThat(filter.match(this.context, fileWithName("file.tmp")), is(false));
        assertThat(filter.match(this.context, fileWithName("~file.dat")), is(false));
        assertThat(filter.match(this.context, fileWithName("~xxkeepxx.bak")), is(true));
    }

    @Test
    public void testPaths() {
        ResourceFilter filter = FilterOn.paths();
        assertThat(filter.match(this.context, folderWithPath("/dojo/folder/tests/file.js")), is(true));
        assertThat(filter.match(this.context, folderWithPath("/dojo/some/folder/tests/file.js")), is(true));
        assertThat(filter.match(this.context, folderWithPath("/dojo/some/folder/tests/another/file.js")), is(true));
    }

    @Test
    public void testPaths1() {
        ResourceFilter filter = FilterOn.paths(PathStyle.FULL);
        assertThat(filter.match(this.context, folderWithPath("/dojo/folder/tests/file.js")), is(true));
        assertThat(filter.match(this.context, folderWithPath("/dojo/some/folder/tests/file.js")), is(true));
        assertThat(filter.match(this.context, folderWithPath("/dojo/some/folder/tests/another/file.js")), is(true));

    }

    @Test
    public void testPaths2() {
        ResourceFilter filter = FilterOn.caseSensitivePaths(PathStyle.FULL);
        assertThat(filter.match(this.context, folderWithPath("/dojo/folder/tests/file.js")), is(true));
        assertThat(filter.match(this.context, folderWithPath("/dojo/some/folder/tests/file.js")), is(true));
        assertThat(filter.match(this.context, folderWithPath("/dojo/some/folder/tests/another/file.js")), is(true));

    }

    @Test
    public void testPaths3() {
        ResourceFilter filter = FilterOn.caseSensitivePaths();
        assertThat(filter.match(this.context, folderWithPath("/dojo/folder/tests/file.js")), is(true));
        assertThat(filter.match(this.context, folderWithPath("/dojo/some/folder/tests/file.js")), is(true));
        assertThat(filter.match(this.context, folderWithPath("/dojo/some/folder/tests/another/file.js")), is(true));

    }

    private File fileWithName(String name) {
        return resourceWithName(File.class, name, null);
    }

    private Folder folderWithName(String name) {
        return resourceWithName(Folder.class, name, null);
    }

    private Folder folderWithPath(String path) {
        String name = new ResourcePath().get(path).getName();
        return resourceWithName(Folder.class, name, path);
    }

    private <T extends Resource> T resourceWithName(Class<T> resourceType, String name, final String path) {
        T resource = Mockito.mock(resourceType);
        BDDMockito.given(resource.getName()).willReturn(name);
        BDDMockito.given(resource.toString()).willReturn(path);
        BDDMockito.given(resource.toStringRelativeTo(any(Folder.class))).willAnswer(new Answer<String>() {

            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                final ResourcePath resourcePath = new ResourcePath().get(path);
                return resourcePath.toStringRelativeTo(invocation.getArguments()[0].toString());
            }
        });
        return resource;
    }
}
