package com.multithreading.diningphilosopher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.multithreading.diningphilosopher.constants.AppConstants;
import com.multithreading.diningphilosopher.models.Chopstick;
import com.multithreading.diningphilosopher.models.Philosopher;

/**
 * Simulation of the Dining Philosopher Problem
 */
public class DiningPhilosopher {
	
	public static void main(String args[]) throws InterruptedException {
		
		ExecutorService threadPool = Executors.newFixedThreadPool(AppConstants.NUMBER_OF_PHILOSOPHERS);
		
		int rightChopstick;
		
		//list of Philosopher threads 
		List<PhilosopherThread> threads = new ArrayList<>();

		//list of Philosophers objects
		List<Philosopher> philosophers = new ArrayList<>();

		//list of Chop stick objects 
		List<Chopstick> chopsticks = new ArrayList<>();
		
		
		// adding Chop sticks
		for (int i = 0 ; i < AppConstants.NUMBER_OF_CHOPSTICKS; i++) {
			chopsticks.add(new Chopstick(i));
		}
		
		// adding Philosophers
		for (int i = 0 ; i < AppConstants.NUMBER_OF_PHILOSOPHERS; i++) {
			
			// calculating the right chop stick based on the philosopher id
			rightChopstick = (i+1) % AppConstants.NUMBER_OF_CHOPSTICKS;
			
			philosophers.add(new Philosopher(i, chopsticks.get(i), chopsticks.get(rightChopstick)));
		}
		
		try {
			
			// creating philosopher threads
			for (int i = 0 ; i < AppConstants.NUMBER_OF_PHILOSOPHERS; i++) {
				
				PhilosopherThread philosopherThread = new PhilosopherThread(philosophers.get(i)); 
				threads.add(philosopherThread);
				
				// running the philosopher threads
				threadPool.execute(philosopherThread);
			}
			
			// Sleeping the main thread for certain amount of time so that the simulation can continue
			Thread.sleep(AppConstants.SIMULATION_RUNNING_TIME);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		finally {
			
			//Stop the running philosopher threads.
			for (int i = 0 ; i < AppConstants.NUMBER_OF_PHILOSOPHERS; i++) {
				threads.get(i).setFull(true);
			}
			
			//Initiates an orderly shutdown in which previously submitted tasks are executed, but no new tasks will be accepted. 
			threadPool.shutdown();
			
			// Returns true if all tasks have completed following shut down.
			// Note that isTerminated is never true unless either shutdown or shutdownNow was called first.
			while(!threadPool.isTerminated()) {
				Thread.sleep(1000);
				System.out.println("Waiting for the threads to gracefully terminate...");
			}
			
			// Display how many times each philosopher has eaten
			for(Philosopher philosopher : philosophers) {
				System.out.println(philosopher + " has eaten " + philosopher.getEatCount() + " times");
			}
		}
		
		
	}

}
