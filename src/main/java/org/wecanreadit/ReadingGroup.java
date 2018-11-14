package org.wecanreadit;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import java.util.Arrays;

@Entity
public class ReadingGroup {

	@Id
	@GeneratedValue
	private long id;

	private String groupName;
	private String topic;

	@ManyToMany
	private Collection<Reader> readingGroup;
	
	@OneToMany(mappedBy = "readingGroup")
	private Collection<GroupBook> books;

	ReadingGroup() {
	}

	ReadingGroup(String groupName, String topic, Reader... members) {
		this.groupName = groupName;
		this.topic = topic;
		this.readingGroup = new HashSet<>(Arrays.asList(members));
	}

	public long getId() {
		return id;
	}

	public String getGroupName() {
		return groupName;
	}

	public String getTopic() {
		return topic;
	}

	public Collection<Reader> getAllMembers() {
		return readingGroup;
	}

	public void removeMember(Reader reader) {
		readingGroup.remove(reader);
	}
	
	public Collection<GroupBook> getAllBooks() {
		return books;
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
		ReadingGroup other = (ReadingGroup) obj;
		if (id != other.id)
			return false;
		return true;
	}

	
}
