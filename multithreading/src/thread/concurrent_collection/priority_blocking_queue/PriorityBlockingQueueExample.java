package thread.concurrent_collection.priority_blocking_queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
/**
 * It implements the BlockingQueue interface
 * 
 * 	- unbounded concurrent queue
 * 	- it uses the same ordering rules as the java.util.PriorityQueue class -> have to implement the Comparable interface
 * 			The comparable interface will determine what will the order in the queue
 * 
 * 			The priority can be the same compare() == 0 case
 * 
 *  - no null items !!!
 * 
 *
 */
public class PriorityBlockingQueueExample {

	public static void main(String args[]) {
		
		BlockingQueue<String> blockingQueue = new PriorityBlockingQueue<>();
		
		new Thread(new FirstWorker(blockingQueue)).start();
		new Thread(new SecondWorker(blockingQueue)).start();
		
	}
	
	
	private static class FirstWorker implements Runnable{

		private BlockingQueue<String> blockingQueue;
		
		public FirstWorker(BlockingQueue<String> blockingQueue) {
			this.blockingQueue = blockingQueue;
		}

		@Override
		public void run() {
			try {
				blockingQueue.put("B");
				blockingQueue.put("H");
				blockingQueue.put("F");
				Thread.sleep(1000);
				blockingQueue.put("A");
				Thread.sleep(1000);
				blockingQueue.put("E");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		
	}
	
	private static class SecondWorker implements Runnable{

		private BlockingQueue<String> blockingQueue;
		
		public SecondWorker(BlockingQueue<String> blockingQueue) {
			this.blockingQueue = blockingQueue;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(5000);
				System.out.println(blockingQueue.take());
				Thread.sleep(1000);
				System.out.println(blockingQueue.take());
				Thread.sleep(1000);
				System.out.println(blockingQueue.take());
				System.out.println(blockingQueue.take());
				System.out.println(blockingQueue.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
