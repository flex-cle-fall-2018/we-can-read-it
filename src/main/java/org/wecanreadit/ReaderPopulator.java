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
		Reader bob = new Reader("Bob", "Em", "Shane", "Em");
		Reader joe = new Reader("Joe", "Mike", "Zeke" ,"Am");
		Reader vi = new Reader("Vi", "Em", "Shane", "Em");
		Reader doug = new Reader("Dough", "Mike", "Zeke" ,"Am");
		shane = readerRepo.save(shane);
		zack = readerRepo.save(zack);
		bob = readerRepo.save(bob);
		joe = readerRepo.save(joe);
		vi = readerRepo.save(vi);
		doug = readerRepo.save(doug);
		
		ReadingGroup test1 = new ReadingGroup("Default Group", "Everything", shane, zack, joe);
		ReadingGroup test2 = new ReadingGroup("Default Group", "Everything", vi, doug, joe);
		ReadingGroup test3 = new ReadingGroup("Default Group", "Everything", zack, bob, vi, doug);
		
		
		
		test1 = groupRepo.save(test1);
		test2 = groupRepo.save(test2);
		test3 = groupRepo.save(test3);
	}

}
