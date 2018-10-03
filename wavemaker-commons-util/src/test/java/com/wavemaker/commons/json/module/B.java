package com.wavemaker.commons.json.module;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 3/10/18
 */
public class B {
    private String value;
    private C c;

    public B(final String value, final C c) {
        this.value = value;
        this.c = c;
    }

    public String getValue() {
        return value;
    }

    public C getC() {
        return c;
    }
}
