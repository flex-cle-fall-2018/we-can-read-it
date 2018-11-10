package org.wecanreadit;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class DiscussionAnswer {

	@Id
	@GeneratedValue
	private long id;

	private String content;

	@ManyToMany(mappedBy = "answers")
	private Collection<DiscussionQuestion> questions;

	DiscussionAnswer() {
	}

	DiscussionAnswer(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public Collection<DiscussionQuestion> getQuestion() {
		return questions;
	}

}
