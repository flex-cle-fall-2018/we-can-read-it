package org.wecanreadit;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class GroupBook {

	@Id
	@GeneratedValue
	private long id;

	private String title;
	private String author;
	
	@JsonIgnore
	@ManyToOne
	private ReadingGroup readingGroup;
	
	@JsonIgnore
	@OneToMany(mappedBy = "book")
	private Collection<ReaderBook> readerBooks;

	public GroupBook() {

	}

	public GroupBook(String title, String author, ReadingGroup readingGroup) {
		this.title = title;
		this.author = author;
		this.readingGroup = readingGroup;
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

	public ReadingGroup getReadingGroup() {
		return readingGroup;
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
		GroupBook other = (GroupBook) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
