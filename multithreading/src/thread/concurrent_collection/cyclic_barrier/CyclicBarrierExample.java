package thread.concurrent_collection.cyclic_barrier;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * Latch --> multiple threads can wait for each other
 *
 * A CyclicBarrier is used in situations where you want to create a group of
 * tasks to perform work in parallel + wait until they are all finished before
 * moving on to the next step -> something like join() -> something like
 * CountDownLatch
 * 
 * CountDownLatch: one-shot event CyclicBarrier: it can be reused over and over
 * again
 * 
 * + cyclicBarrier has a barrier action: a runnable, that will run automatically
 * when the count reaches 0 !!
 * 
 * new CyclicBarrier(N) -> N threads will wait for each other
 *
 * WE CAN NOT REUSE LATCHES BUT WE CAN REUSE CyclicBarriers --> reset() !!!
 * 
 */

public class CyclicBarrierExample {

	public static void main(String args[]) {
		
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		
		CyclicBarrier cyclicBarrier = new CyclicBarrier(5, ()-> {
			System.out.println("All tasks are finished");
		});
		
		
		for (int i = 0; i < 5; i++) {
			executorService.execute(new Worker(i+1, new Random(), cyclicBarrier));
		}
		
		executorService.shutdown();
	}
	
	
	private static class Worker implements Runnable
	{
		private int id;
		private Random random;
		private CyclicBarrier cyclicBarrier; 
	
		public Worker(int id, Random random, CyclicBarrier cyclicBarrier) {
			this.id = id;
			this.random = random;
			this.cyclicBarrier = cyclicBarrier;
		}

		@Override
		public void run() {
			
			System.out.println("starting worker id : " + id);
			try {
				Thread.sleep(random.nextInt(3000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			try {
				cyclicBarrier.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
			System.out.println("finished id : " +id);
		}
	}
}
