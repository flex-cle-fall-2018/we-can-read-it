package org.wecanreadit;

import org.springframework.data.repository.CrudRepository;

public interface LibrarianRepository extends CrudRepository<Librarian, Long> {
	
	Librarian findByFirstName(String name);

	Librarian findByUsername(String username);

}
