package thread.joincoordination;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TreadJoinUseCase {

	public static void main(String args[]) {
		
		List<Long> numbers = Arrays.asList(100000L, 3435L, 34676L, 36788L, 23L, 55567L);
		List<FactorialCalculationThread> threads = new ArrayList<>();
		
		// creating threads for calculating each fatorial number
		for (Long number : numbers) {
			threads.add(new FactorialCalculationThread(number));
		}
		
		// starting each thread
		for (FactorialCalculationThread thread : threads) {
			//thread.setDaemon(true);
			thread.start();
		}
		
		for (FactorialCalculationThread thread : threads) {
			try {
				// setting the main thread into a waiting state until all other threads completes within 2s. 
				// After 2s the main thread resumes
				thread.join(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		for (FactorialCalculationThread thread : threads) {
			if(thread.isFactororialDone) {
				System.out.println("Factorial of " + thread.number + "! is : " + thread.getResult().toString());
			} else {
				// handling 100000L case.
				// interrupting any thread which has not completed its calculations yet (after 2s)
				thread.interrupt();
				System.out.println("Factorial of : " + thread.number + " is still in progress");
			}
		}
	}
	
	/**
	 * Thread which handles the computation of a factorial number
	 */
	private static class FactorialCalculationThread extends Thread{

		private long number;
		private boolean isFactororialDone;
		private BigInteger result = BigInteger.ONE;
		
		public FactorialCalculationThread(long number) {
			this.number = number;
			this.isFactororialDone = false;
		}

		@Override
		public void run() {
			
			for (int i = 1; i < number; i++) {
				
				// handling the interrupt case. The current thread may be interrupted due to longer processing time to reach the result.
				if (Thread.currentThread().isInterrupted()) {
					System.out.println("The thread " + Thread.currentThread().getName() + "has been interrupted due to longer processing time");
					result = BigInteger.ZERO;
					break; 
				}
				result = result.multiply(new BigInteger(Long.toString(i)));
			}
			isFactororialDone = true;
		}
		
		public BigInteger getResult() {
			return result;
		}
	}
}
