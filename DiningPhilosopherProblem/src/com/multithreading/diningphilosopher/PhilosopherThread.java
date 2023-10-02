package com.multithreading.diningphilosopher;

import com.multithreading.diningphilosopher.models.Philosopher;

public class PhilosopherThread implements Runnable {

	private Philosopher philosopher;
		
	public PhilosopherThread(Philosopher philosopher) {
		this.philosopher = philosopher;
	}

	@Override
	public void run() {
		
		try {
			philosopher.think();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
