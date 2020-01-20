package Aufgabe11;


import java.util.Random;
import java.util.concurrent.Semaphore;

public class Philosoph extends Thread {
    private boolean isEating;
    private Philosoph left;
    private Philosoph right;
    private int id;
    private Semaphore mutex;

    Philosoph(int id, Semaphore mutex) {
        this.id = id;
        this.mutex = mutex;
    }
    public void setLeft(Philosoph left) {
        this.left = left;
    }
    public void setRight(Philosoph left) {
        this.right = left;
    }
    private void eat() {
        this.isEating(true);
        this.mutex.release();
        System.out.println(this.id + " is eating");
        try {
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.id + " has been finished his meal");
        this.isEating(false);


    }

    @Override
    public void run() {
        boolean run = false;
        while(!run) {
            try {
                this.mutex.acquire();
                if (left.getIsEating() == false && right.getIsEating() == false) {
                    this.eat();
                    run = true;
                }else {
                    this.mutex.release();
                }

                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private synchronized void isEating(boolean isEating) {
        this.isEating = isEating;
    }
    public synchronized boolean getIsEating() {
        return isEating;
    }
}
