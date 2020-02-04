package Aufgaben.Aufgabe10;

public class Aufgabe10 extends Thread{
    VerketteteList list;
    char indicator;
    int numEntries;
    public Aufgabe10(VerketteteList list, char indicator, int numEntries) {
     this.list = list;
     this.indicator = indicator;
     this.numEntries = numEntries;
    }
    @Override
    public void run() {
        for (int i = 0; i < this.numEntries; i++) {
            this.list.push("" + i + this.indicator);
            this.list.get();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        VerketteteList list = new VerketteteList(1);
        Aufgabe10 a = new Aufgabe10(list, 'a', 100000);
        Aufgabe10 b = new Aufgabe10(list, 'b', 100000);
        a.start();
        b.start();
        a.join();
        b.join();

        System.out.println(list.count());

    }
}
