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

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.core.io.ClassPathResource;

import com.wavemaker.commons.io.exception.ResourceDoesNotExistException;

import static org.junit.Assert.assertThat;

/**
 * Tests for {@link ClassPathFile}.
 * 
 * @author Phillip Webb
 */
public class ClassPathFileTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldLoadRelativeToClass() throws Exception {
        File file = new ClassPathFile(getClass(), "a.txt");
        assertThat(file.getContent().asString(), Matchers.is("a"));
    }

    @Test
    public void shouldLoadFromPathUsingClass() throws Exception {
        File file = new ClassPathFile(getClass(), "/com/wavemaker/runtime/core/io/a.txt");
        assertThat(file.getContent().asString(), Matchers.is("a"));
    }

    @Test
    public void shouldLoadUsingExactPath() throws Exception {
        ClassPathResource resource = new ClassPathResource("/com/wavemaker/runtime/core/io/a.txt");
        System.out.println(resource.getInputStream());
        File file = new ClassPathFile("/com/wavemaker/runtime/core/io/a.txt");
        assertThat(file.getContent().asString(), Matchers.is("a"));
    }

    @Test
    public void shouldSupportNotExists() throws Exception {
        File file = new ClassPathFile("/com/wavemaker/platform/io/missing.txt");
        assertThat(file.exists(), Matchers.is(false));
    }

    @Test
    public void shouldThrowOnLoadNotExists() throws Exception {
        File file = new ClassPathFile(getClass(), "missing.txt");
        this.thrown.expect(ResourceDoesNotExistException.class);
        file.getContent().asString();
    }
}
