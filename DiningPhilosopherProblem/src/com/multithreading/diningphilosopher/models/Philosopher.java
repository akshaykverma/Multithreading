package com.multithreading.diningphilosopher.models;

import java.util.Random;

import com.multithreading.diningphilosopher.enums.ChopstickState;

public class Philosopher {

	private int id;
	private int eatCount;
	private Chopstick leftChopstick;
	private Chopstick rightChopstick;
	private Random randomSleep;

	public Philosopher(int id, int eatCount, Chopstick leftChopstick, Chopstick rightChopstick) {
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
	 * The Philosopher is eating
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
				// This is a atomic operation as its inside the chopstick lock
				eatCount++;
				Thread.sleep(randomSleep.nextInt(500));
			}
			
			// release the lock on right chop stick
			rightChopstick.putDown();
			System.out.println(this + " has put down the " + rightChopstick);
		}
		
		// release the lock on left chop stick
		leftChopstick.putDown();
		System.out.println(this + " has put down the " + leftChopstick);
	}

	public int getEatCount() {
		return eatCount;
	}
	
	@Override
	public String toString() {
		return "Philosopher " + id;
	}
}
