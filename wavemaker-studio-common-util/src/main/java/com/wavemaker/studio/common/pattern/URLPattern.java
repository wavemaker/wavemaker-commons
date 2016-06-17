package com.wavemaker.studio.common.pattern;

/**
 * Created by sunilp on 7/10/15.
 */
public abstract class URLPattern {
    private final String patternString;

    public static URLPattern constructPattern(String requestPattern) {
        if (requestPattern.endsWith("/*")) {
            return new DirectoryURLPattern(requestPattern);
        } else if (requestPattern.startsWith("*.")) {
            return new ExtensionURLPattern(requestPattern);
        } else {
            return new ExactURLPattern(requestPattern);
        }
    }

    protected URLPattern(String pattern) {
        patternString = pattern;
    }

    public String getPatternString() {
        return patternString;
    }
    /**
     * This method, takes a request URI, and checks whether this uri matches this pattern or not. if matches will return
     * true. else returns false.
     *
     * @param requestURI
     * @return
     */
    abstract public boolean matches(String requestURI);
}

