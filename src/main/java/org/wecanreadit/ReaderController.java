package org.wecanreadit;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
		model.addAttribute("group", groupRepo.findAll());
		return ("group");
	}
}
