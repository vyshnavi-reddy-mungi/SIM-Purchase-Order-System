package com.capgemini.telecom.ordersystem.utils.thread_practice.lock_thread3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// This code demonstrates the use of an unfair lock in Java.
// An unfair lock does not guarantee that the longest waiting thread will acquire the lock next.
// Arbitrary threads can acquire the lock, which may lead to starvation of some threads.
public class UnfairnessLock3 {

    private final Lock unfairLock = new ReentrantLock();

    public void accessResource() {
        unfairLock.lock();
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
            unfairLock.unlock();
        }
    }

    public static void main(String[] args) {

        UnfairnessLock3 unfairnessLock = new UnfairnessLock3();

        Runnable task = new Runnable() {
            @Override
            public void run() {
                unfairnessLock.accessResource();
            }
        };

        Thread thread1 = new Thread(task, "Thread-1");
        Thread thread2 = new Thread(task, "Thread-2");
        Thread thread3 = new Thread(task, "Thread-3");

        thread1.start();
        thread2.start();
        thread3.start();
    }


}
