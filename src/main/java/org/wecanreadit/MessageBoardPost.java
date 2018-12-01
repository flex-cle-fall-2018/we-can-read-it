package org.wecanreadit;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class MessageBoardPost {

	@Id
	@GeneratedValue
	private long id;

	private String content;

	@ManyToOne
	private Reader reader;

	@ManyToMany(mappedBy = "posts")
	private Collection<ReadingGroup> groups;

	MessageBoardPost() {
	}

	MessageBoardPost(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public Collection<ReadingGroup> getGroups() {
		return groups;
	}

	public void setReader(Reader reader) {
		this.reader = reader;
	}

	public Reader getReader() {
		return reader;
	}

}
