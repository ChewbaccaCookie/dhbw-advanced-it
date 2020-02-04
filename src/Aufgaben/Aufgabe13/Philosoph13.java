package Aufgaben.Aufgabe13;

public class Philosoph13 extends Thread{
    int id;
    PhilosophMonitor pm;

    public Philosoph13(int id, PhilosophMonitor pm) {
        this.id = id;
        this.pm = pm;
    }

    @Override
    public void run() {
        try {
            pm.startEat(this.id);

            System.out.println(this.id + " eats!");
            Thread.sleep((long) Math.random() * 1000 + 100);
            System.out.println(this.id + " has finished!");

            pm.endEat(this.id);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
