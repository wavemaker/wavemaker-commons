package com.wavemaker.commons;

/**
 * @author Uday Shankar
 */
public class TimeoutException extends WMRuntimeException {

    public TimeoutException(MessageResource messageResource) {
        super(messageResource);
    }
    
    public TimeoutException(MessageResource messageResource, String msg) {
        super(messageResource, msg);
    }
}
