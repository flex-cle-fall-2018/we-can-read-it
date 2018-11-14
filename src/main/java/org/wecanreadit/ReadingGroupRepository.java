package org.wecanreadit;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface ReadingGroupRepository extends CrudRepository<ReadingGroup, Long> {

	Optional<ReadingGroup> findByBooksContains(GroupBook book);

}
