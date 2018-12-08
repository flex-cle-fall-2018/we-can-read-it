package org.wecanreadit;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Goal {

	@Id
	@GeneratedValue
	private long id;

	private int pointValue;

	private String name;

	@ManyToMany(mappedBy = "goals")
	private Collection<ReadingGroup> groups;

	@JsonIgnore
	@ManyToMany
	private Collection<Reader> readers;

	Goal() {
	}

	Goal(String name, int pointValue) {
		this.name = name;
		this.pointValue = pointValue;
	}

	public String getName() {
		return name;
	}

	public Collection<ReadingGroup> getGroups() {
		return groups;
	}

	public Long getId() {
		return id;
	}

	public void addReader(Reader reader) {
		readers.add(reader);
	}

	@JsonIgnore
	public Collection<Reader> getReaders() {
		return readers;
	}

	public int getPoints() {
		return pointValue;
	}

	public boolean containsReader(Reader reader) {
		for (Reader read : readers) {
			if (read.equals(reader)) {
				return true;
			}
		}
		return false;
	}

}
