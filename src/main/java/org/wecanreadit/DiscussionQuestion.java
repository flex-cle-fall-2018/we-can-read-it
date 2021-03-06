package org.wecanreadit;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class DiscussionQuestion {

	@Id
	@GeneratedValue
	private long id;

	private String content;

	@ManyToMany
	private Collection<DiscussionAnswer> answers;

	@ManyToMany(mappedBy = "questions")
	private Collection<ReadingGroup> groups;

	DiscussionQuestion() {
	}

	DiscussionQuestion(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public Collection<DiscussionAnswer> getAnswers() {
		return answers;
	}

	public Collection<ReadingGroup> getGroups() {
		return groups;
	}

	public long getId() {
		return id;
	}

	public void addAnswer(DiscussionAnswer ans) {
		answers.add(ans);
	}

}
