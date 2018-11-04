package org.wecanreadit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Librarian {
	
	@Id
	@GeneratedValue
	private long id;
	private String firstName;
	private String lastName;
	private String library;
	private String email;
	private String favoriteGenre;
	
	public Librarian() {
		
	}
	public Librarian(String firstName, String lastName, String library, String email, String favoriteGenre) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.library = library;
		this.email = email;
		this.favoriteGenre = favoriteGenre;
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

	public String getFavoriteGenre() {
	
		return favoriteGenre;
	}

}
