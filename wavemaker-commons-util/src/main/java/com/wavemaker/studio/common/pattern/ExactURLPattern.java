package com.wavemaker.studio.common.pattern;

/**
 * Created by sunilp on 7/10/15.
 */
public class ExactURLPattern
        extends URLPattern
{
    ExactURLPattern(String pattern)
    {
        super(pattern);
    }

    public boolean matches(String requestURI)
    {
        if (requestURI == null || "".equals(requestURI)) {
            return false;
        }
        return getPatternString().equals(requestURI);
    }

    public String toString()
    {
        return "Exact-Pattern: [" + getPatternString() + "]";
    }
}
