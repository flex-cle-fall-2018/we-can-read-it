package org.wecanreadit;

import org.springframework.data.repository.CrudRepository;

public interface GoalRepository extends CrudRepository<Goal, Long> {

	Goal findByName(String name);


}
