package org.wecanreadit;


import java.util.concurrent.atomic.AtomicLong;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Resource;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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