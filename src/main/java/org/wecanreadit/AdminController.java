package org.wecanreadit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.experimental.categories.Category;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AdminController {
	
	@Resource
	GroupRepository groupRepo;
	
	@Resource
	ReadingGroupRepository readingGroupRepo;
	
	@Resource
	ReaderRepository readerRepo;
	
	@Resource
	LibrarianRepository librarianRepo;

	@RequestMapping("/login")
	public String adminLoginPage() {
		return "login";
	}
	
	@RequestMapping("/admin/login")
	public String adminLogin(
			HttpServletResponse response
			) {
		Cookie adminRoleCookie = new Cookie("role", "admin");
		adminRoleCookie.setHttpOnly(true);
		adminRoleCookie.setMaxAge(300);
		response.addCookie(adminRoleCookie);
		
		return "redirect:/admin";
	}
	
	@RequestMapping("/admin/logout")
	public String adminLogin(
			HttpServletRequest request,
			HttpServletResponse response
			) {
		
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
	public String adminPanel(
		@CookieValue(name = "role", defaultValue = "") String role,
		Model model
	) {
		
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