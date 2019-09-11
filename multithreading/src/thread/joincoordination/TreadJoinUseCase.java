package thread.joincoordination;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TreadJoinUseCase {

	public static void main(String args[]) {
		
		List<Long> numbers = Arrays.asList(100000L, 3435L, 34676L, 36788L, 23L, 55567L);
		List<FactorialCalculationThread> threads = new ArrayList<>();
		
		for (Long number : numbers) {
			threads.add(new FactorialCalculationThread(number));
		}
		
		for (FactorialCalculationThread thread : threads) {
			// handling 100000L case
			thread.setDaemon(true);
			thread.start();
		}
		
		for (FactorialCalculationThread thread : threads) {
			try {
				thread.join(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		for (FactorialCalculationThread thread : threads) {
			if(thread.isFactororialDone) {
				System.out.println("Factorial of " + thread.number + "! is : " + thread.getResult().toString());
			} else {
				System.out.println("Factorial of : " + thread.number + " is still in progress");
			}
		}
	}
	
	
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
				result = result.multiply(new BigInteger(Long.toString(i)));
			}
			isFactororialDone = true;
		}
		
		public BigInteger getResult() {
			return result;
		}
	}
}
