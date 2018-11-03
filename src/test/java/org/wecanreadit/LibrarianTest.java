package org.wecanreadit;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class LibrarianTest {

	Librarian librarian;

	@Before
	public void setup() {
		librarian = new Librarian("firstName", "lastName", "library", "email", "favoriteGenre");
	}

	@Test
	public void shouldBeAbleToCreateLibrarian() {
		Librarian librarian = new Librarian("firstName", "lastName", "library", "email", "favoriteGenre");

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
	public void shouldBeAbleToGetFavoriteGenre() {
		String favoriteGenre = librarian.getFavoriteGenre();
		assertThat(favoriteGenre, is ("favoriteGenre"));
	}
}