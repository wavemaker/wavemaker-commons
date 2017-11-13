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
        } catch (InterruptedException e) {
            throw new ThreadInterruptedException(e);
        }
    }

    public void acquire(int permits) {
        try {
            semaphore.acquire(permits);
        } catch (InterruptedException e) {
            throw new ThreadInterruptedException(e);
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
        } catch (InterruptedException e) {
            throw new ThreadInterruptedException(e);
        }
    }

    public boolean tryAcquire(int permits, int timeout, TimeUnit unit) {
        try {
            return semaphore.tryAcquire(permits, timeout, unit);
        } catch (InterruptedException e) {
            throw new ThreadInterruptedException(e);
        }
    }

    public void acquire(int timeout, TimeUnit unit) {
        try {
            boolean acquired = semaphore.tryAcquire(timeout, unit);
            if (!acquired) {
                throw new SemaphoreAcquisitionTimeoutException();
            }
        } catch (InterruptedException e) {
            throw new ThreadInterruptedException(e);
        }
    }

    public void acquire(int permits, int timeout, TimeUnit unit) {
        try {
            boolean acquired = semaphore.tryAcquire(permits, timeout, unit);
            if (!acquired) {
                throw new SemaphoreAcquisitionTimeoutException();
            }
        } catch (InterruptedException e) {
            throw new ThreadInterruptedException(e);
        }
    }

    public void release() {
        semaphore.release();
    }

    public void release(int permits) {
        semaphore.release(permits);
    }
}
