package com.capgemini.telecom.ordersystem.utils.thread_practice;

// This code demonstrates the use of daemon threads in Java.
// Daemon threads are background threads that do not prevent the JVM from exiting.
// They are typically used for tasks that run in the background, such as garbage collection or monitoring.
// When the main thread finishes execution, the JVM will exit, and any daemon threads will be terminated immediately.
// In this example, we create a daemon thread that runs indefinitely, printing a message to the console.
// The main thread starts the daemon thread and then prints a message indicating that it is done.
// Note: Be cautious when using daemon threads, as they can lead to unexpected behavior if not managed properly.
class Mythread6 extends Thread {
    @Override
    public void run() {
        while(true) {
            System.out.println("Thread is running.");
        }
    }
}
public class SetDaemonThread {
    public static void main(String[] args) {
        Mythread6 myThread = new Mythread6();
        myThread.setDaemon(true);
        myThread.start();
        System.out.println("Main thread is done.");
    }
}
