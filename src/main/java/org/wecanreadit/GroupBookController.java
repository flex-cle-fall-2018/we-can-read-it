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
	GroupBookRepository groupBookRepo;

	
	@RequestMapping("/groupBook")
	public String findOneBook(@RequestParam(value = "id") long id, Model model) throws GroupBookNotFoundException {
		Optional<GroupBook> result = groupBookRepo.findById(id);
		GroupBook groupBook = result.get();
		if (result.isPresent()) {
			model.addAttribute("groupBook", groupBook);
			model.addAttribute("group", groupBook.getReadingGroup());
			model.addAttribute("readerProgressRecords", groupBook.getReaderProgressRecords());
			return "groupBook";
		}
		throw new GroupBookNotFoundException();
	}
}