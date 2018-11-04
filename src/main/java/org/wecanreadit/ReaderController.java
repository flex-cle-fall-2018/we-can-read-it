package org.wecanreadit;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.wecancodeit.reviewsite.Category;

@Controller
public class ReaderController {
	
	@Resource
	ReaderRepository readerRepo;
	
	@Resource
	GroupRepository groupRepo;
	
	@RequestMapping("/")
	public String findAllReader(Model model) {
		model.addAttribute("readers", readerRepo.findAll());
		return ("readers");
	}
	
	@RequestMapping("/group")
	public String findAllGRoups(Model model) {
		model.addAttribute("groups", groupRepo.findAll());
		return ("groups");
	}
	
	@PostMapping("/addGroup")
	public String addGroup(@RequestParam(required = true) String groupName, String topic) {
		if (groupRepo.findByName(groupName) == null) {
			groupRepo.save(new ReadingGroup(groupName, topic));
		}
		return "redirect:/group";
	}
}
