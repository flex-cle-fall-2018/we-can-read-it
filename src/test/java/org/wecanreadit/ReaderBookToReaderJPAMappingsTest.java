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
public class ReaderBookToReaderJPAMappingsTest {

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
	public void shouldEstablishReadersToReaderBooksRelationship() {
		// reader is not the owner so we must create these first
		Reader reader = new Reader("username", "password", "firstName", "lastName");
		reader = readerRepo.save(reader);
		long readerId = reader.getId();

		ReadingGroup readingGroup = new ReadingGroup("group name", "topic");
		readingGroup = readingGroupRepo.save(readingGroup);

		GroupBook book = groupBookRepo.save(new GroupBook("title", "author", readingGroup));
		GroupBook book2 = groupBookRepo.save(new GroupBook("title2", "author", readingGroup));

		ReaderProgressRecord readerBook = readerProgressRecordRepo.save(new ReaderProgressRecord(book, reader, 11, 11, 2018));
		ReaderProgressRecord readerBook2 = readerProgressRecordRepo.save(new ReaderProgressRecord(book2, reader, 11, 06, 2018));

		entityManager.flush();
		entityManager.clear();

		Optional<Reader> result = readerRepo.findById(readerId);
		Reader readerResult = result.get();

		assertThat(readerResult.getReaderProgressRecords(), containsInAnyOrder(readerBook, readerBook2));
	}

	@Test
	public void shouldFindReaderBooksPerReader() {
		// reader is not the owner so we must create first
		Reader reader = new Reader("username", "password", "firstName", "lastName");
		reader = readerRepo.save(reader);

		ReadingGroup readingGroup = new ReadingGroup("group name", "topic");
		readingGroup = readingGroupRepo.save(readingGroup);

		GroupBook book = groupBookRepo.save(new GroupBook("title", "author", readingGroup));
		GroupBook book2 = groupBookRepo.save(new GroupBook("title2", "author", readingGroup));
		GroupBook book3 = groupBookRepo.save(new GroupBook("title3", "author", readingGroup));
		book = groupBookRepo.save(book);
		book2 = groupBookRepo.save(book2);
		book3 = groupBookRepo.save(book3);

		ReaderProgressRecord readerBook1 = readerProgressRecordRepo.save(new ReaderProgressRecord(book, reader, 11, 11, 2018));
		ReaderProgressRecord readerBook2 = readerProgressRecordRepo.save(new ReaderProgressRecord(book2, reader, 11, 06, 2018));
		ReaderProgressRecord readerBook3 = readerProgressRecordRepo.save(new ReaderProgressRecord(book3, reader, 10, 20, 2018));

		entityManager.flush();
		entityManager.clear();

		Collection<ReaderProgressRecord> result = readerProgressRecordRepo.findByReader(reader);

		assertThat(result, containsInAnyOrder(readerBook1, readerBook2, readerBook3));
	}

	@Test
	public void shouldFindReaderForReaderBook() {
		// topic is not the owner so we must create these first
		Reader reader = new Reader("username", "password", "firstName", "lastName");
		reader = readerRepo.save(reader);

		ReadingGroup readingGroup = new ReadingGroup("group name", "topic");
		readingGroup = readingGroupRepo.save(readingGroup);

		GroupBook groupBook = groupBookRepo.save(new GroupBook("title", "author", readingGroup));
		GroupBook groupBook2 = groupBookRepo.save(new GroupBook("title2", "author", readingGroup));
		groupBook = groupBookRepo.save(groupBook);
		groupBook2 = groupBookRepo.save(groupBook2);

		ReaderProgressRecord readerProgress1 = readerProgressRecordRepo.save(new ReaderProgressRecord(groupBook, reader, 11, 11, 2018));

		entityManager.flush();
		entityManager.clear();

		Optional<Reader> result = readerRepo.findByReaderProgressRecordsContains(readerProgress1);
		Reader readerResult = result.get();

		assertEquals(readerResult, reader);
	}

}
