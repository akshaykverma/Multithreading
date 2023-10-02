package com.multithreading.diningphilosopher;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.multithreading.diningphilosopher.constants.AppConstants;
import com.multithreading.diningphilosopher.models.Chopstick;
import com.multithreading.diningphilosopher.models.Philosopher;

public class DiningPhilosopher {
	
	public static void main(String args[]) {
		
		ExecutorService threadPool = Executors.newFixedThreadPool(AppConstants.NUMBER_OF_PHILOSOPHERS);
		
		int leftChopstick;
		int rightChopstick;
		Philosopher philosopher; 
		
		//TODO: create a list of Philosopher threads 
		//TODO: create a list of Philosophers objects
		//TODO: create a list of Chop sticks threads 
		
		try {
			
			// creating philosopher threads
			for (int i = 0 ; i < AppConstants.NUMBER_OF_PHILOSOPHERS; i++) {
				
				// calculating the left and right chop sticks based on the philosopher id
				leftChopstick = (i-1) % AppConstants.NUMBER_OF_CHOPSTICKS;
				rightChopstick = i;
				
				philosopher = new Philosopher(i, new Chopstick(leftChopstick), new Chopstick(rightChopstick));
				threadPool.execute(new PhilosopherThread(philosopher));
			}
			
			// Sleeping the main thread for certain amount of time so that the simulation can continue
			Thread.sleep(AppConstants.SIMULATION_RUNNING_TIME);
			
			//TODO: Stop the running philosopher threads.
			
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		finally {
			
			threadPool.shutdown();
			
			while(!threadPool.isTerminated()) {
				System.out.println("Waiting for the threads to gracefully terminate...");
			}
		}
		
		
	}

}
