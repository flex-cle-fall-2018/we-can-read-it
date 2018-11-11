package org.wecanreadit;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookController {
	
	@Resource
	BookRepository bookRepo;

	
	@RequestMapping("/book")
	public String findOneBook(@RequestParam(value = "id") long id, Model model) throws BookNotFoundException {
		Optional<Book> result = bookRepo.findById(id);
		Book book = result.get();
		if (result.isPresent()) {
			model.addAttribute("book", book);
			model.addAttribute("readingGroup", book.getReadingGroup());
			return "book";
		}
		throw new BookNotFoundException();
	}
}
