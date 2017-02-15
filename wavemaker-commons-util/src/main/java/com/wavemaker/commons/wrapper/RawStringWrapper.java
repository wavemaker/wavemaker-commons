package com.wavemaker.commons.wrapper;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 15/2/17
 */
public class RawStringWrapper {

    private final String value;


    public RawStringWrapper(final String value) {
        this.value = value;
    }

    @JsonRawValue
    @JsonValue
    public String getValue() {
        return value;
    }
}
