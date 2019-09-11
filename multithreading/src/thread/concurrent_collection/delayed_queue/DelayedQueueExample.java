package thread.concurrent_collection.delayed_queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
/**
 * This is an unbounded BlockingQueue of objects that implement the Delayed
 * interface
 * 
 * - DelayQueue keeps the elements internally until a certain delay has expired
 * 
 * - an object can only be taken from the queue when its delay has expired !!! -
 * 
 * We cannot place null items in the queue - The queue is sorted so that the
 * object at the head has a delay that has expired for the longest time.
 * 
 * If no delay has expired, then there is no head element and poll( ) will
 * return null
 * 
 * size() return the count of both expired and unexpired items !!!
 *
 */

public class DelayedQueueExample {

	public static void main(String args[]) {
		
		BlockingQueue<DelayedWorker> blockingQueue = new DelayQueue<>();
		
		try {
			blockingQueue.put(new DelayedWorker(1000, "This is the first message"));
			blockingQueue.put(new DelayedWorker(10000, "This is the second message"));
			blockingQueue.put(new DelayedWorker(4000, "This is the third message"));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		while(!blockingQueue.isEmpty()) {
			try {
				System.out.println(blockingQueue.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	
	private static class DelayedWorker implements Delayed {

		private long duration;
		private String message;
		
		public DelayedWorker(long delayed, String message) {
			this.duration = delayed + System.currentTimeMillis();
			this.message = message;
		}

		@Override
		public int compareTo(Delayed otherDelayed) {
			if(duration < ((DelayedWorker) otherDelayed).getDelayed()) {
				return -1;
			} else if (duration > ((DelayedWorker) otherDelayed).getDelayed()) {
				return 1;
			} else {
				return 0;
			}
		}

		@Override
		public long getDelay(TimeUnit timeUnit) {
			return timeUnit.convert(duration - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
		}

		public long getDelayed() {
			return duration;
		}

		public void setDelayed(long delayed) {
			this.duration = delayed;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		@Override
		public String toString() {
			return "DelayedWorker [message=" + message + "]";
		}
	}
}
