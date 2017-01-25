package com.wavemaker.commons;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 30/9/16
 */
public class PageDoesNotExistException extends PageException{

    private static final long serialVersionUID = 6551459225251765571L;

    public PageDoesNotExistException(final MessageResource resource, final Object... args) {
        super(resource, args);
    }

    public PageDoesNotExistException(String s) {
        super(s);
    }
}
