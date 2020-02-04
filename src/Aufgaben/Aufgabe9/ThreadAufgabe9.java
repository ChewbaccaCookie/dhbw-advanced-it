package Aufgaben.Aufgabe9;

public class ThreadAufgabe9 extends Thread{
    private int min;
    private int max;
    private long sum = 0;

    public ThreadAufgabe9(int min, int max) {
        this.min = min;
        this.max = max;
    }
    public long getSum() {
        return sum;
    }

    @Override
    public void run() {
        int[] numArr = Aufgabe9.getNumArr();
        for(int i = this.min; i < (this.max - this.min); i++) {
            this.sum += numArr[i];
        }
    }
}
