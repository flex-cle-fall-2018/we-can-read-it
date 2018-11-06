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
    //Find a way to get readerPassword to verify if user/password are matching
    @RequestMapping("/verifyLogin")
    public Reader verifyLogin(String name, String password) {
    	Reader reader = readerRepo.findByUsername(name);
    	String readerPassword = reader.getPassword();
    	//System.out.println(readerPassword);
//    	System.out.println(password);
    	return reader;
    }
	
}

