package com.wavemaker.commons;

/**
 * An exception which is thrown when the semaphore acquistion is timed out 
 * 
 * @author Uday Shankar
 */
public class SemaphoreAcquisitionTimeoutException extends WMRuntimeException {

    public SemaphoreAcquisitionTimeoutException() {
        super(MessageResource.SEMAPHORE_ACQUISITION_TIMEOUT);
    }

    public SemaphoreAcquisitionTimeoutException(String msg) {
        super(msg);
    }

    public SemaphoreAcquisitionTimeoutException(MessageResource messageResource) {
        super(messageResource);
    }
}
