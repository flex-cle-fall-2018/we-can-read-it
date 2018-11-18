package org.wecanreadit;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReaderProgressRecordController {
	@Resource
	ReaderProgressRecordRepository readerProgressRecordRepo;

	@Resource
	ReaderRepository readerRepo;

	@RequestMapping("/readerProgressRecord")
	public String findOneReaderProgressRecord(@RequestParam(required = true) long id, Model model)
			throws ReaderProgressRecordNotFoundException {

		Optional<ReaderProgressRecord> readerProgressRecord = readerProgressRecordRepo.findById(id);
		if (readerProgressRecord.isPresent()) {
			model.addAttribute("readerProgressRecord", readerProgressRecord.get());
			model.addAttribute("reader", readerProgressRecord.get().getReader());
			return "readerProgressRecord";
		}
		throw new ReaderProgressRecordNotFoundException();

	}

}
