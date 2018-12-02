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
		

	}
}