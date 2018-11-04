package org.wecanreadit;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReaderController {

	@Resource
	ReaderRepository readerRepo;

	@Resource
	GroupRepository groupRepo;

	@RequestMapping("/readers")
	public String findAllReader(Model model) {
		model.addAttribute("readers", readerRepo.findAll());
		model.addAttribute("groups", groupRepo.findAll());
		return ("readers");
	}

	@RequestMapping("/groups")
	public String findAllGRoups(Model model) {
		model.addAttribute("groups", groupRepo.findAll());
		return ("groups");
	}
	
	@RequestMapping("/group")
	public String findAGroup(@RequestParam(required = true) long id, Model model) {
		ReadingGroup group = groupRepo.findById(id).get();
		model.addAttribute("groups", group);
		model.addAttribute("readers", group.getAllMembers());
		return "group";
	}
	

	@PostMapping("/addGroup")
	public String addGroup(@RequestParam(required = true) String groupName, String topic) {
		groupRepo.save(new ReadingGroup(groupName, topic));

		return "redirect:/group";
	}
	
	@GetMapping("/deleteGroup")
	public String deleteGroup(@RequestParam(required = true) String groupName) {
		groupRepo.deleteById(groupRepo.findByGroupName(groupName).getId());
		return "redirect:/group";
	}
}
