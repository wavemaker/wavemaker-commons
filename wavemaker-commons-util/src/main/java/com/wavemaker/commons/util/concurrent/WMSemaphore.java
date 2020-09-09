/**
 * Copyright (C) 2020 WaveMaker, Inc.
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
package com.wavemaker.commons.util.concurrent;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import com.wavemaker.commons.SemaphoreAcquisitionTimeoutException;
import com.wavemaker.commons.ThreadInterruptedException;

/**
 * 
 * A class which wraps the {@link Semaphore} fuctionality with extra timeout and thread interruption handling
 * 
 * @author Uday Shankar
 */
public class WMSemaphore {
    
    private Semaphore semaphore;

    public WMSemaphore(int permits) {
        this.semaphore = new Semaphore(permits);
    }

    public WMSemaphore(int permits, boolean fair) {
        this.semaphore = new Semaphore(permits, fair);
    }

    public void acquire() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) { //NOSONAR
            handleInterruptedException(e);
        }
    }

    public void acquire(int permits) {
        try {
            semaphore.acquire(permits);
        } catch (InterruptedException e) { //NOSONAR
            handleInterruptedException(e);
        }
    }

    public boolean tryAcquire() {
        return semaphore.tryAcquire();
    }

    public boolean tryAcquire(int permits) {
        return semaphore.tryAcquire(permits);
    }

    public boolean tryAcquire(int timeout, TimeUnit unit) {
        try {
            return semaphore.tryAcquire(timeout, unit);
        } catch (InterruptedException e) { //NOSONAR
            handleInterruptedException(e);
        }
        return false;
    }

    public boolean tryAcquire(int permits, int timeout, TimeUnit unit) {
        try {
            return semaphore.tryAcquire(permits, timeout, unit);
        } catch (InterruptedException e) { //NOSONAR
            handleInterruptedException(e);
        }
        return false;
    }

    public void acquire(int timeout, TimeUnit unit) {
        try {
            boolean acquired = semaphore.tryAcquire(timeout, unit);
            if (!acquired) {
                throw new SemaphoreAcquisitionTimeoutException();
            }
        } catch (InterruptedException e) { //NOSONAR
            handleInterruptedException(e);
        }
    }

    public void acquire(int permits, int timeout, TimeUnit unit) {
        try {
            boolean acquired = semaphore.tryAcquire(permits, timeout, unit);
            if (!acquired) {
                throw new SemaphoreAcquisitionTimeoutException();
            }
        } catch (InterruptedException e) { //NOSONAR
            handleInterruptedException(e);
        }
    }

    public void release() {
        semaphore.release();
    }

    public void release(int permits) {
        semaphore.release(permits);
    }

    private void handleInterruptedException(InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new ThreadInterruptedException(e);
    }
}
