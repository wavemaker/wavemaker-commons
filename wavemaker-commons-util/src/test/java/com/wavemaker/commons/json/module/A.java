package com.wavemaker.commons.json.module;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 3/10/18
 */
public class A {
    private String value;
    private B b;

    public A(final String value, final B b) {
        this.value = value;
        this.b = b;
    }

    public String getValue() {
        return value;
    }

    public B getB() {
        return b;
    }
}
