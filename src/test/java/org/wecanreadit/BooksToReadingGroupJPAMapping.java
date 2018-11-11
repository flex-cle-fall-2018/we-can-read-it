package org.wecanreadit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

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
public class BooksToReadingGroupJPAMapping {
	
	@Resource
	BookRepository bookRepo;
	
	@Resource
	ReadingGroupRepository readingGroupRepo;
	
	@Resource
	EntityManager entityManager;
	
	@Test
	public void shouldSaveAndLoadBook() {
		Book book = bookRepo.save(new Book("title", "author",null));
		long bookId = book.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		Optional<Book> result = bookRepo.findById(bookId);
		Book resultBook = result.get();
		assertThat(resultBook.getTitle(), is("title"));
	}
	
	@Test
	public void shouldGenerateBookId() {
		Book book = bookRepo.save(new Book("title", "author",null));
		long bookId = book.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		
		assertThat(bookId, is(greaterThan(0L)));
	}
	
	@Test
	public void shouldSaveAndLoadReadingGroup() {
		ReadingGroup readingGroup = readingGroupRepo.save(new ReadingGroup("group name", "topic"));
		long readingGroupId = readingGroup.getId();
	
		entityManager.flush();
		entityManager.clear();
		
		Optional<ReadingGroup> result = readingGroupRepo.findById(readingGroupId);
		ReadingGroup resultReadingGroup = result.get();
		assertThat(resultReadingGroup.getGroupName(), is("group name"));
		}
	
	@Test
	public void shouldEstablishReadingGroupToBooksRelationship() {
		//book is not the owner so we must create these first
		ReadingGroup readingGroup = new ReadingGroup("group name", "topic");
		readingGroup = readingGroupRepo.save(readingGroup);
		long readingGroupId = readingGroup.getId();
		
		Book book = bookRepo.save(new Book("title", "author",readingGroup));
		Book book2 = bookRepo.save(new Book("title2", "author", readingGroup));
		
		entityManager.flush();
		entityManager.clear();
		
		Optional<ReadingGroup> result = readingGroupRepo.findById(readingGroupId);
		ReadingGroup readingGroupResult = result.get();
		
		assertThat(readingGroupResult.getAllBooks(), containsInAnyOrder(book, book2));
	}
	
	@Test
	public void shouldFindBooksPerReadingGroup() {
		//reader is not the owner so we must create first
				ReadingGroup readingGroup = new ReadingGroup("group name", "topic");
				readingGroup = readingGroupRepo.save(readingGroup);
				
				Book book = bookRepo.save(new Book("title", "author", readingGroup));
				Book book2 = bookRepo.save(new Book("title2", "author", readingGroup));
				Book book3 = bookRepo.save(new Book("title3", "author", readingGroup));
				book = bookRepo.save(book);
				book2 = bookRepo.save(book2);
				book3 = bookRepo.save(book3);
			
				entityManager.flush();
				entityManager.clear();
				
				Collection<Book> result = bookRepo.findByReadingGroup(readingGroup);
				
				assertThat(result, containsInAnyOrder(book, book2, book3));
	}
	
	@Test
	public void shouldFindReadingGroupForBook() {
		//topic is not the owner so we must create these first
		ReadingGroup readingGroup = new ReadingGroup("group name", "topic");
		readingGroup = readingGroupRepo.save(readingGroup);
		
	
		Book book = bookRepo.save(new Book("title", "author", readingGroup));
		Book book2 = bookRepo.save(new Book("title2", "author", readingGroup));
		book = bookRepo.save(book);
		book2 = bookRepo.save(book2);
		
	
		entityManager.flush();
		entityManager.clear();
		
		Optional<ReadingGroup> result = readingGroupRepo.findByBooksContains(book);
		ReadingGroup readingGroupResult = result.get();
		
		assertEquals(readingGroupResult, readingGroup);
	}
	
}
