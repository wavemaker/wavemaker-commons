package com.wavemaker.commons.pattern;

/**
 * Created by sunilp on 7/10/15.
 */
public class DirectoryURLPattern
        extends URLPattern
{
    private String directoryPatternwithSlash;
    private String directoryPattern;


    DirectoryURLPattern(String pattern)
    {
        super(pattern);
        directoryPattern = pattern.substring(0, pattern.length() - 2);
        directoryPatternwithSlash = pattern.substring(0, pattern.length() - 1);
    }

    public boolean matches(String requestURI)
    {
        if (requestURI == null || "".equals(requestURI)) {
            return false;
        }

        if (requestURI.length() == directoryPattern.length()) {
            if (directoryPattern.equals(requestURI)) {
                return true;
            }
        } else if (requestURI.startsWith(directoryPatternwithSlash)) {
            return true;
        }

        return false;
    }

    public String toString()
    {
        return "Directory-Pattern: [" + directoryPatternwithSlash + "*]";
    }
}

