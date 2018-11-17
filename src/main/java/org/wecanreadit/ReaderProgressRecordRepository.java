package org.wecanreadit;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

public interface ReaderProgressRecordRepository extends CrudRepository<ReaderProgressRecord, Long> {

	Collection<ReaderProgressRecord> findByReader(Reader reader);

	Collection<ReaderProgressRecord> findByGroupBook(GroupBook book);
	

}
