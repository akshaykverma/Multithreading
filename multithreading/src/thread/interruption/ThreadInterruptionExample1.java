package thread.interruption;

public class ThreadInterruptionExample1 {

	public static void main(String args[]) {
		
		Thread thread = new Thread(new BlockingTask());
		thread.start();
		thread.interrupt();
	}
	
	private static class BlockingTask implements Runnable {

		@Override
		public void run() {
			
			try {
				Thread.sleep(300000);
			} catch (InterruptedException e) {
				System.out.println("Exiting the thread due to interruption");
			}
			
		}
		
	}
	 
}
