package com.capgemini.telecom.ordersystem.utils.thread_practice.lock_thread3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantExample2 {

    private Lock lock = new ReentrantLock();

    public void outerLock() {
        lock.lock();
        try {
            System.out.println("Outer lock acquired by " + Thread.currentThread().getName());
            innerLock();
        } finally {
            lock.unlock();
            System.out.println("Outer lock released by " + Thread.currentThread().getName());
        }
    }

    private void innerLock() {
        lock.lock();
        try {
            System.out.println("Inner lock acquired by " + Thread.currentThread().getName());

        } finally {
            lock.unlock();
            System.out.println("Inner lock released by " + Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {
        ReentrantExample2 example = new ReentrantExample2();

        example.outerLock();
    }
}
