package org.wecanreadit;

import org.springframework.data.repository.CrudRepository;

public interface ReaderRepository extends CrudRepository<Reader, Long> {

	Reader findByFirstName(String name);

	Reader findByUsername(String username);

}