package org.wecanreadit;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ReaderProgressRecordRestController {
	
	@Resource
	ReaderProgressRecordRepository readerProgressRecordRepo;
	
	@DeleteMapping("/api/deleteReaderProgressRecord")
	public String deleteReaderBookProgress(@RequestParam long readerProgressRecordId) {
		Optional<ReaderProgressRecord> readerProgressRecord = readerProgressRecordRepo.findById(readerProgressRecordId);
		ReaderProgressRecord readerProgressResult = readerProgressRecord.get();
		long readerId = readerProgressResult.getReader().getId();
		readerProgressRecordRepo.delete(readerProgressResult);
		return "redirect:/reader?id=" + readerId;
	}
	
	@PutMapping("/api/updateFinishedDate")
	public ReaderProgressRecord updateReaderProgressDate(@RequestBody UpdatedDateFinished updatedDateFinished) {
		Optional<ReaderProgressRecord> readerProgressRecord = readerProgressRecordRepo.findById(updatedDateFinished.readerProgressRecordId);
		ReaderProgressRecord readerProgressResult = readerProgressRecord.get();
		readerProgressResult.setDateFinished(updatedDateFinished.monthFinished, updatedDateFinished.dayOfMonthFinished, updatedDateFinished.yearFinished);
		return readerProgressRecordRepo.save(readerProgressResult);
	}

	public static class UpdatedDateFinished {
		public int monthFinished;
		public int dayOfMonthFinished;
		public int yearFinished;
		public long readerProgressRecordId;
		
		
		
	}
	

}

