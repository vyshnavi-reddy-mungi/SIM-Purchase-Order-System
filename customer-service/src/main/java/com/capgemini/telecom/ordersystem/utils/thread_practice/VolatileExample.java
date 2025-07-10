package com.capgemini.telecom.ordersystem.utils.thread_practice;

class SharedObj {
    private volatile boolean flag = false;

    public void setFlagTrue() {
        System.out.println("Flag is set to true");
        flag = true;
    }

    public void printIfFlagTrue(){
        while(!flag) {
            // Busy-waiting until flag is true
        }
        System.out.println("Flag is true!");
    }
}
public class VolatileExample {
    public static void main(String[] args) {

        SharedObj sharedObj = new SharedObj();

        Thread writerThread = new Thread(() -> {
            try {
                Thread.sleep(1000); // Simulating some work
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sharedObj.setFlagTrue();
        });

        Thread readerThread = new Thread(() -> {
            sharedObj.printIfFlagTrue();
        });

        writerThread.start();
        readerThread.start();
    }
}
