package org.wecanreadit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Book {
	
	@Id
	@GeneratedValue
	private long id;
	
	private String title;
	private int totalPages;
	private int pagesRead;
	private long dateFinished;
	
	@ManyToOne
	private Reader reader;
	
	public Book() {
		
	}
	
	public Book(String title, int totalPages, int pagesRead, long dateFinished, Reader reader) {
		this.title = title;
		this.totalPages = totalPages;
		this.pagesRead = pagesRead;
		this.dateFinished = dateFinished;
		this.reader = reader;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getPagesRead() {
		return pagesRead;
	}

	public long getDateFinished() {
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
	
	
	
	
}
