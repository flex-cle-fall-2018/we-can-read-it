package org.wecanreadit;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

public class LibrarianTest {

	Librarian librarian;
	ReadingGroup readingGroup;
	GroupBook book;

	@Before
	public void setup() {
		librarian = new Librarian("firstName", "lastName", "library", "email", "username", "password", "favoriteGenre");
		
	}
	

	@Test
	public void shouldBeAbleToCreateLibrarian() {
		Librarian librarian = new Librarian("firstName", "lastName", "library", "email", "username", "password", "favoriteGenre");

	}

	@Test
	public void shouldBeAbleToGetFirstName() {
		String firstName = librarian.getFirstName();
		assertThat(firstName, is("firstName"));
	}

	@Test
	public void shouldBeAbleToGetLastName() {
		String lastName = librarian.getLastName();
		assertThat(lastName, is("lastName"));

	}

	@Test
	public void shouldBeAbleToGetLibrary() {
		String library = librarian.getLibrary();
		assertThat(library, is("library"));

	}

	@Test
	public void shouldBeAbleToGetEmail() {
		String email = librarian.getEmail();
		assertThat(email, is("email"));
	}
	@Test 
	public void shouldBeAbleToGetUsername() {
		String username = librarian.getUsername();
		assertThat(username, is("username"));
	}

	@Test
	public void shouldBeAbleToGetPassword() {
		String password = librarian.getPassword();
		assertThat(password, is("password"));
	}

	@Test
	public void shouldBeAbleToGetFavoriteGenre() {
		String favoriteGenre = librarian.getFavoriteGenre();
		assertThat(favoriteGenre, is("favoriteGenre"));
	}
			
	@Test 
	public void shouldBeAbleToAddBooksToGroup() {
		Librarian readingGroup = new Librarian();
		GroupBook book = new GroupBook();
		readingGroup.addBook(book);
	
	}
}