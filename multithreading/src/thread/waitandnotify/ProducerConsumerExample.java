package thread.waitandnotify;

import java.util.ArrayList;
import java.util.List;

public class ProducerConsumerExample {

	public static void main(String args[]) throws InterruptedException {
		Processor processor = new Processor();
		
		Thread producerThread = new Thread(() -> {
			try {
				processor.producer();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		Thread consumerThread = new Thread(() -> {
			try {
				processor.consumer();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		producerThread.start();
		consumerThread.start();
		
		producerThread.join();
		consumerThread.join();
	}
	
	
	public static class Processor {
		
		private List<Integer> list = new ArrayList<>();
		private static final int LIMIT = 5;
		private Object lock = new Object();
		
		public void producer() throws InterruptedException {
			
			synchronized (lock) {
				int item;
				while(true) {
					
					if(list.size() == LIMIT) {
						System.out.println("Producer waiting....");
						lock.wait();
					} else {
						item = 1 + list.size();
						list.add(item);
						System.out.println("Produced Item : " + item);
						lock.notify();
					}
					Thread.sleep(500);
				}
			}
			
		}
		
		public void consumer() throws InterruptedException {

			synchronized (lock) {
				while (true) {

					if (list.size() == 0) {
						System.out.println("Consumer waiting....");
						lock.wait();
					} else {
						System.out.println("Consumed Item : " + list.remove(0));
						lock.notify();
					}
					Thread.sleep(500);
				}
				
			}
		}
	}
	
}
