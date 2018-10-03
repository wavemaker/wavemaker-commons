package com.wavemaker.commons.json.module;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 3/10/18
 */
public class C {
    private String value;
    private A a;

    public C(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public C setValue(final String value) {
        this.value = value;
        return this;
    }

    public A getA() {
        return a;
    }

    public C setA(final A a) {
        this.a = a;
        return this;
    }
}
