package org.wecanreadit;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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


	@OneToMany(mappedBy = "librarian")
	private Collection<Reader> readers;

	@OneToMany(mappedBy = "librarian")
	private Collection<GroupBook> books;

	@OneToMany(mappedBy = "librarian")
	private Collection<ReadingGroup> groups;

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

	public Long getId() {
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

	public Collection<ReadingGroup> getAllGroups() {
		return groups;
	}

	public Collection<Reader> getAllReaders() {
		return readers;
	}

	public Collection<GroupBook> getBooks() {
		return books;
	}

	public String getGroupName() {

		return groupName;
	}

}
