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
public class Librarian {

	@Id
	@GeneratedValue
	private long id;
	private String firstName;
	private String lastName;
	private String library;
	private String email;
	private String username;
	private String password;
	private String favoriteGenre;
	private String groupName;
	private String topic;
	
	@ManyToMany
	private Collection<Reader> readingGroup;
	
	@OneToMany(mappedBy = "readingGroup")
	private Collection<GroupBook> groupBooks;


	public Librarian() {

	}

	public Librarian(String firstName, String lastName, String library, String email, String username, String password,
			String favoriteGenre) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.library = library;
		this.email = email;
		this.username = username;
		this.password = password;
		this.favoriteGenre = favoriteGenre;
	}
	
	public void ReadingGroup(String groupName, String topic, Reader... members) {
		this.groupName = groupName;
		this.topic = topic;
		this.readingGroup = new HashSet<>(Arrays.asList(members));
	}
	
	public long getId() {
		return id;
	}


	public String getFirstName() {

		return firstName;
	}

	public String getLastName() {

		return lastName;
	}

	public String getLibrary() {

		return library;
	}

	public String getEmail() {

		return email;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getFavoriteGenre() {
		return favoriteGenre;
	}
		
	public Collection<Reader> getAllMembers() {
		return readingGroup;
	}

	public String getGroupName() {
		
		return groupName;
	}

public String addBook() {
	return addBook();
}

public void addBook(GroupBook book) {
	
	
}

public static Object findAll() {
	// TODO Auto-generated method stub
	return null;
}

}
