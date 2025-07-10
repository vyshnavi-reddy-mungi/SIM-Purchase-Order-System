package com.capgemini.telecom.ordersystem.utils.thread_practice.synchronize_thread2;

public class MyThread  extends Thread {

    private Counter counter;

    MyThread(Counter counter){
        this.counter = counter;
    }

   @Override
    public void run(){
       for(int i=1;i<=10000;i++){
           counter.increment();
       }
   }
}
