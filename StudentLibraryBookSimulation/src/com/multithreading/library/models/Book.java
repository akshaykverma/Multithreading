package com.multithreading.library.models;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Book representation in a library
 */
public class Book {

	/**
	 * Unique book id
	 */
	private int bookId;
	
	/**
	 * Lock on book
	 */
	private Lock bookLock;

	public Book(int bookId) {
		this.bookId = bookId;
		this.bookLock = new ReentrantLock();
	}
	
	/**
	 * Acquiring the book to read by putting a lock if not already taken by some other student
	 * @return
	 * @throws InterruptedException
	 */
	public boolean takeBook() throws InterruptedException {
		
		if (bookLock.tryLock(10, TimeUnit.MILLISECONDS)) {
			System.out.println(this + " has been taken");
			return true;
		}
		return false;
	}
	
	/**
	 * Returning the book by releasing the lock so that other student can take it.
	 */
	public void returnBook() {
		bookLock.unlock();
		System.out.println(this + " has been returned");
	}

	@Override
	public String toString() {
		return "Book " + bookId;
	}
	
	
}
