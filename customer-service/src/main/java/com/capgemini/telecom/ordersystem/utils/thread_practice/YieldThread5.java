package com.capgemini.telecom.ordersystem.utils.thread_practice;

class MyThread5 extends Thread {
    @Override
    public void run() {

       for(int i=0;i<5;i++){
           System.out.println(Thread.currentThread().getName());
           Thread.yield();
       }
    }
}
public class YieldThread5 {
    public static void main(String[] args) {
        MyThread5 myThread1 = new MyThread5();
        MyThread5 myThread2 = new MyThread5();
        myThread1.setName("Thread 1");
        myThread2.setName("Thread 2");
        myThread1.start();
        myThread2.start();
    }
}
