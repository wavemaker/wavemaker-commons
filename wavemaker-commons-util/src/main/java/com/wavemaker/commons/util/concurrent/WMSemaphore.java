/*******************************************************************************
 * Copyright (C) 2022-2023 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.wavemaker.commons.util.concurrent;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wavemaker.commons.SemaphoreAcquisitionTimeoutException;
import com.wavemaker.commons.ThreadInterruptedException;

/**
 * A class which wraps the {@link Semaphore} fuctionality with extra timeout and thread interruption handling
 *
 * @author Uday Shankar
 */
public class WMSemaphore {

    private Semaphore semaphore;

    private static final Logger logger = LoggerFactory.getLogger(WMSemaphore.class);
    private String name;
    private int totalPermits;

    public WMSemaphore(String name, int permits) {
        this(name, permits, true);
    }

    public WMSemaphore(String name, int totalPermits, boolean fair) {
        this.semaphore = new Semaphore(totalPermits, fair);
        this.name = name;
        this.totalPermits = totalPermits;
    }

    public void acquire(int timeout, TimeUnit unit) {
        acquire(1, timeout, unit);
    }

    public void acquire(int permits, int timeout, TimeUnit unit) {
        try {
            logger.info("Trying to acquire {} permit(s) in semaphore {}", permits, this);
            boolean acquired = semaphore.tryAcquire(permits, timeout, unit);
            logger.info("Acquisition status is {} while acquiring {} permit(s) in semaphore {}", acquired, permits, this);
            if (!acquired) {
                throw new SemaphoreAcquisitionTimeoutException();
            }
        } catch (InterruptedException e) { //NOSONAR
            handleInterruptedException(e);
        }
    }

    public void release() {
        release(1);
    }

    public void release(int permits) {
        semaphore.release(permits);
        logger.info("Released {} permit(s) in semaphore {}", permits, this);
    }

    private void handleInterruptedException(InterruptedException e) {
        logger.info("Received interrupted exception in thread {} in semaphore {}", Thread.currentThread().getName(), this);
        Thread.currentThread().interrupt();
        throw new ThreadInterruptedException(e);
    }

    @Override
    public String toString() {
        return "WMSemaphore{" +
            " availablePermits=" + semaphore.availablePermits() +
            ", totalPermits=" + totalPermits +
            ", name='" + name + '\'' +
            '}';
    }
}
