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
	GroupBookRepository groupBookRepo;
	
	@Resource
	ReaderRepository readerRepo;
	
	@Resource
	ReadingGroupRepository readingGroupRepo;
	
	@Resource
	ReaderProgressRecordRepository readerProgressRecordRepo;
	
	@Resource
	EntityManager entityManager;

	@Test
	public void shouldEstablishReaderProgressToGroupBookRelationship() {
		// reader is not the owner so we must create these first
		Reader reader = new Reader("username", "password", "firstName", "lastName");
		reader = readerRepo.save(reader);
		

		ReadingGroup readingGroup = new ReadingGroup("group name", "topic");
		readingGroup = readingGroupRepo.save(readingGroup);

		GroupBook groupBook = groupBookRepo.save(new GroupBook("title", "author", readingGroup));

		ReaderProgressRecord readerProgress = readerProgressRecordRepo.save(new ReaderProgressRecord(groupBook, reader, 11, 11, 2018));
		long readerProgressId = readerProgress.getId();

		entityManager.flush();
		entityManager.clear();

		Optional<ReaderProgressRecord> result = readerProgressRecordRepo.findById(readerProgressId);
		ReaderProgressRecord readerBookResult = result.get();

		assertThat(readerBookResult.getGroupBook(), is(groupBook));
	}

	@Test
	public void shouldFindReaderBooksPerBook() {
		// reader is not the owner so we must create first
		Reader reader = new Reader("username", "password", "firstName", "lastName");
		reader = readerRepo.save(reader);

		ReadingGroup readingGroup = new ReadingGroup("group name", "topic");
		readingGroup = readingGroupRepo.save(readingGroup);

		GroupBook book = groupBookRepo.save(new GroupBook("title", "author", readingGroup));
		GroupBook book2 = groupBookRepo.save(new GroupBook("title2", "author", readingGroup));
		GroupBook book3 = groupBookRepo.save(new GroupBook("title3", "author", readingGroup));
		book = groupBookRepo.save(book);

		ReaderProgressRecord readerBook1 = readerProgressRecordRepo.save(new ReaderProgressRecord(book, reader, 11, 11, 2018));
		ReaderProgressRecord readerBook2 = readerProgressRecordRepo.save(new ReaderProgressRecord(book, reader, 11, 06, 2018));
		ReaderProgressRecord readerBook3 = readerProgressRecordRepo.save(new ReaderProgressRecord(book, reader, 10, 20, 2018));

		entityManager.flush();
		entityManager.clear();

		Collection<ReaderProgressRecord> result = readerProgressRecordRepo.findByGroupBook(book);

		assertThat(result, containsInAnyOrder(readerBook1, readerBook2, readerBook3));
	}

	@Test
	public void shouldFindBookForReaderBook() {
		Reader reader = new Reader("username", "password", "firstName", "lastName");
		reader = readerRepo.save(reader);

		ReadingGroup readingGroup = new ReadingGroup("group name", "topic");
		readingGroup = readingGroupRepo.save(readingGroup);

		GroupBook book = groupBookRepo.save(new GroupBook("title", "author", readingGroup));
		GroupBook book2 = groupBookRepo.save(new GroupBook("title2", "author", readingGroup));
		book = groupBookRepo.save(book);
		book2 = groupBookRepo.save(book2);

		ReaderProgressRecord readerBook1 = readerProgressRecordRepo.save(new ReaderProgressRecord(book, reader, 11, 11, 2018));

		entityManager.flush();
		entityManager.clear();

		Optional<GroupBook> result = groupBookRepo.findByReaderProgressRecordsContains(readerBook1);
		GroupBook bookResult = result.get();

		assertEquals(bookResult, book);
	}


}
