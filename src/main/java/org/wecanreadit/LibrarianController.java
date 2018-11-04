package org.wecanreadit;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

public class LibrarianController {

	@Resource
	LibrarianRepository librarianRepo;
	
	public String findOneLibrarian(@RequestParam(value = "id") long id, Model model) throws LibrarianNotFoundException {
		Optional<Librarian> librarian = librarianRepo.findById(id);
		if(librarian.isPresent()) {
			model.addAttribute("librarian", librarian.get());
			return "";
		}
		throw new LibrarianNotFoundException();
	}

	public String findAllLibrarians(Model model) {
		model.addAttribute("librarians", librarianRepo.findAll());
		return "";
		
	}

}
