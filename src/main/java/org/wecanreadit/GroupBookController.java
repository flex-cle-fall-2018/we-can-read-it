package org.wecanreadit;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GroupBookController {
	
	@Resource
	GroupBookRepository bookRepo;

	
	@RequestMapping("/book")
	public String findOneBook(@RequestParam(value = "id") long id, Model model) throws GroupBookNotFoundException {
		Optional<GroupBook> result = bookRepo.findById(id);
		GroupBook book = result.get();
		if (result.isPresent()) {
			model.addAttribute("book", book);
			model.addAttribute("group", book.getReadingGroup());
			return "book";
		}
		throw new GroupBookNotFoundException();
	}
}
