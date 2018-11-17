package org.wecanreadit;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

	@Resource
	ReadingGroupRepository groupRepo;

	@Resource
	ReaderRepository readerRepo;

	@RequestMapping("/profileInfo")
	public Iterable<Reader> collection() {
		return readerRepo.findAll();
	}

	@RequestMapping("/updateBio")
	public Reader updateBio(String name, String bio) {
		Reader reader = readerRepo.findByUsername(name);
		reader.setBio(bio);
		readerRepo.save(reader);
		return reader;
	}

}