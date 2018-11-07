package org.wecanreadit;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Book {
	
	@Id
	@GeneratedValue
	private long id;
	
	private String title;
	private int totalPages;
	private int pagesRead;
	private Calendar dateFinished;
	private String author;
	
	
	@ManyToOne
	private Reader reader;
	
	public Book() {
		
	}
	
	public Book(String title, String author, int totalPages, int pagesRead, int monthFinished, int dayFinished, int yearFinished, Reader reader) {
		this.title = title;
		this.author = author;
		this.totalPages = totalPages;
		this.pagesRead = pagesRead;
		this.reader = reader;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}
	
	public String getAuthor() {
		return author;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getPagesRead() {
		return pagesRead;
	}

	public Calendar getDateFinished() {
		return dateFinished;
	}
	
	public Reader getReader() {
		return reader;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public String getStringDateFinished() {
		return new SimpleDateFormat("MMM dd, YYYY").format(this.dateFinished.getTime());
	}

}
