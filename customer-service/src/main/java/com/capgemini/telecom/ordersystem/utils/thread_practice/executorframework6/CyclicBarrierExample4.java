package com.capgemini.telecom.ordersystem.utils.thread_practice.executorframework6;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

// This example demonstrates the use of CyclicBarrier to synchronize multiple subsystems
// in a system initialization process. Each subsystem runs in its own thread and waits
// at the barrier until all subsystems have completed their initialization.
// Once all subsystems are initialized, a final action is performed to start the main system.


public class CyclicBarrierExample4 {
    public static void main(String[] args) {

        CyclicBarrier barrier = new CyclicBarrier(4, new Runnable() {
            @Override
            public void run() {
                System.out.println("All subsystems are initialized. Starting the main system.");
            }
        });
        Thread webServerThread = new Thread(new Sybsystem("Web Server", 2000, barrier));
        Thread databaseThread = new Thread(new Sybsystem("Database", 3000, barrier));
        Thread cacheThread = new Thread(new Sybsystem("Cache", 1000, barrier));
        Thread messagingThread = new Thread(new Sybsystem("Messaging", 1500, barrier));

        webServerThread.start();
        databaseThread.start();
        cacheThread.start();
        messagingThread.start();
    }
}

class Sybsystem implements Runnable{

    private String name;
    private int intilizationTime;
    private CyclicBarrier barrier;

    public Sybsystem(String name, int intilizationTime, CyclicBarrier barrier) {
        this.name = name;
        this.intilizationTime = intilizationTime;
        this.barrier = barrier;
    }

   @Override
    public void run() {
        System.out.println("Subsystem " + name + " is initializing for " + intilizationTime + " seconds.");
        try {
        Thread.sleep(intilizationTime);

            barrier.await();
        } catch (InterruptedException e) {
           Thread.currentThread().interrupt();
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

    }
}
