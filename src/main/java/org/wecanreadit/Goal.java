package org.wecanreadit;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Goal {

	@Id
	@GeneratedValue
	private long id;

	private String name;

	@ManyToMany(mappedBy ="goals")
	private Collection<ReadingGroup> groups;

	Goal() {
	}

	Goal(String name) {
		this.name = name;
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
}
