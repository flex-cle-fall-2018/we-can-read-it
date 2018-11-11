package org.wecanreadit;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

public class BookTest {
	
	Book book;

	@Before
	public void setup() {
		book = new Book("title", "author", null);
	}

	

	@Test
	public void shouldBeAbleToGetTitle() {
		String title = book.getTitle();
		assertThat(title, is("title"));
	}


	


}
