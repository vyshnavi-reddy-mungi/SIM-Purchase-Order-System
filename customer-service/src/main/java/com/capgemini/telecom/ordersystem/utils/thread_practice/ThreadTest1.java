package com.capgemini.telecom.ordersystem.utils.thread_practice;

//start run sleep join
class MyThread1 extends Thread {

    @Override
    public void run() {
        System.out.println("Thread is running.");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

public class ThreadTest1 {
    public static void main(String[] args) throws InterruptedException {
        MyThread1 mythread = new MyThread1();
        mythread.start();
        mythread.join();
        System.out.println("Test1 class is running.");
    }
}
