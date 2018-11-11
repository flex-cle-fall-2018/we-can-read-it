package org.wecanreadit;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ReaderFinishedBook {
	
	@Id
	@GeneratedValue
	private long id;
	
	private LocalDate dateFinished;
	private String title;
	private String author;
	
	@ManyToOne
	private Reader reader;
	
	public ReaderFinishedBook() {
		
	}
	
	public ReaderFinishedBook(Book book, int dayOfMonthFinished, int monthFinished, int yearFinished, Reader reader) {
		this.title = book.getTitle();
		this.author = book.getAuthor();
		dateFinished = LocalDate.of(yearFinished, monthFinished, dayOfMonthFinished);
		this.reader = reader;
	}
	
	public ReaderFinishedBook(String title, String author, int dayOfMonthFinished, int monthFinished, int yearFinished, Reader reader) {
		this.title = title;
		this.author =author;
		dateFinished = LocalDate.of(yearFinished, monthFinished, dayOfMonthFinished);
		this.reader = reader;
	}
	
	public String getStringDateFinished() {
		return this.dateFinished.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));
			
	}
	
	public LocalDate getDateFinished() {
		return dateFinished;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReaderFinishedBook other = (ReaderFinishedBook) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
	
}
