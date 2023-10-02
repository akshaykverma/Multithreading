package com.multithreading.diningphilosopher.models;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.multithreading.diningphilosopher.enums.ChopstickState;

public class Chopstick {
	
	private int id;
	private Lock lock;
	
	public Chopstick(int id) {
		this.id = id;
		this.lock = new ReentrantLock();
	}
	
	/**
	 * Trying to pick the chopstick using the tryLock with a waiting time of 10ms (Acquires the lock if it is free within the given waiting time)
	 * @param philosopher
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
	 * Putting down the chopstick by releasing the lock
	 * @param philosopher
	 */
	public void putDown() {
		lock.unlock();
	}
	
	@Override
	public String toString() {
		return "Chopstick " + id;
	}

}
