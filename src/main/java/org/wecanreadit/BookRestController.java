package org.wecanreadit;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BookRestController {
	
	@Resource
	BookRepository bookRepo;
	
	@Resource
	ReaderRepository readerRepo;
	
	@PutMapping("/api/reader/{readerId}/books/{bookTitle}/add")
	public Book createBook(@PathVariable long readerId, @PathVariable String bookTitle, String bookAuthor, int totalPages, int pagesRead, int monthFinished, int dayFinished, int yearFinished) throws ReaderNotFoundException {
	
		Book newBook;
		
		//get the reader from repository
				Optional<Reader> readerResult = readerRepo.findById(readerId);
				
				//if reader not present, throw review not found exception
				if (!readerResult.isPresent()) {
					throw new ReaderNotFoundException();
				}
				
				Reader reader = readerResult.get();
				//get reader's books
				Collection<Book> readerBooks = bookRepo.findByReader(reader);
				//check if same name and author?
				newBook = bookRepo.save(new Book(bookTitle, bookAuthor, totalPages, pagesRead, monthFinished, dayFinished, yearFinished, reader));
		
	      //save book to repo
			bookRepo.save(newBook);
		 //return book
		return newBook;
	}
	
	

}
