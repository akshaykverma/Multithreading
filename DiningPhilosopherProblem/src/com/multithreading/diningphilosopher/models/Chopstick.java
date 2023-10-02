package com.multithreading.diningphilosopher.models;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.multithreading.diningphilosopher.enums.ChopstickState;

/**
 * Represents the chop stick to be used by the philosophers
 *
 */
public class Chopstick {
	
	/**
	 * Unique id for a chop stick
	 */
	private int id;
	
	/**
	 *  lock to be used while picking and putting down the chop sticks
	 */
	private Lock lock;
	
	public Chopstick(int id) {
		this.id = id;
		this.lock = new ReentrantLock();
	}
	
	/**
	 * Trying to pick the chop stick using the tryLock with a waiting time of 10ms (Acquires the lock if it is free within the given waiting time)
	 * @param state
	 * @return
	 * @throws InterruptedException
	 */
	public boolean pickChopstick(ChopstickState state) throws InterruptedException {
		
		if (lock.tryLock(10, TimeUnit.MICROSECONDS)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Putting down the chop stick by releasing the lock
	 */
	public void putDown() {
		lock.unlock();
	}
	
	@Override
	public String toString() {
		return "Chopstick " + id;
	}

}
