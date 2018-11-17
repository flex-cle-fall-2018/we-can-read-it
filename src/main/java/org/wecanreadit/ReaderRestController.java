package org.wecanreadit;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReaderRestController {
	
	@PutMapping("/addFriend")
	public NewFriend addFriend(@RequestBody NewFriend newFriend) {
		return newFriend;
	}
	
	public static class NewFriend {
		public String friendUsername;
		public long readerId;	
	}
	
	
	
}
