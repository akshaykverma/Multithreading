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
		
		List<Thread> producerThreads = new ArrayList<>();
		producerThreads.add(new Producer(inventory, items));
//		producerThreads.add(new Producer(inventory, items));
//		producerThreads.add(new Producer(inventory, items));
//		producerThreads.add(new Producer(inventory, items));
//		producerThreads.add(new Producer(inventory, items));
//		
		List<Thread> consumerThreads = new ArrayList<>();
		consumerThreads.add(new Consumer(inventory, items));
		consumerThreads.add(new Consumer(inventory, items));
		consumerThreads.add(new Consumer(inventory, items));

		for (Thread thread : producerThreads) {
			thread.start();
		}

		for (Thread thread : consumerThreads) {
			thread.start();
		}
		
		
	}
	
	private static class Inventory {
		private Queue<Integer> queue;
		private int capacity;
		private ReentrantLock lock;
		private Semaphore empty;
		private Semaphore full;
		
		public Inventory(int capacity) {
			this.capacity = capacity;
			queue = new LinkedList<Integer>();
			lock = new ReentrantLock();
			empty = new Semaphore(1);
			full = new Semaphore(0);
		}
		
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
	

	private static class Consumer extends Thread {
		private Inventory inventory;
		private Random items;
		
		Consumer(Inventory inventory, Random items) {
			this.inventory = inventory;
			this.items = items;
		}
		
		@Override
		public void run() {
			while(true) {
				inventory.consumeItem();
			}
		}
		
	}
}
