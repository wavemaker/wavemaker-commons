package com.wavemaker.studio.common.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;

/**
 * @author Uday Shankar
 */
@Order(value = 0)
public interface WMApplicationListener<E extends ApplicationEvent> extends ApplicationListener<E> {
}
