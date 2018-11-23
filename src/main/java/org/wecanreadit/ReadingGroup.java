package org.wecanreadit;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

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
	private Collection<GroupBook> groupBooks;

	@ManyToMany
	private Collection<Goal> goals;

	@ManyToMany
	private Collection<DiscussionQuestion> questions;

	@ManyToMany
	private Collection<MessageBoardPost> posts;

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

	public Collection<GroupBook> getAllGroupBooks() {
		return groupBooks;
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

	public void removeQuestion(DiscussionQuestion quest) {
		questions.remove(quest);
	}

	public Collection<MessageBoardPost> getPosts() {
		return posts;
	}

	public void addPost(MessageBoardPost newPost) {
		posts.add(newPost);
		
	}
	
	public Collection<GroupBook> getBooks(){
		return groupBooks;
	}

}
