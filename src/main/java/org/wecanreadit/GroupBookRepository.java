package org.wecanreadit;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface GroupBookRepository extends CrudRepository<GroupBook, Long> {

	Collection<GroupBook> findByReadingGroup(ReadingGroup readingGroup);

	GroupBook findByTitle(String title);

	Optional<GroupBook> findByReaderProgressRecordsContains(ReaderProgressRecord readerProgressRecord);

}
