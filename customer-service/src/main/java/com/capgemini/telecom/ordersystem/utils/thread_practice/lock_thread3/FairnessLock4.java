package com.capgemini.telecom.ordersystem.utils.thread_practice.lock_thread3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FairnessLock4 {

    private final Lock fairLock = new ReentrantLock(true);

    public void accessResource() {
        fairLock.lock();
        try {
            System.out.println("Thread " + Thread.currentThread().getName() + " has acquired the lock.");
            // Simulating some work with the resource
            Thread.sleep(100);
            System.out.println("Thread " + Thread.currentThread().getName() + " is working with the resource.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread " + Thread.currentThread().getName() + " was interrupted.");
        } finally {

            System.out.println("Thread " + Thread.currentThread().getName() + " has released the lock.");
            fairLock.unlock();
        }
    }

    public static void main(String[] args) {

        FairnessLock4 fairnessLock = new FairnessLock4();

        Runnable task = new Runnable() {
            @Override
            public void run() {
                fairnessLock.accessResource();
            }
        };

        Thread thread1 = new Thread(task, "Thread-1");
        Thread thread2 = new Thread(task, "Thread-2");
        Thread thread3 = new Thread(task, "Thread-3");

        try {
            thread2.start();
            Thread.sleep(50);
            thread1.start();
            Thread.sleep(50);
            thread3.start();
        } catch (Exception e) {

        }
    }


}
