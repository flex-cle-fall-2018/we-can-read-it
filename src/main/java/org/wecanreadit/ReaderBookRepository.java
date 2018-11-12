package org.wecanreadit;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

public interface ReaderBookRepository extends CrudRepository<ReaderBook, Long> {

	Collection<ReaderBook> findByReader(Reader reader);

	Collection<ReaderBook> findByBook(Book book);

}
