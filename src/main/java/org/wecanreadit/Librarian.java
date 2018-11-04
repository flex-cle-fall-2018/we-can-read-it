package org.wecanreadit;

public class Librarian {

	private String firstName;
	private String lastName;
	private String library;
	private String email;
	private String favoriteGenre;
	private long id;
	
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
