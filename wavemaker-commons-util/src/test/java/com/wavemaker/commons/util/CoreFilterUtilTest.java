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
package com.wavemaker.commons.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.testng.Assert;

import com.wavemaker.commons.pattern.URLPattern;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CoreFilterUtilTest {

    private  List<URLPattern> excludedUrl;

    @Before
    public  void initializeExcludedUri() {
        excludedUrl = CoreFilterUtil.extractExcludedUrlsList("/sample/test/url");
    }

    @Test
    public void testIsExcluded() {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("foo/sample/test/url");
        when(request.getContextPath()).thenReturn("foo");
        Assert.assertTrue(CoreFilterUtil.isExcluded(request,excludedUrl));
    }

    @Test
    public void testNotExcludeUri() {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("foo/sample/util/package/url");
        when(request.getContextPath()).thenReturn("foo");
        Assert.assertFalse(CoreFilterUtil.isExcluded(request,excludedUrl));
    }
}