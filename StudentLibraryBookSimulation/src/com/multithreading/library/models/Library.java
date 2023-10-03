package com.multithreading.library.models;

import java.util.ArrayList;
import java.util.List;

import com.multithreading.library.constants.AppConstants;

public class Library {
	
	/**
	 * Students in the library
	 */
	private List<Student> students;
	
	/**
	 * Books in the library
	 */
	private List<Book> books;

	public Library() {
		this.students = new ArrayList<>();
		this.books = new ArrayList<>();
	}

	/**
	 * Add all the books
	 */
	public void populateBooks() {
		for (int i = 0; i < AppConstants.NUMBER_OF_BOOKS; i++) {
			books.add(new Book(i));
		}
	}
	
	/**
	 * Add all students
	 */
	public void populateStudents() {
		for (int i = 0; i < AppConstants.NUMBER_OF_STUDENTS; i++) {
			students.add(new Student(i, books));
		}
	}

	public List<Student> getStudents() {
		return students;
	}
}
