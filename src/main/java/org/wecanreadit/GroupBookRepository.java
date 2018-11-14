package org.wecanreadit;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface GroupBookRepository extends CrudRepository<GroupBook, Long> {

	Collection<GroupBook> findByReadingGroup(ReadingGroup readingGroup);

	Optional<GroupBook> findByTitle(String bookTitle);

	Optional<GroupBook> findByReaderBooksContains(ReaderBook readerBook);

}
