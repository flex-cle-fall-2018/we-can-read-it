package org.wecanreadit;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ReaderProgressRecord {
	/*This class is for the reader to track when they finished a particular groupBook
	 */

	@Id
	@GeneratedValue
	private long id;
	
	private LocalDate dateFinished;
	
	@ManyToOne
	private GroupBook groupBook;

	@ManyToOne
	private Reader reader;

	public ReaderProgressRecord() {

	}

	public ReaderProgressRecord(GroupBook groupBook, Reader reader, int monthFinished, int dayOfMonthFinished, int yearFinished) {
		dateFinished = LocalDate.of(yearFinished, monthFinished, dayOfMonthFinished);
		this.reader = reader;
		this.groupBook = groupBook;
	}
	
	public long getId() {
		return id;
	}

	public String getStringDateFinished() {
		if(dateFinished.getDayOfMonth() < 10) {
			return this.dateFinished.format(DateTimeFormatter.ofPattern("MMMM d, yyyy"));
		} else {
		return this.dateFinished.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));
		}
	}

	public LocalDate getDateFinished() {
		return dateFinished;
	}
	
	public GroupBook getGroupBook() {
		return groupBook;
	}
	
	protected void setDateFinished(int monthFinished, int dayOfMonthFinished, int yearFinished) {
		dateFinished = LocalDate.of(yearFinished, monthFinished, dayOfMonthFinished);
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
		ReaderProgressRecord other = (ReaderProgressRecord) obj;
		if (id != other.id)
			return false;
		return true;
	}

	

	


}
