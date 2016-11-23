package com.wavemaker.studio.common.event;

import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;

/**
 * @author Uday Shankar
 */
@Order(value = 0)
public interface WMApplicationListener<T extends WMEvent> extends ApplicationListener<T> {
}
