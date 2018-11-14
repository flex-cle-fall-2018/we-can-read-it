package org.wecanreadit;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ReaderBookRestController {
	
	@Resource
	ReaderBookRepository readerBookRepo;
	
	@PutMapping("/api/updateFinishedDate")
	public ReaderBook updateReaderBookDate(@RequestBody UpdatedDateFinished updatedDateFinished) {
		Optional<ReaderBook> readerBook = readerBookRepo.findById(updatedDateFinished.readerBookId);
		ReaderBook readerBookResult = readerBook.get();
		readerBookResult.setDateFinished(updatedDateFinished.monthFinished, updatedDateFinished.dayOfMonthFinished, updatedDateFinished.yearFinished);
		return readerBookRepo.save(readerBookResult);
	
		
	}

	public static class UpdatedDateFinished {
		public int monthFinished;
		public int dayOfMonthFinished;
		public int yearFinished;
		public long readerBookId;
		
		
		
	}
}

