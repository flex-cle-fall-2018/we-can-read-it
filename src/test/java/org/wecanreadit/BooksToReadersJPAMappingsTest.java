package org.wecanreadit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class BooksToReadersJPAMappingsTest {
	
	@Resource
	BookRepository bookRepo;
	
	@Resource
	ReaderRepository readerRepo;
	
	@Resource
	EntityManager entityManager;
	
	@Test
	public void shouldSaveAndLoadBook() {
		Book book = bookRepo.save(new Book("title", 200, 150, Calendar.getInstance(), null));
		long bookId = book.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		Optional<Book> result = bookRepo.findById(bookId);
		Book resultBook = result.get();
		assertThat(resultBook.getTitle(), is("title"));
	}
	
	@Test
	public void shouldGenerateBookId() {
		Book book = bookRepo.save(new Book("title", 200, 150, Calendar.getInstance(), null));
		long bookId = book.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		
		assertThat(bookId, is(greaterThan(0L)));
	}
	
	@Test
	public void shouldSaveAndLoadReader() {
		Reader reader = readerRepo.save(new Reader("username", "password", "firstName", "lastName"));
		long readerId = reader.getId();
	
		entityManager.flush();
		entityManager.clear();
		
		Optional<Reader> result = readerRepo.findById(readerId);
		Reader resultReader = result.get();
		assertThat(resultReader.getFirstName(), is("firstName"));
		}
	
	@Test
	public void shouldEstablishReaderToBooksRelationship() {
		//book is not the owner so we must create these first
		Reader reader = new Reader("username", "password", "firstName", "lastName");
		reader = readerRepo.save(reader);
		long readerId = reader.getId();
		
		Book book = bookRepo.save(new Book("title", 200, 150, Calendar.getInstance(), reader));
		Book book2 = bookRepo.save(new Book("title2", 350, 75, Calendar.getInstance(), reader));
		
		entityManager.flush();
		entityManager.clear();
		
		Optional<Reader> result = readerRepo.findById(readerId);
		Reader readerResult = result.get();
		
		assertThat(readerResult.getBooks(), containsInAnyOrder(book, book2));
	}
	
	@Test
	public void shouldFindBooksPerReader() {
		//reader is not the owner so we must create first
				Reader reader = new Reader("username", "password", "firstName", "lastName");
				reader = readerRepo.save(reader);
				
				Book book = bookRepo.save(new Book("title", 200, 150, Calendar.getInstance(), reader));
				Book book2 = bookRepo.save(new Book("title2", 350, 75, Calendar.getInstance(), reader));
				Book book3 = bookRepo.save(new Book("title3", 700, 340, Calendar.getInstance(), reader));
				book = bookRepo.save(book);
				book2 = bookRepo.save(book2);
				book3 = bookRepo.save(book3);
			
				entityManager.flush();
				entityManager.clear();
				
				Collection<Book> result = bookRepo.findByReader(reader);
				
				assertThat(result, containsInAnyOrder(book, book2, book3));
	}
	
	@Test
	public void shouldFindReaderForBook() {
		//topic is not the owner so we must create these first
		Reader reader = new Reader("username", "password", "firstName", "lastName");
		reader = readerRepo.save(reader);
		
	
		Book book = bookRepo.save(new Book("title", 200, 150, Calendar.getInstance(), reader));
		Book book2 = bookRepo.save(new Book("title2", 350, 75, Calendar.getInstance(), reader));
		book = bookRepo.save(book);
		book2 = bookRepo.save(book2);
		
	
		entityManager.flush();
		entityManager.clear();
		
		Optional<Reader> result = readerRepo.findByBooksContains(book);
		Reader readerResult = result.get();
		
		assertEquals(readerResult, reader);
	}

}
