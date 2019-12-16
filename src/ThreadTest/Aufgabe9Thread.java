package ThreadTest;

public class Aufgabe9Thread extends Thread{
	
	private int[] array;
	private int begin;
	private int size;
	
	public Aufgabe9Thread(int begin, int size, int[] array){
		this.array = array;
		this.begin = begin;
		this.size = size;
	}
	
	public void run() {
		for(int i = begin; i < begin + size; ++i) {
			array[i] = (int) (Math.random() * 100);
		}
		//System.out.println("Finished: " + begin);
	}
	
}
