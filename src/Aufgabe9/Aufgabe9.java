package Aufgabe9;

import Helpers.BasicTable;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.ArrayList;

public class Aufgabe9 {

    private static int[] numArr = new int[(int) Math.pow(2,25)];
    private static long expectedSum = 0;

    public static void main(String[] args) throws InterruptedException {
        final int MAX_THREADS = 20;
        fillArray();
        System.out.println("Erwartete Summe");
        System.out.println(expectedSum);

        ArrayList<ThreadAufgabe9> runningThreads = new ArrayList<>();
        String[][] millis = new String[MAX_THREADS][4];
        for(int i = 1; i <= MAX_THREADS; i++) {
            int step = (int) ((double)numArr.length / (double)i);
            int currentNum = 0;
            runningThreads.clear();

            // Create Threads
            for(int ii = 0; ii < i; ii++) {
                ThreadAufgabe9 thread = new ThreadAufgabe9(currentNum, currentNum + step);
                runningThreads.add(thread);
                currentNum += step;
            }
            System.out.println(currentNum);
            System.out.println("----------------------");

            // Start Threads and messurement
            long startMillis = System.nanoTime();
            for(ThreadAufgabe9 thread: runningThreads) {
                thread.start();
            }
            long sum = 0;
            for(ThreadAufgabe9 thread: runningThreads) {
                thread.join();
                sum += thread.getSum();
            }
            long time = System.nanoTime() - startMillis;

            millis[i-1][0] = i + "";
            millis[i-1][1] = time + "";
            millis[i-1][2] = sum == expectedSum ? "Richtig" : "Falsch";
            millis[i-1][3] = sum + "";
        }
        BasicTable.generateTable(new String[]{"Anzahl", "Dauer", "Korrektheit", "Summe"},millis, new int[]{10,20,15, 15}, new String[]{"left", "left", "left", "left"});
    }

    private static void fillArray() {
        for(int i = 0; i < numArr.length; i++) {
            numArr[i] = (int)(Math.random() * 100);
            expectedSum += numArr[i];
        }
    }

    public static int[] getNumArr() {
        return numArr;
    }

}
