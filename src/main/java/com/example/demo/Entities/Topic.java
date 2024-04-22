package com.example.demo.Entities;

public class Topic {

	String id;
	
	String Author;

	
	
	public Topic(String id, String author) {
		this.id = id;
		this.Author = author;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAuthor() {
		return Author;
	}

	public void setAuthor(String author) {
		Author = author;
	}
	
}
