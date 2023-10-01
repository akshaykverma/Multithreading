package thread.waitandnotify;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class ProducerConsumerExample {

	public static void main(String args[]) throws InterruptedException {
		
		Processor processor = new Processor(5);
		
		Thread producerThread = new Thread(() -> {
			try {
				
				processor.producer();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		Thread consumerThread = new Thread(() -> {
			try {
				processor.consumer();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		producerThread.start();
		consumerThread.start();
		
	}
	
	
	public static class Processor {
		
		private Queue<Integer> queue;
		private int limit;
		private final Object lock;
		
		public Processor(int limit) {
			super();
			queue = new LinkedList<>();
			this.limit = limit;
			this.lock = new Object();
		}
		
		/**
		 * Produce Method
		 * @throws InterruptedException
		 */
		public void producer() throws InterruptedException {
			
			Random random = new Random();

			while(true) {
				int item = random.nextInt(1000);
				synchronized (lock) {
					if(queue.size() == limit) {
						System.out.println("Producer waiting due to limit of " + this.limit + " ....");
						lock.wait();
					} else {
						queue.add(item);
						System.out.println("Produced Item : " + item);
						lock.notify();
					}
				} // lock is released here
				
				// faster production
				Thread.sleep(500);
			}
			
		}
		
		/**
		 * Consume method
		 * @throws InterruptedException
		 */
		public void consumer() throws InterruptedException {

			while (true) {
				synchronized (lock) {
					if (queue.size() == 0) {
						System.out.println("Consumer waiting....");
						lock.wait();
					} else {
						System.out.println("Consumed Item : " + queue.poll());
						lock.notify();
					}
				} // lock is release here 
				
				// slower production
				Thread.sleep(1000);				
			}
		}
	}
	
}
