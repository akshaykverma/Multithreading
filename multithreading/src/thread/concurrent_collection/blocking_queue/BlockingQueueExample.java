package thread.concurrent_collection.blocking_queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
/**
 * 
 * 	BlockingQueue -> an interface that represents a queue that is thread safe
 * 		Put items or take items from it ...
 * 
 * 		For example: one thread putting items into the queue and another thread taking items from it
 * 			at the same time !!!
 * 				We can do it with producer-consumer pattern !!!
 * 
 * 		put() putting items to the queue
 * 		take() taking items from the queue
 * 
 */

public class BlockingQueueExample {

	public static void main(String args[]) {
		
		BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<Integer>(10);
		
		new Thread(new Producer(blockingQueue)).start();
		new Thread(new Consumer(blockingQueue)).start();
		
	}
	
	private static class Producer implements Runnable{

		private int count;
		private BlockingQueue<Integer> blockingQueue;
		
		public Producer(BlockingQueue<Integer> blockingQueue) {
			this.count = 0;
			this.blockingQueue = blockingQueue;
		}

		@Override
		public void run() {
			while(true) {
				try {
					blockingQueue.put(++count);
					System.out.println("item added : " + count);
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}		
	}
	
	private static class Consumer implements Runnable{

		private BlockingQueue<Integer> blockingQueue;
		
		public Consumer(BlockingQueue<Integer> blockingQueue) {
			this.blockingQueue = blockingQueue;
		}

		@Override
		public void run() {
			while(true) {				
				try {
					int count = blockingQueue.take();
					System.out.println("item removed : " + count);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
