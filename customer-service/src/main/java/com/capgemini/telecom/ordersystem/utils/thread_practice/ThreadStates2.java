package com.capgemini.telecom.ordersystem.utils.thread_practice;

//start run sleep join getState
class MyThread extends Thread {

    @Override
    public void run() {
        System.out.println("Thread is running.");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
public class ThreadStates2 {

    public static void main(String[] args) throws InterruptedException {

        MyThread thread = new MyThread();
        System.out.println(thread.getState());
        thread.start();
        System.out.println(thread.getState());
        Thread.sleep(200);
        System.out.println(thread.getState());
        thread.join();
        System.out.println(thread.getState());
        System.out.println("Test class is running.");
    }
}

