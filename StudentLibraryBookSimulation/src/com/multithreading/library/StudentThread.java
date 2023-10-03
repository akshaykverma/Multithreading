package com.multithreading.library;

import com.multithreading.library.models.Student;

/**
 * Thread representation of a student
 */
public class StudentThread implements Runnable {

	private Student student;
	
	private volatile boolean isReadingDone;
	
	public StudentThread(Student student) {
		this.student = student;
	}

	@Override
	public void run() {
		
		while (!isReadingDone) {
			
			try {
				
				student.readBook();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void setReadingDone(boolean isReadingDone) {
		this.isReadingDone = isReadingDone;
	}
	
}
