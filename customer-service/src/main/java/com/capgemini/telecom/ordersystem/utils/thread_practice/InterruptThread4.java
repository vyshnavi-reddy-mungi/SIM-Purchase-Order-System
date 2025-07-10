package com.capgemini.telecom.ordersystem.utils.thread_practice;

// start run sleep interrupt
// This code demonstrates how to interrupt a thread that is sleeping.
class MyThread4 extends  Thread{
    @Override
    public void run() {
        System.out.println("Thread is running.");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println("Thread was interrupted:"+e);
        }
    }
}
public class InterruptThread4 {
    public static void main(String[] args) {
        MyThread4 myThread = new MyThread4();
        myThread.start();
        myThread.interrupt();
    }
}
