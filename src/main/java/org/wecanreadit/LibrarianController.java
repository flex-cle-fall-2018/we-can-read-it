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
import org.wecanreadit.ProfileController.LoginRequest;

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

	@RequestMapping("/addBook")
	public String addBook(long id, String book, String author, String pageCount) {
		int count = 0, pages = Integer.valueOf(pageCount);
		GroupBook book1 = new GroupBook(book, author, groupRepo.findById(id).get());
		while (pages > 100) {
			pages -= 100;
			count += 5;
		}
		book1.setPoints(count);
		groupBookRepo.save(book1);
		return "redirect:/group?id=" + id;
	}

	@RequestMapping("librarian/addNewReader")
	public String addNewReader(Reader reader) {
		if (reader.getFirstName() != null && reader.getLastName() != null && reader.getUsername() != null
				&& reader.getPassword() != null) {
			readerRepo.save(reader);
			return "Worked";
		} else {
			return "Nerp";
		}

	}
	
    //Add custom Exceptions
	@ResponseBody
    @PostMapping("/verifyLibrarianLogin")
    public Librarian verifyLogin(
    	@RequestBody LoginRequest login, HttpServletResponse response) throws Exception {
    	String isLibrarian = "true";
    	
    	Librarian librarian = librarianRepo.findByUsername(login.name);
    	
    	if (!librarian.getPassword().equals(login.password)) {
    		throw new Exception();
    	}
    	//Makes new cookie, takes in string,string name, id
    	Cookie librarianIdCookie = new Cookie("LibrarianId", librarian.getId().toString()); 
    	response.addCookie(librarianIdCookie);
    	Cookie isALibrarian = new Cookie("isALibrarian", isLibrarian);
    	response.addCookie(isALibrarian);
    	return librarian;
    	
    }
    public static class LoginRequest {
    	public String name;
    	public String password;
    }	

}
