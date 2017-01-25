package com.wavemaker.commons.pattern;

/**
 * Created by sunilp on 7/10/15.
 */
public class ExtensionURLPattern
        extends URLPattern
{
    private String extenstionPattern;

    ExtensionURLPattern(String pattern)
    {
        super(pattern);
        extenstionPattern = pattern.substring(1);
    }

    public boolean matches(String requestURI)
    {
        if (requestURI == null || "".equals(requestURI)) {
            return false;
        }

        return requestURI.endsWith(extenstionPattern);
    }

    public String toString()
    {
        return "Extension-Pattern: [ " + getPatternString() + "]";
    }
}