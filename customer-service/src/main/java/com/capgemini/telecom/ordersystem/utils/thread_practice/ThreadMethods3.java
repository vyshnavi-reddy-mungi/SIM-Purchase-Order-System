package com.capgemini.telecom.ordersystem.utils.thread_practice;

//start run sleep join getState setPriority
class MyThread3 extends  Thread{

    MyThread3(String threadName) {
        super(threadName);
    }
    @Override
    public void run() {
        for(int i=0;i<5;i++) {
            String a="";
            for(int j=0;j<100000;j++){
                a+="a";
            }
            System.out.println("Thread is running: " + Thread.currentThread().getName()
                    + " priority: " + Thread.currentThread().getPriority()
                    + " count: " + i);
      try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
public class ThreadMethods3 {
    public static void main(String[] args) throws InterruptedException {
        MyThread3 l = new MyThread3("low priority thread");
        MyThread3 m = new MyThread3("medium priority thread");
        MyThread3 h = new MyThread3("high priority thread");
        l.setPriority(Thread.MIN_PRIORITY);
        m.setPriority(Thread.NORM_PRIORITY);
        h.setPriority(Thread.MAX_PRIORITY);
        l.start();
        m.start();
        h.start();



      System.out.println("Test class is running.");
    }
}
