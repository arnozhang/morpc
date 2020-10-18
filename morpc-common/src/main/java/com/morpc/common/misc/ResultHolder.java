package com.morpc.common.misc;

import lombok.Data;

/**
 * result holder
 *
 * @author arnozhang <zyfgood12@163.com>
 * @date 2020/10/08
 */
@Data
public class ResultHolder<T> {

    /**
     * actual result object
     */
    private T result;

    /**
     * is result notified
     */
    private boolean notified;

    /**
     * lock
     */
    private final Object lock = new Object();

    /**
     * wait result with lock
     */
    public void waitResult(long timeout) throws Exception {
        synchronized (lock) {
            lock.wait(timeout);
        }
    }

    /**
     * unlock and notify actual result
     *
     * @param result actual result
     */
    public void notifyResult(T result) {
        this.result = result;
        this.notified = true;

        synchronized (lock) {
            lock.notifyAll();
        }
    }
}
