package com.wavemaker.studio.common.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

/**
 * @author Uday Shankar
 */
public class WMEventPublisherImpl implements WMEventPublisher, ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publish(WMEvent wmEvent) {
        applicationEventPublisher.publishEvent(wmEvent);
    }


}
