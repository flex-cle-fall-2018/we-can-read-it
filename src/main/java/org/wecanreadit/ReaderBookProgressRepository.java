package org.wecanreadit;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

public interface ReaderBookProgressRepository extends CrudRepository<ReaderBookProgress, Long> {

	Collection<ReaderBookProgress> findByReader(Reader reader);

	Collection<ReaderBookProgress> findByBook(GroupBook book);

}
