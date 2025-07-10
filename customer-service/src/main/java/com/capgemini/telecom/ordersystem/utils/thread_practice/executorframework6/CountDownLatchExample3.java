package com.capgemini.telecom.ordersystem.utils.thread_practice.executorframework6;

import java.util.concurrent.*;

/**
 * This example demonstrates how to use the ExecutorService to manage multiple threads
 * that depend on each other. In this case, we simulate three dependent services that
 * must complete their processing before the main thread can proceed.
 * The main thread waits for all dependent services to complete using Future.get().
 * but the problem is we have to call get on each Future object, lets say there are 1000 dependent services,
 * we have to call get on each Future object, which is not efficient.
 */
public class CountDownLatchExample3 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Future<String> future1 = executorService.submit(new DependentServiceEach());
        Future<String> future2 = executorService.submit(new DependentServiceEach());
        Future<String> future3 = executorService.submit(new DependentServiceEach());

        future1.get();
        future2.get();
        future3.get();
        System.out.println("All dependent services have completed processing.");
        executorService.shutdown();


        // Using CountDownLatch to wait for all dependent services to complete
        // This is more efficient as we can wait for all services to complete without calling get on each Future object.
        // CountDownLatch is a synchronization aid that allows one or more threads to wait until a set of operations being performed in other threads completes.
        // count down latch is initialized with a count of the number of dependent services.
        // count is decremented in finally block

        int noOfServices = 3;
        CountDownLatch latch = new CountDownLatch(noOfServices); // 3 dependent services
        ExecutorService executorService1 = Executors.newFixedThreadPool(noOfServices);
        for(int i=0;i<noOfServices;i++){
            executorService1.submit(new DependentService(latch));
        }
        latch.await();

        System.out.println("Main");
        executorService1.shutdown();
    }
}

class DependentServiceEach implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println("Dependent service is processing..." + Thread.currentThread().getName());
        return "ok";
    }
}

class DependentService implements Runnable {

    private CountDownLatch latch;

    public DependentService(CountDownLatch latch) {
        this.latch = latch;
    }
    @Override
    public void run() {
        System.out.println("Dependent service is processing..." + Thread.currentThread().getName());
        try {
            Thread.sleep(1000); // Simulate some processing time
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        finally {
            latch.countDown(); // Decrement the count of the latch
             }
    }
}
