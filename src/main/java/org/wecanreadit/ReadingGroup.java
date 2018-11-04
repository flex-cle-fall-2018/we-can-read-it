package org.wecanreadit;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class ReadingGroup {

	@Id
	@GeneratedValue
	private long id;
	
	private String groupName;
	private String topic;
	
	@OneToMany(mappedBy = "group")
	private Collection<Reader> readingGroup;
	
	ReadingGroup(){
	}
	
	ReadingGroup(String groupName, String topic, Reader...members){
		this.groupName = groupName;
		this.topic = topic;
		for(Reader member : members) {
			this.readingGroup.add(member);
		}
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
