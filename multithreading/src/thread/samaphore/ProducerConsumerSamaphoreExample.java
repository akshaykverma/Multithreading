package thread.samaphore;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerSamaphoreExample {

	public static void main(String args[]) {
		
		final int CAPACITY = 5;
		Random items = new Random();
		Inventory inventory = new Inventory(CAPACITY);
		
		// producer threads
		List<Thread> producerThreads = new ArrayList<>();
		producerThreads.add(new Producer(inventory, items));
		producerThreads.add(new Producer(inventory, items));
		producerThreads.add(new Producer(inventory, items));
		producerThreads.add(new Producer(inventory, items));
		producerThreads.add(new Producer(inventory, items));
		
		// consumer threads
		List<Thread> consumerThreads = new ArrayList<>();
		consumerThreads.add(new Consumer(inventory));
		consumerThreads.add(new Consumer(inventory));
		consumerThreads.add(new Consumer(inventory));
		
		// starting producer threads
		for (Thread thread : producerThreads) {
			thread.start();
		}
		
		// starting consumer threads
		for (Thread thread : consumerThreads) {
			thread.start();
		}
		
	}
	
	/**
	 * Inventory contains the queue, lock, capacity, empty and full semaphores
	 */
	private static class Inventory {
		private Queue<Integer> queue;
		private int capacity;
		
		// used for the critical section (queue)
		private ReentrantLock lock;
		
		// handles the producers 
		private Semaphore empty;
		
		// handles the consumers
		private Semaphore full;
		
		public Inventory(int capacity) {
			this.capacity = capacity;
			queue = new LinkedList<Integer>();
			lock = new ReentrantLock();
			empty = new Semaphore(capacity);
			
			// set to 0 so that the consumer does not start before producer
			full = new Semaphore(0);
		}
		
		/**
		 * Produce Item and add to the queue
		 * @param item
		 */
		public void produceItem(int item) {
			try {
				empty.acquire();
				lock.lock();
				if(queue.size() < capacity) {
					queue.add(item);	
					System.out.println(item + " produced");
					//Thread.sleep(1000);
				} else {
					System.out.println("Queue is Full");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
				full.release();
			}	
		}
		
		/**
		 * consume the items from the queue
		 */
		public void consumeItem() {
			try {
				full.acquire();
				lock.lock();
				if(queue.size() != 0) {
					System.out.println(queue.peek() + " consumed");
					queue.remove();		
					Thread.sleep(1000);
				} else {
					System.out.println("Queue is empty");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
				empty.release();
			}	
			
		}
	}
	
	/**
	 * Producer Thread
	 */
	private static class Producer extends Thread {
		private Inventory inventory;
		private Random items;
		
		Producer(Inventory inventory, Random items) {
			this.inventory = inventory;
			this.items = items;
		}
		
		@Override
		public void run() {
			while(true) {
				inventory.produceItem(items.nextInt(1000));
			}
		}
	}
	
	/**
	 * Consumer Thread
	 */
	private static class Consumer extends Thread {
		private Inventory inventory;
		
		Consumer(Inventory inventory) {
			this.inventory = inventory;
		}
		
		@Override
		public void run() {
			while(true) {
				inventory.consumeItem();
			}
		}
		
	}
}
