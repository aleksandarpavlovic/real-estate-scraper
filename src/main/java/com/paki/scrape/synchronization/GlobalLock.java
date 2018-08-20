package com.paki.scrape.synchronization;

import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class GlobalLock {
    private final Lock lock = new ReentrantLock();

    public boolean tryLock() {
        return lock.tryLock();
    }

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }
}
