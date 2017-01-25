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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.wavemaker.commons.io.File;
import com.wavemaker.commons.io.Folder;
import com.wavemaker.commons.io.Resource;
import com.wavemaker.commons.io.ResourceFilter;
import com.wavemaker.commons.io.ResourceFilterContext;
import com.wavemaker.commons.io.ResourceOperation;
import com.wavemaker.commons.io.ResourcesCollection;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests for {@link ResourcesCollection}.
 * 
 * @author Phillip Webb
 */
@RunWith(MockitoJUnitRunner.class)
public class ResourcesCollectionTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private Folder source;

    @Mock
    private Folder folder;

    @Mock
    private File file;

    @Mock
    private ResourceOperation<Resource> resourceOperation;

    @Test
    public void shouldNeedResources() throws Exception {
        this.thrown.expect(IllegalArgumentException.class);
        this.thrown.expectMessage("Resources must not be null");
        new ResourcesCollection<Resource>(this.source, (Collection<Resource>) null);
    }

    @Test
    public void shouldNeedResourcesArray() throws Exception {
        this.thrown.expect(IllegalArgumentException.class);
        this.thrown.expectMessage("Resources must not be null");
        new ResourcesCollection<Resource>(this.source, (Resource[]) null);
    }

    @Test
    public void shouldIterateCollection() throws Exception {
        ResourcesCollection<Resource> collection = new ResourcesCollection<Resource>(this.source, Arrays.asList(this.folder, this.file));
        Iterator<Resource> iterator = collection.iterator();
        assertThat(iterator.next(), Matchers.is((Resource) this.folder));
        assertThat(iterator.next(), Matchers.is((Resource) this.file));
        assertThat(iterator.hasNext(), Matchers.is(false));
    }

    @Test
    public void shouldIterateArray() throws Exception {
        ResourcesCollection<Resource> collection = new ResourcesCollection<Resource>(this.source, this.folder, this.file);
        Iterator<Resource> iterator = collection.iterator();
        assertThat(iterator.next(), Matchers.is((Resource) this.folder));
        assertThat(iterator.next(), Matchers.is((Resource) this.file));
        assertThat(iterator.hasNext(), Matchers.is(false));
    }

    @Test
    public void shouldDeleteAgainstItems() throws Exception {
        ResourcesCollection<Resource> collection = new ResourcesCollection<Resource>(this.source, this.folder, this.file);
        collection.delete();
        InOrder ordered = Mockito.inOrder(this.folder, this.file);
        ordered.verify(this.folder).delete();
        ordered.verify(this.file).delete();
    }

    @Test
    public void shouldMoveToAgainstItems() throws Exception {
        ResourcesCollection<Resource> collection = new ResourcesCollection<Resource>(this.source, this.folder, this.file);
        Folder destination = Mockito.mock(Folder.class);
        collection.moveTo(destination);
        InOrder ordered = Mockito.inOrder(this.folder, this.file);
        ordered.verify((Resource) this.folder).moveTo(destination);
        ordered.verify((Resource) this.file).moveTo(destination);
    }

    @Test
    public void shouldCopyToAgainstItems() throws Exception {
        ResourcesCollection<Resource> collection = new ResourcesCollection<Resource>(this.source, this.folder, this.file);
        Folder destination = Mockito.mock(Folder.class);
        collection.copyTo(destination);
        InOrder ordered = Mockito.inOrder(this.folder, this.file);
        ordered.verify((Resource) this.folder).copyTo(destination);
        ordered.verify((Resource) this.file).copyTo(destination);
    }

    @Test
    public void shouldPerformOperationAgainstResourceItems() throws Exception {
        ResourcesCollection<Resource> collection = new ResourcesCollection<Resource>(this.source, this.folder, this.file);
        collection.performOperation(this.resourceOperation);
        Mockito.verify(this.resourceOperation).perform(this.folder);
        Mockito.verify(this.resourceOperation).perform(this.file);
        Mockito.verifyNoMoreInteractions(this.resourceOperation);
    }

    @Test
    public void shouldFetchAll() throws Exception {
        ResourcesCollection<Resource> collection = new ResourcesCollection<Resource>(this.source, this.folder, this.file);
        List<Resource> list = collection.fetchAll();
        assertThat(list.size(), Matchers.is(2));
        assertThat(list.get(0), Matchers.is((Resource) this.folder));
        assertThat(list.get(1), Matchers.is((Resource) this.file));
    }

    @Test
    public void shouldSupportFiltering() throws Exception {
        File a = Mockito.mock(File.class);
        File b = Mockito.mock(File.class);
        File c = Mockito.mock(File.class);
        File d = Mockito.mock(File.class);
        File e = Mockito.mock(File.class);
        ResourcesCollection<File> collection = new ResourcesCollection<File>(this.source, a, b, c, d, e);
        Iterator<File> actual = collection.include(filterOn(a), filterOn(c), filterOn(e)).exclude(filterOn(c)).iterator();
        assertThat(actual.next(), Matchers.is(a));
        assertThat(actual.next(), Matchers.is(e));
        assertThat(actual.hasNext(), Matchers.is(false));
    }

    private ResourceFilter filterOn(final Resource on) {
        return new ResourceFilter() {

            @Override
            public boolean match(ResourceFilterContext context, Resource resource) {
                return resource == on;
            }
        };
    }
}
