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
	
	@RequestMapping("/readerViewGroupBook")
	public String findOneReaderGroupBook(@RequestParam(value = "id") long id, Model model) throws GroupBookNotFoundException {
		Optional<GroupBook> result = groupBookRepo.findById(id);
		GroupBook groupBook = result.get();
		if (result.isPresent()) {
			model.addAttribute("groupBook", groupBook);
			model.addAttribute("group", groupBook.getReadingGroup());
			model.addAttribute("readerProgressRecords", groupBook.getReaderProgressRecords());
			return "readerViewGroupBook";
		}
		throw new GroupBookNotFoundException();
	}
	
	@RequestMapping("/changePoints")
	public String changePointValue(long id, int points) {
		GroupBook result = groupBookRepo.findById(id).get();
		result.setPoints(points);
		groupBookRepo.save(result);
		return "redirect:/groupBook?id=" + id;
	}
//	@RequestMapping("/pageCount")
//	public void getPageCount(@RequestParam(value = "id") long id, int pageCount) {
//		int count = 0;
//		while(pageCount > 100) {
//			pageCount -= 100;
//			count ++;
//		}
//	}
}
