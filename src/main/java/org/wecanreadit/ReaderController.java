package org.wecanreadit;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ReaderController {

	@Resource
	ReaderRepository readerRepo;

	@Resource
	GroupRepository groupRepo;

	@Resource
	BookRepository bookRepo;

	@Resource
	ReaderBookRepository readerBookRepo;

	@RequestMapping("/readers")
	public String findAllReader(Model model) {
		model.addAttribute("readers", readerRepo.findAll());
		model.addAttribute("groups", groupRepo.findAll());
		return ("readers");
	}

	@RequestMapping("/reader")
	public String findAReader(@RequestParam(required = true) long id, Model model) {
		Reader reader = readerRepo.findById(id).get();
		model.addAttribute("reader", reader);
		model.addAttribute("readerBooks", reader.getReaderBooks());
		return "reader";
	}

	@RequestMapping("/groups")
	public String findAllGroups(Model model) {
		model.addAttribute("groups", groupRepo.findAll());
		return ("groups");
	}

	@RequestMapping("/group")
	public String findAGroup(@RequestParam(required = true) long id, Model model) {
		ReadingGroup group = groupRepo.findById(id).get();
		model.addAttribute("groups", group);
		model.addAttribute("readers", group.getAllMembers());
		model.addAttribute("books", group.getAllBooks());
		return "group";
	}

	@PostMapping("/addGroup")
	public String addGroup(@RequestParam(required = true) String groupName, String topic) {
		groupRepo.save(new ReadingGroup(groupName, topic));

		return "redirect:/groups";
	}

	@GetMapping("/deleteGroup")
	public String deleteGroup(@RequestParam(required = true) String groupName) {
		groupRepo.deleteById(groupRepo.findByGroupName(groupName).getId());
		return "redirect:/groups";
	}

	@GetMapping("/deleteReader")
	public String deleteSingleReader(@RequestParam(required = true) String username, long id) {
		ReadingGroup group = groupRepo.findById(id).get();
		group.removeMember(readerRepo.findByUsername(username));
		groupRepo.save(group);
		return "redirect:/group?id=" + id;
	}

	@RequestMapping("/addReaderFinishedBook")
	public String addReaderFinishedBook(long bookId, int monthFinished, int dayOfMonthFinished, int yearFinished,
			long readerId, Model model) {
		Optional<Reader> reader = readerRepo.findById(1L);
		Reader readerResult = reader.get();
		Optional<Book> book = bookRepo.findById(bookId);
		Book bookResult = book.get();
		Collection<ReaderBook> readerBooks = readerResult.getReaderBooks();
		for (ReaderBook readerBook : readerBooks) {
			if (readerBook.getBook().equals(bookResult)) {
				return "redirect:/book?id=" + bookId;
			}
		}
		ReaderBook readerBook = new ReaderBook(bookResult, readerResult, monthFinished, dayOfMonthFinished,
				yearFinished);
		readerBookRepo.save(readerBook);
		return "redirect:/book?id=" + bookId;
	}

}
