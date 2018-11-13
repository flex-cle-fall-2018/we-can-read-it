package org.wecanreadit;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class ReaderBookController {
	@Resource
	ReaderBookRepository readerBookRepo;

	@Resource
	ReaderRepository readerRepo;

	@RequestMapping("/readerBook")
	public String findOneReaderBook(@RequestParam(required = true) long id, Model model)
			throws ReaderBookNotFoundException {

		Optional<ReaderBook> readerBook = readerBookRepo.findById(id);
		if (readerBook.isPresent()) {
			model.addAttribute("readerBook", readerBook.get());
			return "readerBook";
		}
		throw new ReaderBookNotFoundException();

	}

}
