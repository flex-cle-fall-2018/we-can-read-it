package org.wecanreadit;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

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
	
	ReadingGroup(){
	}
	
	ReadingGroup(String groupName, String topic, Reader...members){
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
	
	public Collection<Reader> getAllMembers(){
		return readingGroup;
	}
	
}
