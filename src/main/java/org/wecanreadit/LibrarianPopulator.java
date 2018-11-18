package org.wecanreadit;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class LibrarianPopulator implements CommandLineRunner {

	
	

		@Resource
		private LibrarianRepository librarianRepo;


		@Override
		public void run(String... args) throws Exception {
			Librarian mike = new Librarian("Mike", "Myers", "Beachwood", "waynesworld.com", "waynesworld1", "password", "action");
			Librarian angie = new Librarian("Angie", "Smith", "Heights", "smithtown.com", "smittylady5", "password5", "biography");			Reader bob = new Reader("Bob", "Em", "Shane", "Em");
			Librarian ashley = new Librarian("Ashley", "Stanley", "South Euclid-Lyndhurst", "sheslays.com", "ash321", "password91", "science fiction");			Reader vi = new Reader("Vi", "Em", "Shane", "Em");
			
			mike = librarianRepo.save(mike);
			angie = librarianRepo.save(angie);
			ashley = librarianRepo.save(ashley);
		
}
}