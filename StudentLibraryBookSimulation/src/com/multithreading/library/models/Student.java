package com.multithreading.library.models;

import java.util.List;
import java.util.Random;

import com.multithreading.library.constants.AppConstants;

/**
 * Student representation in a library
 */
public class Student {
	
	private static final int MAX_STUDENT_READING_TIME_IN_MILLIS = 1000;

	private int studentId;
	
	/**
	 * Total number of books read by a student
	 */
	private int booksRead;
	
	/**
	 * Available books to in the library
	 */
	private List<Book> books;

	private Random random;
	
	public Student(int studentId, List<Book> books) {
		this.studentId = studentId;
		this.books = books;
		this.random = new Random();
	}

	/**
	 * Reading a random book from the book collection
	 * @throws InterruptedException
	 */
	public void readBook() throws InterruptedException {

		// selecting a random book to read
		Book slectedBook = books.get(random.nextInt(AppConstants.NUMBER_OF_BOOKS)); 
		
		// taking the selected book
		if (slectedBook.takeBook()) {
			
			System.out.println(this + " is reading the book " + slectedBook);
			booksRead++;
			
			// reading simulation
			Thread.sleep(random.nextInt(MAX_STUDENT_READING_TIME_IN_MILLIS));
			slectedBook.returnBook();
		}
	}
	
	public int getBooksRead() {
		return booksRead;
	}

	@Override
	public String toString() {
		return "Student " + studentId;
	}
	
	
	
}
