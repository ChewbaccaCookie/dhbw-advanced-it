package ThreadTest;

public class Aufgabe9Calc extends Thread{
	
	int sum = 0;
	int[] array;
	int begin;
	int size;
	
	public Aufgabe9Calc(int[] array, int begin, int size) {
		this.array = array;
		this.begin = begin;
		this.size = size;
	}
	
	public void run() {
		for(int i = begin; i < begin + size; ++i) {
			sum += array[i];
		}
	}
	
}
