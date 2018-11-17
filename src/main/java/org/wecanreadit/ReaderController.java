package org.wecanreadit;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	GroupBookRepository bookRepo;

	@Resource
	ReaderProgressRecordRepository readerProgressRecordRepo;

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
		model.addAttribute("readerProgressRecords", readerProgressRecordRepo.findByReader(reader));
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
		model.addAttribute("groupBooks", group.getAllGroupBooks());
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

	@RequestMapping("/addReaderProgressRecord")
	public String addReaderProgressRecord(long groupBookId, int monthFinished, int dayOfMonthFinished, int yearFinished,
			long readerId, Model model) {
		Optional<Reader> reader = readerRepo.findById(readerId);
		Reader readerResult = reader.get();
		Optional<GroupBook> groupBook = bookRepo.findById(groupBookId);
		GroupBook groupBookResult = groupBook.get();
		Collection<ReaderProgressRecord> readerProgressRecords = readerResult.getReaderProgressRecords();
		for (ReaderProgressRecord readerProgressRecord : readerProgressRecords) {
			if (readerProgressRecord.getGroupBook().equals(groupBookResult)) {
				return "redirect:/book?id=" + groupBookId;
			}
		}
		ReaderProgressRecord readerProgressRecord = new ReaderProgressRecord(groupBookResult, readerResult, monthFinished, dayOfMonthFinished,
				yearFinished);
		readerProgressRecordRepo.save(readerProgressRecord);
		return "redirect:/groupBook?id=" + groupBookId;
	}
	
	@RequestMapping("/removeReaderProgressRecord")
	public String removeReaderProgressRecord(@RequestParam long id) {
		Optional<ReaderProgressRecord> readerProgressRecord = readerProgressRecordRepo.findById(id);
		ReaderProgressRecord readerProgressResult = readerProgressRecord.get();
		long readerId = readerProgressResult.getReader().getId();
		readerProgressRecordRepo.delete(readerProgressResult);
		return "redirect:/reader?id=" + readerId;
	}

}
