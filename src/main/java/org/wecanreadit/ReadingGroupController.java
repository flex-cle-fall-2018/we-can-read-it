package org.wecanreadit;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReadingGroupController {

	@Resource
	ReadingGroupRepository readingGroupRepo;

	@RequestMapping("/new-group-creation")
	public String newGroupPage(Model model) {

		return "new-group-creation";
	}

}
