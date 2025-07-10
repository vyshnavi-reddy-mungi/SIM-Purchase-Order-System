package com.capgemini.telecom.ordersystem.utils.thread_practice;

public class NoAtomicCounterExample {

    private int count = 0;

    public void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        NoAtomicCounterExample counter = new NoAtomicCounterExample();
        int n=5000;
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
