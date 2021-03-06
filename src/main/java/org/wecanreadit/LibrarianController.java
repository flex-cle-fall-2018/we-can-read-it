package org.wecanreadit;

import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LibrarianController {

	@Resource
	LibrarianRepository librarianRepo;

	@Resource
	GroupRepository groupRepo;

	@Resource
	GroupBookRepository groupBookRepo;

	@Resource
	ReadingGroupRepository readingGroupRepo;

	@Resource
	ReaderRepository readerRepo;

	@Resource
	LibrarianRepository libRepo;

	@PostMapping("/add-librarian")
	public String addLibrarian(String firstName, String lastName, String email, String username, String password,
			String library, String favoriteGenre) {
		Librarian newLibrarian = new Librarian(firstName, lastName, library, email, username, password, favoriteGenre);
		librarianRepo.save(newLibrarian);
		return "redirect:/";
	}

	@RequestMapping("/librarian-sign-up")
	public String librarianSignUp() {
		return "librarianSignUp";
	}

	@RequestMapping("/librarian-login")
	public String adminLogin(HttpServletResponse response) {
		return "LibrarianLogin";
	}

	@RequestMapping("/librarian-logout")
	public String adminLogin(HttpServletRequest request, HttpServletResponse response) {

		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("role")) {
				cookie.setMaxAge(0);
				response.addCookie(cookie);
				break;
			}
		}
		return "redirect:/librarian-login";
	}

	@RequestMapping("/addBook")
	public String addBook(@CookieValue(value = "LibrarianId") long librarianId, long id, String book, String author, String pageCount) {
		int count = 0, pages = Integer.valueOf(pageCount);
		GroupBook book1 = new GroupBook(book, author, groupRepo.findById(id).get());
		Librarian lib = libRepo.findById(librarianId).get();
		book1.setLibrarian(lib);
		while (pages > 100) {
			pages -= 100;
			count += 5;
		}
		book1.setPoints(count);
		groupBookRepo.save(book1);
		return "redirect:/group?id=" + id;
	}

	// Add custom Exceptions
	@ResponseBody
	@PostMapping("/verifyLibrarianLogin")
	public Librarian verifyLogin(@RequestBody LoginRequest login, HttpServletResponse response) throws Exception {

		Librarian librarian = librarianRepo.findByUsername(login.name);

		if (!librarian.getPassword().equals(login.password)) {
			throw new Exception();
		}
		// Makes new cookie, takes in string,string name, id
		Cookie librarianIdCookie = new Cookie("LibrarianId", librarian.getId().toString());
		response.addCookie(librarianIdCookie);
		Cookie isALibrarian = new Cookie("isALibrarian", "true");
		response.addCookie(isALibrarian);
		return librarian;

	}

	public static class LoginRequest {
		public String name;
		public String password;
	}

}
