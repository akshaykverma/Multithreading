package com.multithreading.library;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.multithreading.library.constants.AppConstants;
import com.multithreading.library.models.Library;
import com.multithreading.library.models.Student;

public class LibrarySimulation {

	public static void main(String[] args) throws InterruptedException {
		
		Library library = new Library();
		ExecutorService threadPool = Executors.newFixedThreadPool(AppConstants.NUMBER_OF_STUDENTS);
		
		library.populateBooks();
		library.populateStudents();
		
		List<StudentThread> threads = new ArrayList<>();
		List<Student> students = library.getStudents();
		
		try {
			
			// Running the student threads
			for (int i = 0; i < AppConstants.NUMBER_OF_STUDENTS; i++) {
				
				StudentThread thread = new StudentThread(students.get(i));
				threads.add(thread);
				threadPool.execute(thread);
			}
			
			// Running the application for some time
			Thread.sleep(AppConstants.SIMULATION_RUNNING_TIME);
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
		finally {
			cleanUpTasks(threadPool, threads, students);
		}
	}

	
	private static void cleanUpTasks(ExecutorService threadPool, List<StudentThread> threads, List<Student> students)
			throws InterruptedException {
	
		//stopping each thread to stop the simulation
		for (StudentThread thread : threads) {
			thread.setReadingDone(true);
		}
		
		threadPool.shutdown();
		
		while (!threadPool.isTerminated()) {
			Thread.sleep(1000);
			System.out.println("Waiting for the threads to gracefully terminate...");
		}
		
		// print number of books read by each student
		for (Student student : students) {
			System.out.println(student + " read " + student.getBooksRead());
		}
	}

}
