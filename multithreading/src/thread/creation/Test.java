package thread.creation;

public class Test {

	public static void main(String[] args) {
		
		Thread one = new Thread(new Worker());
		Thread two = new Thread(new Worker());
		
		one.start();
		two.start();
		
	}
	
	public static class Worker implements Runnable {

		@Override
		public void run() {
			
			for (int i = 0; i < 10; i++) {
			System.out.println(i);
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

}
