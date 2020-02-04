package Aufgaben.Aufgabe13;

public class Aufgabe13 {

    public static void main(String[] args) {
        Philosoph13[] philosoph13s = new Philosoph13[5];
        PhilosophMonitor pm = new PhilosophMonitor(5);

        for(int i = 0; i<5; i++) {
            philosoph13s[i] = new Philosoph13(i,pm);
            philosoph13s[i].start();
        }

    }

}
