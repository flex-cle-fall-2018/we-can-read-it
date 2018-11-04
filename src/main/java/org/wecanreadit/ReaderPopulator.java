package org.wecanreadit;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ReaderPopulator implements CommandLineRunner {
	
	@Resource
	private ReaderRepository readerRepo;
	
	@Resource
	private GroupRepository groupRepo;
	
	@Override
	public void run(String... args) throws Exception {
		Reader shane = new Reader("Shane", "Em", "Shane", "Em");
		Reader zack = new Reader("Zack", "Mike", "Zeke" ,"Am");
		shane = readerRepo.save(shane);
		zack = readerRepo.save(zack);
		
		ReadingGroup wreader = new ReadingGroup("Default Group", "Everything", shane, zack);
		wreader = groupRepo.save(wreader);
	}

}
