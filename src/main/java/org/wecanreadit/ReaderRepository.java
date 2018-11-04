package org.wecanreadit;

import org.springframework.data.repository.CrudRepository;

public interface ReaderRepository extends CrudRepository<Reader, Long> {

<<<<<<< HEAD
	Reader findByFirstName(String name);
=======
	Reader findByUsername(String username);
>>>>>>> b0e9b306392891dded4175e4c41c7fb3924ed844
	
	
}