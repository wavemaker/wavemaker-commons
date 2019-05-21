package com.wavemaker.commons;

public class WMClientRuntimeException extends WMRuntimeException {

    public WMClientRuntimeException(MessageResource resource, Object... args) {
        super(resource, args);
    }

}
