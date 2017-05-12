/**
 * Copyright © 2013 - 2017 WaveMaker, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wavemaker.commons.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

/**
 * @author Uday Shankar
 */
public class WMEventPublisherImpl implements WMEventPublisher, ApplicationEventPublisherAware {

    private static final Logger logger = LoggerFactory.getLogger(WMEventPublisherImpl.class);

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publish(WMEvent wmEvent) {
        long startTime = System.currentTimeMillis();
        applicationEventPublisher.publishEvent(wmEvent);
        logger.debug("Time taken for the event for class {} is {}ms.  ", wmEvent.getClass().getSimpleName(), (System.currentTimeMillis() - startTime));
    }

}
