package com.multithreading.diningphilosopher;

import com.multithreading.diningphilosopher.models.Philosopher;

public class PhilosopherThread implements Runnable {

	private Philosopher philosopher;
	private volatile boolean isFull;
	
	public PhilosopherThread(Philosopher philosopher) {
		this.philosopher = philosopher;
		this.isFull = false;
	}

	@Override
	public void run() {
		
		while (!isFull) {
			try {

				// think
				philosopher.think();
				
				// eat
				philosopher.eat();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void setFull(boolean isFull) {
		this.isFull = isFull;
	}
}
