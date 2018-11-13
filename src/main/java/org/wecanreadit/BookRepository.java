package org.wecanreadit;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {

	Collection<Book> findByReadingGroup(ReadingGroup readingGroup);

	Optional<Book> findByTitle(String bookTitle);

	Optional<Book> findByReaderBooksContains(ReaderBook readerBook);

}
