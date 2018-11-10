package org.wecanreadit;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class ReadingGroup {

	@Id
	@GeneratedValue
	private long id;

	private String groupName;
	private String topic;

	@ManyToMany
	private Collection<Reader> readingGroup;

	@ManyToMany
	private Collection<Goal> goals;

	@ManyToMany
	private Collection<DiscussionQuestion> questions;

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

	public void addMember(Reader reader) {
		readingGroup.add(reader);
	}

	public void addGoal(Goal goal) {
		goals.add(goal);
	}

	public void removeGoal(Goal goal) {
		goals.remove(goal);
	}

	public Collection<Goal> getGoals() {
		return goals;
	}

	public Collection<DiscussionQuestion> getQuestions() {
		return questions;
	}

	public void addQuestion(DiscussionQuestion question) {
		questions.add(question);
		
	}

}
