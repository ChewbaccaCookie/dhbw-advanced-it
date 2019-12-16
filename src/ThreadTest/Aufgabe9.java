package ThreadTest;

public class Aufgabe9 {

	private static void runTest(int serviceWorkerCount) throws InterruptedException {
		Thread[] serviceWorker = new Thread[serviceWorkerCount];
		int[] werte = new int[(int)Math.pow(2, 23)];
		int serviceWorkerFieldSize = werte.length / serviceWorkerCount;
		int begin = 0;
		for(int i = 0; i < serviceWorkerCount; ++i) {
			serviceWorker[i] = new Aufgabe9Thread(begin, serviceWorkerFieldSize, werte);
			serviceWorker[i].start();
			begin += serviceWorkerFieldSize;
		}

		for(Thread t: serviceWorker) {
			t.join();
		}

		begin = 0;
		for(int i = 0; i < serviceWorkerCount; ++i) {
			serviceWorker[i] = new Aufgabe9Calc(werte, begin, serviceWorkerFieldSize);
			begin += serviceWorkerFieldSize;
		}

		long currentTime = System.currentTimeMillis();
		for(Thread t : serviceWorker) {
			t.start();
		}

		long sum = 0;
		for(int i = 0; i< serviceWorkerCount; ++i) {
			serviceWorker[i].join();
			sum += ((Aufgabe9Calc) serviceWorker[i]).sum;
		}

		System.out.println("Time needed: " + (System.currentTimeMillis() - currentTime));
		System.out.println("Sum: " + sum);
	}

	public static void main(String[] args) throws InterruptedException {
		Aufgabe9.runTest(1);
		Aufgabe9.runTest(2);
		Aufgabe9.runTest(4);
		Aufgabe9.runTest(8);
		Aufgabe9.runTest(16);
		Aufgabe9.runTest(32);

	}
	
}
