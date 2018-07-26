package com.wavemaker.commons;

/**
 * Created by prakashb on 27/7/18.
 */
public interface MessageResourceHolder {

    MessageResource getMessageResource();

    Object[] getArgs();

    String getMessage();
}
