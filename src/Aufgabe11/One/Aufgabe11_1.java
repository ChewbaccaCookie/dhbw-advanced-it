package Aufgabe11.One;

import java.util.concurrent.Semaphore;

public class Aufgabe11_1{

    public static void main(String[] args) {
        Semaphore[] forks = new Semaphore[5];
        Philosopher[] list = new Philosopher[5];
        for(int i = 0; i < 5; i++) {
            forks[i] = new Semaphore(1, true);
            list[i] = new Philosopher(i, forks);
        }
        for(Philosopher p: list) {
            p.start();
        }
    }

}

class Philosopher extends Thread{
    private final int id;
    private final Semaphore[] forks;

    public Philosopher(int id, Semaphore[] forks) {
        this.id = id;
        this.forks = forks;
    }

    @Override
    public void run() {
        try {
            int y = this.id < 4 ? this.id + 1 : 0;
            forks[this.id].acquire();
            forks[y].acquire();
            System.out.println(String.format("Philosopher %s starts eating", this.id));
            Thread.sleep((int) (Math.random() * 2000));
            System.out.println(String.format("Philosopher %s stops eating", this.id));

            forks[this.id].release();
            forks[y].release();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}