package org.wecanreadit;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Reader {

	@Id
	@GeneratedValue
	private long id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String bio;
	private String profileUrl;
	private int points = 0;
	

	@ManyToOne
	private Librarian librarian;

	@OneToMany(mappedBy = "reader")
	private Collection<MessageBoardPost> posts;

	@OneToMany(mappedBy = "reader")
	private Collection<DiscussionAnswer> answers;


	@ManyToMany(mappedBy = "readingGroup")
	private Collection<ReadingGroup> groups;

	@JsonIgnore
	@OneToMany(mappedBy = "reader")
	private Collection<ReaderProgressRecord> readerProgressRecords;

	@JsonIgnore
	@ManyToMany
	private Collection<Reader> pendingFriends;

	@ManyToMany(mappedBy = "pendingFriends")
	private Collection<Reader> pendingFriendOf;

	@JsonIgnore
	@ManyToMany
	private Collection<Reader> friends;

	@ManyToMany(mappedBy = "friends")
	private Collection<Reader> friendOf;

	protected Reader() {
	}

	public Reader(String username, String password, String firstName, String lastName) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.pendingFriends = new HashSet<>(Arrays.asList());
		this.friends = new HashSet<>(Arrays.asList());
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}
	
	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
	public void addPoints(int points) {
		this.points += points;
	}

	public Collection<ReaderProgressRecord> getReaderProgressRecords() {
		return readerProgressRecords;
	}

	public Collection<Reader> getPendingFriends() {
		return pendingFriends;
	}

	public Collection<Reader> getPendingFriendOf() {
		return pendingFriendOf;
	}

	public Collection<Reader> getFriends() {
		return friends;
	}

	public void addFriends(Reader friend) {
		friends.add(friend);

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
		Reader other = (Reader) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public void saveAnswer(DiscussionAnswer answer) {
		answers.add(answer);
	}

	public void savePost(MessageBoardPost post) {
		posts.add(post);
	}
	
	@JsonIgnore
	public Collection<ReadingGroup> getGroups() {
		return groups;
	}
	
	public void setLibrarian(Librarian lib) {
		this.librarian = lib;
	}


}
