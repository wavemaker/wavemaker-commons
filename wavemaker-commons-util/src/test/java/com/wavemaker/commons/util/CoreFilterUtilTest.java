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