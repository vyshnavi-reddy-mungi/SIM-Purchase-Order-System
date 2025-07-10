package com.capgemini.telecom.ordersystem.utils.thread_practice;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounterExample {

    private AtomicInteger count = new AtomicInteger(0);

    public void increment() {
        count.incrementAndGet();
    }

    public int getCount() {
        return count.get();
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicCounterExample counter = new AtomicCounterExample();
        int n=10000;
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < n; i++) {
                counter.increment();
            }
        }, "Thread-1");


        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < n; i++) {
                counter.increment();
            }
        }, "Thread-2");

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
        System.out.println("Final count: " + counter.getCount());
    }
}
