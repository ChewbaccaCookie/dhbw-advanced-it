package Aufgaben.Aufgabe8;

public class ThreadAufgabe8 extends Thread {
    private int id;
    public ThreadAufgabe8(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println(String.format("Thread %s started", this.id));
        try { Thread.sleep((int) (Math.random() * 1000));
        } catch (InterruptedException e) {e.printStackTrace();}
        System.out.println(String.format("Thread %s stopped", this.id));
    }
}
