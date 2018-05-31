package com.wavemaker.commons;

/**
 * Created by prakashb on 23/7/18.
 */
public class WMError extends Error {

    public WMError(String message) {
        super(message);
    }

    public WMError(String message, Throwable cause) {
        super(message, cause);
    }

    public WMError(Throwable cause) {
        super(cause);
    }
}
