package com.capgemini.telecom.ordersystem.utils.thread_practice.executorframework6;



    // This class can be used to demonstrate the use of Future and ScheduledExecutorService
    // in Java for scheduling tasks and handling their results asynchronously.

    // Example methods can be added here to showcase how to submit tasks,
    // retrieve results, and handle exceptions using Future and ScheduledExecutorService.

    // For instance, you can create a ScheduledExecutorService instance,
    // schedule a task, and then retrieve the result using Future.

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

// This is a placeholder for future implementation.
    public class FutureScheduledExecutoreService2 {

    public static void main(String[] args) {

// -----------------schedule-------------------
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
//        scheduler.schedule(() ->
//            System.out.println("Task executed after 2 seconds delay"),
//        2,
//                TimeUnit.SECONDS);

//        scheduler.scheduleAtFixedRate(() ->
//                        System.out.println("Task executed after 2 seconds delay"),
//                2,
//                2,
//                TimeUnit.SECONDS);
//
//        scheduler.shutdown();

// -----------------scheduleAtFixedRate---------- continues abruptly with next one---------
//        scheduler.scheduleAtFixedRate(() ->
//                        System.out.println("Task executed after 2 seconds delay"),
//                2,
//                2,
//                TimeUnit.SECONDS);
//
//        // here the shutdown dont know, how to stop the scheduled task, so explicity shutting down the task
//        scheduler.schedule(() -> {
//            System.out.println("Shutting down the scheduler");
//            scheduler.shutdown();
//        }, 3, TimeUnit.SECONDS);

        //------------scheduleWithFixedDelay----- no overlapping. waits for certain time before starting the new one --------------
        ScheduledFuture<?> scheduledFuture = scheduler.scheduleWithFixedDelay(() ->
                        System.out.println("Task executed after 2 seconds delay with fixed delay"),
                2,
                2,
                TimeUnit.SECONDS);

        // here the shutdown dont know, how to stop the scheduled task, so explicity shutting down the task
        scheduler.schedule(() -> {
            System.out.println("Shutting down the scheduler");
            scheduler.shutdown();
        }, 10, TimeUnit.SECONDS);


    }
}