package org.wecanreadit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class DiscussionAnswer {

	@Id
	@GeneratedValue
	private long id;

	private String content;

	@ManyToOne
	private DiscussionQuestion question;

	DiscussionAnswer() {
	}

	DiscussionAnswer(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public DiscussionQuestion getQuestion() {
		return question;
	}

}
