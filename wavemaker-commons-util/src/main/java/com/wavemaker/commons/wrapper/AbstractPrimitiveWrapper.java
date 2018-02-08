package com.wavemaker.commons.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Uday Shankar
 */
public class AbstractPrimitiveWrapper<T> {

    private T result;

    public AbstractPrimitiveWrapper(T result) {
        this.result = result;
    }

    @JsonProperty("__result__")
    public T getResult() {
        return result;
    }
}
