package org.wecanreadit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
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
public class ReaderBookToBookJPAMappingTests {
	
	@Resource
	GroupBookRepository bookRepo;
	
	@Resource
	ReaderRepository readerRepo;
	
	@Resource
	ReadingGroupRepository readingGroupRepo;
	
	@Resource
	ReaderBookRepository readerBookRepo;
	
	@Resource
	EntityManager entityManager;

	@Test
	public void shouldEstablishReaderBooksToBookRelationship() {
		// reader is not the owner so we must create these first
		Reader reader = new Reader("username", "password", "firstName", "lastName");
		reader = readerRepo.save(reader);
		

		ReadingGroup readingGroup = new ReadingGroup("group name", "topic");
		readingGroup = readingGroupRepo.save(readingGroup);

		GroupBook book = bookRepo.save(new GroupBook("title", "author", readingGroup));

		ReaderBook readerBook = readerBookRepo.save(new ReaderBook(book, reader, 11, 11, 2018));
		long readerBookId = readerBook.getId();

		entityManager.flush();
		entityManager.clear();

		Optional<ReaderBook> result = readerBookRepo.findById(readerBookId);
		ReaderBook readerBookResult = result.get();

		assertThat(readerBookResult.getBook(), is(book));
	}

	@Test
	public void shouldFindReaderBooksPerBook() {
		// reader is not the owner so we must create first
		Reader reader = new Reader("username", "password", "firstName", "lastName");
		reader = readerRepo.save(reader);

		ReadingGroup readingGroup = new ReadingGroup("group name", "topic");
		readingGroup = readingGroupRepo.save(readingGroup);

		GroupBook book = bookRepo.save(new GroupBook("title", "author", readingGroup));
		GroupBook book2 = bookRepo.save(new GroupBook("title2", "author", readingGroup));
		GroupBook book3 = bookRepo.save(new GroupBook("title3", "author", readingGroup));
		book = bookRepo.save(book);

		ReaderBook readerBook1 = readerBookRepo.save(new ReaderBook(book, reader, 11, 11, 2018));
		ReaderBook readerBook2 = readerBookRepo.save(new ReaderBook(book, reader, 11, 06, 2018));
		ReaderBook readerBook3 = readerBookRepo.save(new ReaderBook(book, reader, 10, 20, 2018));

		entityManager.flush();
		entityManager.clear();

		Collection<ReaderBook> result = readerBookRepo.findByBook(book);

		assertThat(result, containsInAnyOrder(readerBook1, readerBook2, readerBook3));
	}

	@Test
	public void shouldFindBookForReaderBook() {
		Reader reader = new Reader("username", "password", "firstName", "lastName");
		reader = readerRepo.save(reader);

		ReadingGroup readingGroup = new ReadingGroup("group name", "topic");
		readingGroup = readingGroupRepo.save(readingGroup);

		GroupBook book = bookRepo.save(new GroupBook("title", "author", readingGroup));
		GroupBook book2 = bookRepo.save(new GroupBook("title2", "author", readingGroup));
		book = bookRepo.save(book);
		book2 = bookRepo.save(book2);

		ReaderBook readerBook1 = readerBookRepo.save(new ReaderBook(book, reader, 11, 11, 2018));

		entityManager.flush();
		entityManager.clear();

		Optional<GroupBook> result = bookRepo.findByReaderBooksContains(readerBook1);
		GroupBook bookResult = result.get();

		assertEquals(bookResult, book);
	}


}
