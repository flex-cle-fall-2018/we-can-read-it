package org.wecanreadit;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {

	Collection<Book> findByReader(Reader reader);

}
