package org.wecanreadit;

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
    public Collection<Reader> collection(@RequestParam(value="firstName", defaultValue="World") String name) {
    	Reader reader = new Reader("zackm","12e", "zackName", "Meinke");
    	Reader reader2 = new Reader("zackm","12e", "zackName", "Meinke");

    	ReadingGroup readingGroup = new ReadingGroup("group", "Fantasy", reader, reader2);
    	
    	reader = readerRepo.save(reader);
    	return readingGroup.getAllMembers();
    	

    }
}
	

