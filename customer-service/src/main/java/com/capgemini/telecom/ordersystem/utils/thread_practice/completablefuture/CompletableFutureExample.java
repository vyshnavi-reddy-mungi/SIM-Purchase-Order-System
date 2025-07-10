package com.capgemini.telecom.ordersystem.utils.thread_practice.completablefuture;

import java.util.concurrent.*;


// This code demonstrates the use of CompletableFuture in Java to run a task asynchronously.
// It simulates a long-running task that sleeps for 5 seconds and then returns a string "ok".
// CompletableFuture allows you to write non-blocking code, it runs in background as daemon thread, and main
// thread do not wait.
// using get() method, The main thread waits retrieves the result, which blocks until the result is available.
public class CompletableFutureExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("Worker");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "ok";
        });

        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("Worker");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "ok";
        });

        //-------------- just linke invokeAll in future.invokeAll() , CompletableFuture.allOf() is used to wait for multiple futures to complete.
        CompletableFuture<Void> f = CompletableFuture.allOf(f1, f2);
        f.get(); // This will block until both futures are complete, it needs an exception to be handled
        f.join(); // This will block until both futures are complete
        //  String s=stringCompletableFuture.get();
        System.out.println(f);


// ----------------- .get() inline
        String s = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("Worker3");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "ok";
        }).get();
        System.out.println(s);
        System.out.println("Main");

        // after getting the result from the supply function, we can apply some transformation to the result using thenApply
        // thenApply is used to apply a function to the result of the CompletableFuture
        CompletableFuture<String> worker4 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("Worker4");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "ok";
        }).thenApply(x -> x + x);
        System.out.println(worker4.get());


        // ----------------- .orTimeout() inline
        // orTimeout is used to set a timeout for the CompletableFuture, if the timeout occurs, it will throw a TimeoutException
        CompletableFuture<String> worker5 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("Worker5");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "ok";
        }).orTimeout(1, TimeUnit.SECONDS).exceptionally(ex -> "Timeout occured");
        System.out.println(worker5.get());


        // using executor to run the task asynchronously
        ExecutorService executor = Executors.newFixedThreadPool(2); // This is not used in this example, but can be used to create a thread pool for executing tasks
        CompletableFuture<String> worker6 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("Worker6");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "ok";
        }, executor).thenApply(x -> x + x);
        System.out.println(worker4.get());
    }

    // by default completableFuture tasks often run on daemon threads, which means they do not prevent the JVM from exiting.
    // completableFuture often runs on  daemon threads use ForkJoinPool.commonPool() by default, which is a shared thread pool that is used by all CompletableFuture tasks.
    // can control the thread type by providing a custom Executor
    // the completanbleFuture task itself doesn't dictate the thread type,whether it is daemon or non-daemon(user), it depends on the executor used to run the task.
}
