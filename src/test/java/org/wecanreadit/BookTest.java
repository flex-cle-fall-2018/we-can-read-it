package org.wecanreadit;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class BookTest {
	
	Book book;

	@Before
	public void setup() {
		book = new Book("title", 150, 200, 11052018L, null);
	}

	

	@Test
	public void shouldBeAbleToGetTitle() {
		String title = book.getTitle();
		assertThat(title, is("title"));
	}

	@Test
	public void shouldBeAbleToGetPagesRead() {
		int pagesRead = book.getPagesRead();
		assertThat(pagesRead, is(200));

	}
	
	@Test
	public void shouldBeAbleToGetTotalPages() {
		int totalPages = book.getTotalPages();
		assertThat(totalPages, is(150));

	}
	
	@Test
	public void shouldBeAbleToGetDateFinished() {
		long dateFinished = book.getDateFinished();
		assertThat(dateFinished, is(11052018L));

	}


}
