package com.multithreading.diningphilosopher.models;

import java.util.Random;

import com.multithreading.diningphilosopher.enums.ChopstickState;

/**
 * 
 * Representation of a Philosopher
 *
 */
public class Philosopher {

	private int id;
	private int eatCount;
	private Chopstick leftChopstick;
	private Chopstick rightChopstick;
	
	/**
	 * Used for setting a random time to sleep while thinking and eating
	 */
	private Random randomSleep;

	public Philosopher(int id, Chopstick leftChopstick, Chopstick rightChopstick) {
		this.id = id;
		this.leftChopstick = leftChopstick;
		this.rightChopstick = rightChopstick;
		this.randomSleep = new Random();
	}

	/**
	 * The Philosopher is thinking
	 * @throws InterruptedException
	 */
	public void think() throws InterruptedException {
		System.out.println("Philosopher " + id + " is thinking...");
		Thread.sleep(randomSleep.nextInt(500));
	}
	
	/**
	 * The Philosopher is eating by picking the left and right chop sticks 
	 * @throws InterruptedException 
	 */
	public void eat() throws InterruptedException {
		
		// trying to pick the left chop stick
		if (leftChopstick.pickChopstick(ChopstickState.LEFT)) {
			System.out.println(this + " picked up the " + ChopstickState.LEFT.toString() + " " + leftChopstick);
			
			// trying to pick the right chop stick
			if (rightChopstick.pickChopstick(ChopstickState.RIGHT)) {
				
				System.out.println(this + " picked up the " + ChopstickState.RIGHT.toString() + " " + rightChopstick);
				System.out.println(this + " is eating...");
				
				// increasing the counter. 
				// This is a atomic operation as its inside the chop stick lock
				eatCount++;
				Thread.sleep(randomSleep.nextInt(500));

				// release the lock on right chop stick
				rightChopstick.putDown();
				System.out.println(this + " has put down the " + rightChopstick);
			}
			
			// release the lock on left chop stick
			leftChopstick.putDown();
			System.out.println(this + " has put down the " + leftChopstick);
		}
	}

	public int getEatCount() {
		return eatCount;
	}
	
	@Override
	public String toString() {
		return "Philosopher " + id;
	}
}
