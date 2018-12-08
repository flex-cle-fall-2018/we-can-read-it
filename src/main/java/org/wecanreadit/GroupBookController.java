package org.wecanreadit;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GroupBookController {

	@Resource
	GroupBookRepository groupBookRepo;
	
	@Resource
	ReadingGroupRepository groupRepo;

	@Resource
	ReaderRepository readerRepo;
	
	@RequestMapping("/group/{groupId}/groupBook/{groupBookId}")
	public String findOneBook(@PathVariable("groupId") long groupId, @PathVariable("groupBookId") long groupBookId, Model model) throws GroupBookNotFoundException {
		Optional<GroupBook> result = groupBookRepo.findById(groupBookId);

		GroupBook groupBook = result.get();
		ReadingGroup group = groupRepo.findById(groupId).get();
		if (result.isPresent()) {
			model.addAttribute("groupBook", groupBook);
			model.addAttribute("group", group);
			model.addAttribute("readerProgressRecords", groupBook.getReaderProgressRecords());
			return "groupBook";
		}
		throw new GroupBookNotFoundException();
	}
	
	@RequestMapping("/groupBook")
	public String findOneBook(@RequestParam long id, Model model) throws GroupBookNotFoundException {
		Optional<GroupBook> result = groupBookRepo.findById(id);
		GroupBook groupBook = result.get();
		if (result.isPresent()) {
			model.addAttribute("groupBook", groupBook);	
			model.addAttribute("readerProgressRecords", groupBook.getReaderProgressRecords());
			return "librarianBook";
		}
		throw new GroupBookNotFoundException();
	}
	
	@RequestMapping("/readerViewGroupBook/{groupId}/groupBook/{groupBookId}")
	public String findOneReaderGroupBook(@PathVariable("groupId") long groupId, @PathVariable("groupBookId") long groupBookId, @CookieValue(value = "readerId") long readerId, Model model) throws GroupBookNotFoundException {
		Optional<GroupBook> result = groupBookRepo.findById(groupBookId);
		model.addAttribute("points", readerRepo.findById(readerId).get().getPoints());
		GroupBook groupBook = result.get();
		ReadingGroup group = groupRepo.findById(groupId).get();
		if (result.isPresent()) {
			model.addAttribute("groupBook", groupBook);
			model.addAttribute("group", group);
			model.addAttribute("readerProgressRecords", groupBook.getReaderProgressRecords());
			return "readerViewGroupBook";
		}
		throw new GroupBookNotFoundException();
	}

	@RequestMapping("/changePoints")
	public String changePointValue(long id, int points, long groupId) {
		GroupBook result = groupBookRepo.findById(id).get();
		result.setPoints(points);
		groupBookRepo.save(result);
		return "redirect:/group/" + groupId + "/groupBook/" + id;
	}
	
	@RequestMapping("/changePointsGroupBook")
	public String changePointValue(long id, int points) {
		GroupBook result = groupBookRepo.findById(id).get();
		result.setPoints(points);
		groupBookRepo.save(result);
		return "redirect:/groupBook?id=" + id;
	}

}
