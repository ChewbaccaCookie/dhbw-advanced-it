package Aufgaben.Aufgabe8;

import java.util.ArrayList;

public class Aufgabe8 {
    public static void main(String[] args) throws InterruptedException {
        final int THREAD_NUM = 8;
        ArrayList<ThreadAufgabe8> list = new ArrayList<>();

        // Start all Threads
        for(int i = 0; i < THREAD_NUM; i++) {
            ThreadAufgabe8 ta = new ThreadAufgabe8(i);
            list.add(ta);
            ta.start();
        }

        // Wait for all threads to finish and print a message on the screen
        for(ThreadAufgabe8 thread : list) {
            thread.join();
        }
        System.out.println("All Threads finished!");

    }
}
