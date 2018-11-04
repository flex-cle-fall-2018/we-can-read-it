package org.wecanreadit;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LibrarianController {

	@Resource
	LibrarianRepository librarianRepo;

	@RequestMapping("/librarian")
	public String findOneLibrarian(@RequestParam(value = "id") long id, Model model) throws LibrarianNotFoundException {
		Optional<Librarian> librarian = librarianRepo.findById(id);
		if (librarian.isPresent()) {
			model.addAttribute("librarian", librarian.get());
			return "librarian";
		}
		throw new LibrarianNotFoundException();
	}

	@RequestMapping("/show-librarians")
	public String findAllLibrarians(Model model) {
		model.addAttribute("librarians", librarianRepo.findAll());
		return "librarians";

	}

	@RequestMapping
	public String addComment(String firstName, String lastName, String email, String username, String password,
			String library, String favoriteGenre, Model model) {
		Librarian newLibrarian = new Librarian(firstName, lastName, email, username, password, library, favoriteGenre);
		librarianRepo.save(newLibrarian);
		return "redirect:/show-librarians";
	}

}
