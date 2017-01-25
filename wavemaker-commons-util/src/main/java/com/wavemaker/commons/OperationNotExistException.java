package com.wavemaker.commons;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 12/5/16
 */
public class OperationNotExistException extends Exception {

    public OperationNotExistException(String s) {
        super(s);
    }

    public OperationNotExistException(String s, Throwable cause) {
        super(s, cause);
    }

}
