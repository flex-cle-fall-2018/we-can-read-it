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
	
	/*This class allows a book to be created within a reading group. Note there is currently no shared repository for
	 * librarians to access books (as of 11-17-18), so the librarian account would need to create separate
	 * groupBooks for each group
	 */

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
	private Collection<ReaderBookProgress> readerBooks;

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
