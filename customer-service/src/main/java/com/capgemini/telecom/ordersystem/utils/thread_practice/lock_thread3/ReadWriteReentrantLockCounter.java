package com.capgemini.telecom.ordersystem.utils.thread_practice.lock_thread3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// This class demonstrates the use of ReadWriteLock to manage concurrent access to a shared counter.
// The ReadWriteLock allows multiple threads to read the counter simultaneously
// while ensuring that only one thread can write to it at a time, thus improving performance in read-heavy scenarios.
public class ReadWriteReentrantLockCounter {

    private int count =0;

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private Lock readLock = readWriteLock.readLock();

    private Lock writeLock = readWriteLock.writeLock();

    public void increment() {
        writeLock.lock();
        try {
            count++;
            System.out.println("Count incremented to: " + count + " by " + Thread.currentThread().getName());
        } finally {
            writeLock.unlock();
        }
    }

    public int getCount() {
        readLock.lock();
        try {
            System.out.println("Count read as: " + count + " by " + Thread.currentThread().getName());
            return count;
        } finally {
            readLock.unlock();
        }
    }

    public static void main(String[] args) {

        ReadWriteReentrantLockCounter readWriteCounter = new ReadWriteReentrantLockCounter();
        Runnable writeTask = new Runnable() {
            @Override
            public void run() {
                for(int i=0; i < 5; i++) {
                    readWriteCounter.increment();
                    try {
                        Thread.sleep(50); // Simulate some work
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        };

        Runnable readTask = new Runnable() {
            @Override
            public void run() {
                for(int i=0; i < 5; i++) {
                    readWriteCounter.getCount();
                    try {
                        Thread.sleep(50); // Simulate some work
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        };

        Thread writerThread = new Thread(writeTask, "WriterThread");
        Thread readerThread1 = new Thread(readTask, "ReaderThread1");
        Thread readerThread2 = new Thread(readTask, "ReaderThread2");

        writerThread.start();
        readerThread1.start();
        readerThread2.start();

        try {
            writerThread.join();
            readerThread1.join();
            readerThread2.join();
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread interrupted: " + e.getMessage());
        }
    }
}
