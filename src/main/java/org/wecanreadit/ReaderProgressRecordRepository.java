package org.wecanreadit;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

public interface ReaderProgressRecordRepository extends CrudRepository<ReaderProgressRecord, Long> {

	Collection<ReaderProgressRecord> findByReader(Reader reader);

	Collection<ReaderProgressRecord> findByGroupBook(GroupBook book);

	// Reader
	// Collection<ReaderProgressRecord>
	// connected to Group via GroupBook
	// sorted by GROUP

	/*
	 * default Collection<ReaderProgressRecord> findByReaderSortByGroup(Reader
	 * reader) {
	 * 
	 * Collection<ReaderProgressRecord> progressRecords = findByReader(reader);
	 * 
	 * return progressRecords.stream() .sorted((recordA, recordB) ->
	 * recordA.getGroupBook().getReadingGroup().getGroupName()
	 * .compareTo(recordB.getGroupBook().getReadingGroup().getGroupName()))
	 * .collect(Collectors.toList()); }
	 */
}
