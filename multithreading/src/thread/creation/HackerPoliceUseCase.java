package thread.creation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HackerPoliceUseCase {

	private static final int MAX_PASSWORD_LIMIT = 9999;
	
	public static void main(String args[]) {
		
		Random random = new Random(); 
		
		// random vault password
		int password = random.nextInt(MAX_PASSWORD_LIMIT);
		System.out.println("Password : " + password);
		
		Vault vault = new Vault(password); 
		
		Thread policeThread = new PoliceThread();
		policeThread.setName(policeThread.getClass().getName());
		
		List<Thread> threads = new ArrayList<>();
		threads.add(new AscendingHackerThread(vault));
		threads.add(new DescendingHackerThread(vault));
		threads.add(policeThread);
		
		for (Thread thread : threads) {
			thread.start();
		}
	}
	
	/**
	 * Vault Creation which the hackers are tying to break.
	 *
	 */
	private static class Vault {
		private int password;

		public Vault(int password) {
			this.password = password;
		}

		public boolean isCorrectPassword(int pass) {
			return password == pass;
		}
	}

	/**
	 * Abstract method that has the access to the vault 
	 * and provides abstract method run for the hackers to implement 
	 * where they write their logic to break the vault by guessing the password
	 */
	private static abstract class HackerThread extends Thread {
		protected Vault vault;
		
		public HackerThread(Vault vault) {
			this.vault = vault;
			this.setName(this.getClass().getSimpleName());
			this.setPriority(MAX_PRIORITY);
		}
		
		@Override
		public abstract void run();

		@Override
		public void start() {
			System.out.println("Starting thread : " + this.getName());
			super.start();
		}
	}
	
	/**
	 * This hacker implements run by guessing the password in ascending order
	 * 
	 */
	private static class AscendingHackerThread extends HackerThread {

		public AscendingHackerThread(Vault vault) {
			super(vault);
		}

		@Override
		public void run() {
			
			for(int i=0; i<=MAX_PASSWORD_LIMIT; i++) {
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				if(vault.isCorrectPassword(i)) {
					System.out.println("Thread : " + this.getName() + " accessed the Vault");
					System.exit(0);
				}
			}
		}
	}
	
	/**
	 * This hacker implements run() by guessing the password in descending order.
	 */
	private static class DescendingHackerThread extends HackerThread {

		public DescendingHackerThread(Vault vault) {
			super(vault);
		}

		@Override
		public void run() {
			
			for(int i=MAX_PASSWORD_LIMIT; i>=0; i--) {
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				if(vault.isCorrectPassword(i)) {
					System.out.println("Thread : " + this.getName() + " accessed the Vault");
					System.exit(0);
				}
			}
		}
	}
	
	/**
	 * Police thread running at a delay of 1s and if it completes the hackers are caught
	 */
	private static class PoliceThread extends Thread {
		
		@Override
		public void run() {
			for (int i = 10; i >= 1; i--) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				System.out.println(i);
			}
			
			System.out.println("Game Over by : " + this.getClass().getSimpleName());
			System.exit(0);
		}
	}
}
