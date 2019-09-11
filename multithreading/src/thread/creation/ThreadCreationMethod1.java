package thread.creation;

import java.lang.Thread.UncaughtExceptionHandler;

public class ThreadCreationMethod1 {

	public static void main(String args[]) throws InterruptedException {
		
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				throw new RuntimeException("error message");
			}
		});
		
		thread.setName("New Worker Thread");
		thread.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.out.println("Uncaught Exception from thread : " + t.getName() +
						", error message : " + e.getMessage());
			}
		});
		thread.start();
	}
}
