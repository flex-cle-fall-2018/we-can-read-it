package org.wecanreadit;

import java.util.Calendar;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ReaderPopulator implements CommandLineRunner {

	@Resource
	private ReaderRepository readerRepo;

	@Resource
	private GroupRepository groupRepo;

	@Resource
	private GroupBookRepository bookRepo;
	
	@Resource
	private ReaderProgressRecordRepository readerBookRepo;

	@Override
	public void run(String... args) throws Exception {
		Reader shane = new Reader("Shane", "Em", "Shane", "Em");
		Reader zack = new Reader("Zack", "Mike", "Zack", "Am");
		Reader bob = new Reader("Bob", "Em", "Shane", "Em");
		Reader joe = new Reader("Joe", "Mike", "Joe", "Am");
		Reader vi = new Reader("Vi", "Em", "Shane", "Em");
		Reader doug = new Reader("Doug", "Mike", "Zeke", "Am");
		shane = readerRepo.save(shane);
		zack = readerRepo.save(zack);
		bob = readerRepo.save(bob);
		joe = readerRepo.save(joe);
		vi = readerRepo.save(vi);
		doug = readerRepo.save(doug);

		ReadingGroup test1 = new ReadingGroup("Group 1", "Everything", shane, zack, joe);
		ReadingGroup test2 = new ReadingGroup("Group 2", "Everything", vi, doug, joe);
		ReadingGroup test3 = new ReadingGroup("Group 3", "Everything", zack, bob, vi, doug);

		test1 = groupRepo.save(test1);
		test2 = groupRepo.save(test2);
		test3 = groupRepo.save(test3);

		GroupBook gameOfThrones = bookRepo.save(new GroupBook("A Game of Thrones", "George R.R. Martin", test1));
		GroupBook nameOfTheWind = bookRepo.save(new GroupBook("The Name of the Wind", "Patrick Rothfuss", test1));
		GroupBook harryPotter = bookRepo.save(new GroupBook("Harry Potter and the Sorcerer's Stone", "JK Rowling", test1));
		GroupBook lordOfTheRings = bookRepo.save(new GroupBook("The Fellowship of the Ring", "JRR Tolkein", test1));
		
		GroupBook dune = bookRepo.save(new GroupBook("Dune", "Frank Herbert", test2));
		GroupBook frankenstein = bookRepo.save(new GroupBook("Frankenstein", "Mary Shelley", test2));
		GroupBook leftHandOfDarkness = bookRepo.save(new GroupBook("Left Hand of Darkness", "Ursula Le Guin", test2));
		GroupBook endersGame = bookRepo.save(new GroupBook("Ender's Game", "Orson Scott Card", test2));
		
		ReaderProgressRecord joeProgressRecord1 = readerBookRepo.save(new ReaderProgressRecord(gameOfThrones, joe, 2, 2, 2018));
		ReaderProgressRecord joeProgressRecord2 = readerBookRepo.save(new ReaderProgressRecord(dune, joe, 5, 25, 2018));
		ReaderProgressRecord joeProgressRecord3 = readerBookRepo.save(new ReaderProgressRecord(nameOfTheWind, joe, 10, 18, 2018));
		ReaderProgressRecord viProgressRecord1 = readerBookRepo.save(new ReaderProgressRecord(endersGame, vi, 9, 23, 2018));
		ReaderProgressRecord dougProgressRecord1 = readerBookRepo.save(new ReaderProgressRecord(endersGame, doug, 8, 14, 2018));
	
	
	}

}
