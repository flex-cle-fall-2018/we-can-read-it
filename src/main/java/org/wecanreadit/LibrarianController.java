package org.wecanreadit;

import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LibrarianController {

	@Resource
	LibrarianRepository librarianRepo;

	@Resource
	GroupRepository groupRepo;

	@Resource
	ReadingGroupRepository readingGroupRepo;

	@Resource
	ReaderRepository readerRepo;

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

	@RequestMapping("/add-librarian")
	public String addLibrarian(String firstName, String lastName, String email, String username, String password,
			String library, String favoriteGenre, Model model) {
		Librarian newLibrarian = new Librarian(firstName, lastName, email, username, password, library, favoriteGenre);
		librarianRepo.save(newLibrarian);
		return "redirect:/show-librarians";
	}

	@RequestMapping("/login")
	public String adminLoginPage() {
		return "login";
	}

	@RequestMapping("/librarian/login")
	public String adminLogin(HttpServletResponse response) {
		Cookie adminRoleCookie = new Cookie("role", "librarian");
		adminRoleCookie.setHttpOnly(true);
		adminRoleCookie.setMaxAge(300);
		response.addCookie(adminRoleCookie);

		return "redirect:/admin";
	}

	@RequestMapping("/admin/logout")
	public String adminLogin(HttpServletRequest request, HttpServletResponse response) {

		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("role")) {
				cookie.setMaxAge(0);
				response.addCookie(cookie);
				break;
			}
		}

		return "redirect:/admin";

	}

	@RequestMapping("/admin")
	public String adminPanel(@CookieValue(name = "role", defaultValue = "") String role, Model model) {

		System.out.println("ROLE: " + role);

		if (role == null || !role.equals("admin")) {
			return "redirect:/login";
		}

		System.out.println("SUCCESS");

		Iterable<Librarian> categories = librarianRepo.findAll();
		model.addAttribute("categories", categories);

		return "admin";
	}

}
