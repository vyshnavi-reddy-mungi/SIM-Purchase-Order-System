package com.capgemini.telecom.ordersystem.utils.thread_practice.synchronize_thread2;

public class Counter {

    private int count = 0;

    // this is critical section, only one thread can access this method at a time
    // if no synchronization is used, multiple threads can access this method simultaneously
    // and is called as race condition
    // synchronized keyword ensures that only one thread can execute this method at a time and is
    // called mutual exclusion, only one thread can access the critical section at a time
    public synchronized void increment(){
        count++;
    }

    // Alternative method to increment with a value with synchronization block, specific to thread calling it
//    public void increment(int value) {
//        synchronized (this) {
//            count++;
//        }
//    }

    public int getCount() {
        return count;
    }
}
