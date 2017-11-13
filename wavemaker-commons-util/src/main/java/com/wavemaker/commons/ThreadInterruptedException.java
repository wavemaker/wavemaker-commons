package com.wavemaker.commons;

/**
 * An unchecked execption class which can be used to wrap Interrupted exception
 * 
 * @author Uday Shankar
 */
public class ThreadInterruptedException extends WMRuntimeException {

    public ThreadInterruptedException(InterruptedException e) {
        super(e);
    }
    
    public ThreadInterruptedException(String msg, InterruptedException e) {
        super(msg, e);
    }
}
