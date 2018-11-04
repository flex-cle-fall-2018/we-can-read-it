package org.wecanreadit;

import org.springframework.data.repository.CrudRepository;

public interface GroupRepository extends CrudRepository<ReadingGroup, Long> {

	ReadingGroup findByGroupName(String groupName);

	void deleteByGroupName(String groupName);
	
	
}