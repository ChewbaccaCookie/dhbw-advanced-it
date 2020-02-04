package Betriebsmittelverwaltung;

import java.util.concurrent.Semaphore;

public class Bmv {
    Semaphore printer;
    Semaphore mutex = new Semaphore(1, true);
    boolean[] printerBusy;

    public Bmv(int printerNum) {
        printer = new Semaphore(printerNum, true);
        printerBusy = new boolean[printerNum];
    }

    public void print() {
        try {
            printer.acquire();
            mutex.acquire();
            int printerId = -1;
            for (int i = 0; i < printerBusy.length; i++) {
                if (printerBusy[i] == false) {
                    printerId = i;
                    break;
                }
            }
            printerBusy[printerId] = true;
            mutex.release();

            // Printing ...

            System.out.println(String.format("Printer %s starts printing", printerId));
            Thread.sleep((int) Math.random() * 1000);
            System.out.println(String.format("Printer %s finished printing", printerId));

            // Printer stopped

            mutex.acquire();
            printerBusy[printerId] = false;
            mutex.release();
            printer.release();
        } catch (Exception ex) {
        }
    }

    public static void main(String[] args) {
        Bmv b = new Bmv(5);
        for (int ii = 0; ii < 5; ii++) {
            Thread t = new Thread() {
                public void run() {
                    for (int i = 0; i < 30; i++) {
                        b.print();
                    }
                }
            };
            t.start();
        }
    }
}
