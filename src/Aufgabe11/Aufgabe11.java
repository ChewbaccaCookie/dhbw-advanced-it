package Aufgabe11;

import java.util.concurrent.Semaphore;

public class Aufgabe11 {

    public static void main(String[] args) {
        Philosoph[] philosophs = new Philosoph[5];
        Semaphore mutex = new Semaphore(1);
        for(int i = 0; i<5; i++) {
            philosophs[i] = new Philosoph(i,mutex);
        }
        for(int i = 0; i<5; i++) {
            int left = i > 0 ? i-1 : 4;
            int right = i < 4 ? i+1 : 0;
            philosophs[i].setLeft(philosophs[left]);
            philosophs[i].setRight(philosophs[right]);
        }

        for(int i = 0; i<5; i++) {
            philosophs[i].start();
        }
    }

}
