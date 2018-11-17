package org.wecanreadit;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProfileWebpageController {
	
	@Resource
	ReaderRepository readerRepo;
	
	@RequestMapping("/profile")
	public String ProfilePage() {	
		return ("profile");
	}
	@RequestMapping("/ReaderLogin")
	public String ReaderLoginPage() {
		return ("ReaderLogin");
	}
}
