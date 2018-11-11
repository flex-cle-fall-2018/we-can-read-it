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
public class BookToReaderJPAMappingsTest {
	
	@Resource
	BookRepository bookRepo;
	
	@Resource
	ReaderRepository readerRepo;
	
	@Resource
	ReadingGroupRepository readingGroupRepo;
	
	@Resource
	EntityManager entityManager;

	/*
	
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
	public void shouldEstablishReadersToBooksRelationship() {
		//reader is not the owner so we must create these first
		Reader reader = new Reader("username", "password", "firstName", "lastName");
		reader = readerRepo.save(reader);
		long readerId = reader.getId();
		
		ReadingGroup readingGroup = new ReadingGroup("group name", "topic");
		readingGroup = readingGroupRepo.save(readingGroup);
		
		Book book = bookRepo.save(new Book("title", "author", 200, 150, readingGroup));
		Book book2 = bookRepo.save(new Book("title2", "author", 350, 75, readingGroup));
		
		book.addReader(reader);
		book2.addReader(reader);
		
		
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
				
				ReadingGroup readingGroup = new ReadingGroup("group name", "topic");
				readingGroup = readingGroupRepo.save(readingGroup);
				
				Book book = bookRepo.save(new Book("title", "author",200, 150, readingGroup));
				Book book2 = bookRepo.save(new Book("title2", "author", 350, 75, readingGroup));
				Book book3 = bookRepo.save(new Book("title3", "author", 700, 340, readingGroup));
				book = bookRepo.save(book);
				book2 = bookRepo.save(book2);
				book3 = bookRepo.save(book3);
				book.addReader(reader);
				book2.addReader(reader);
				book3.addReader(reader);
			
				entityManager.flush();
				entityManager.clear();
				
				Collection<Book> result = bookRepo.findByReadersContains(reader);
				
				assertThat(result, containsInAnyOrder(book, book2, book3));
	}
	
	@Test
	public void shouldFindReaderForBook() {
		//topic is not the owner so we must create these first
		Reader reader = new Reader("username", "password", "firstName", "lastName");
		reader = readerRepo.save(reader);
		
		ReadingGroup readingGroup = new ReadingGroup("group name", "topic");
		readingGroup = readingGroupRepo.save(readingGroup);
	
		Book book = bookRepo.save(new Book("title", "author",200, 150, readingGroup));
		Book book2 = bookRepo.save(new Book("title2", "author", 350, 75, readingGroup));
		book = bookRepo.save(book);
		book2 = bookRepo.save(book2);
		book.addReader(reader);
		book2.addReader(reader);
		
	
		entityManager.flush();
		entityManager.clear();
		
		Optional<Reader> result = readerRepo.findByBooksContains(book);
		Reader readerResult = result.get();
		
		assertEquals(readerResult, reader);
	}*/


}
