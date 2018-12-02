package org.wecanreadit;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	GoalRepository goalRepo;

	@Resource
	DiscussionQuestionRepository questRepo;

	@Resource
	DiscussionAnswerRepository ansRepo;

	@Resource
	GroupBookRepository bookRepo;

	@Resource
	ReaderProgressRecordRepository readerProgressRecordRepo;

	@Resource
	MessageBoardPostRepository postRepo;

	@Resource
	LibrarianRepository libRepo;

	@RequestMapping("/questionlist")
	public String findQuestions(@CookieValue(value = "readerId") long readerId, Model model) {

		model.addAttribute("groups", readerRepo.findById(readerId).get().getGroups());
		return "groupquestionlist";
	}

	@RequestMapping("/singlegroupquestions")
	public String getSingleGroupsQuestions(@CookieValue(value = "readerId") long readerId,
			@RequestParam(required = true) long id, Model model) {
		ReadingGroup group = groupRepo.findById(id).get();
		Reader reader = readerRepo.findById(readerId).get();
		model.addAttribute("group", group);
		model.addAttribute("books", group.getBooks());
		model.addAttribute("questions", group.getQuestions());
		model.addAttribute("goals", group.getGoals());
		model.addAttribute("posts", group.getPosts());
		model.addAttribute("reader", reader);
		return "singlegroupquestions";
	}

	@PostMapping("/createnewreader")
	public String createNewReader(@CookieValue(value = "LibrarianId") long librarianId, String username,
			String password, String firstName, String lastName) {
		Reader newReader = new Reader(username, password, firstName, lastName);
		newReader.setLibrarian(libRepo.findById(librarianId).get());
		readerRepo.save(newReader);
		return "redirect:/readers";
	}

	@PostMapping("/savePost")
	public String saveNewPost(@CookieValue(value = "readerId") long readerId,
			@RequestParam(required = true) String newPost, long groupid) {
		ReadingGroup group = groupRepo.findById(groupid).get();
		Reader reader = readerRepo.findById(readerId).get();
		MessageBoardPost post = postRepo.save(new MessageBoardPost(newPost));
		group.addPost(post);
		post.setReader(reader);
		reader.savePost(post);
		groupRepo.save(group);
		postRepo.save(post);
		readerRepo.save(reader);
		return "redirect:/singlegroupquestions?id=" + groupid;
	}

	@RequestMapping("/readers")
	public String findAllReader(@CookieValue(value = "LibrarianId") long librarianId, Model model) {
		Librarian lib = libRepo.findById(librarianId).get();
		model.addAttribute("readers", lib.getAllReaders());
		model.addAttribute("groups", lib.getAllGroups());
		model.addAttribute("books", lib.getBooks());
		return "readers";
	}

	@RequestMapping("/reader")
	public String findAReader(@RequestParam(required = true) long id, Model model) {
		Reader reader = readerRepo.findById(id).get();
		model.addAttribute("reader", reader);
		model.addAttribute("readerProgressRecords", readerProgressRecordRepo.findByReader(reader));
		return "reader";
	}

	@RequestMapping("/groups")
	public String findAllGroups(@CookieValue(value = "LibrarianId") long librarianId, Model model) {
		Librarian lib = libRepo.findById(librarianId).get();
		model.addAttribute("groups", lib.getAllGroups());
		return "groups";
	}

	@RequestMapping("/group")
	public String findAGroup(@RequestParam(required = true) long id, Model model) {
		ReadingGroup group = groupRepo.findById(id).get();
		model.addAttribute("group", group);
		model.addAttribute("readers", group.getAllMembers());
		model.addAttribute("goals", group.getGoals());
		model.addAttribute("questions", group.getQuestions());
		model.addAttribute("groupBooks", group.getAllGroupBooks());
		model.addAttribute("posts", group.getPosts());
		return "group";
	}

	@PostMapping("/addQuestion")
	public String addQuestion(@RequestParam(required = true) String content, long id) {
		ReadingGroup group = groupRepo.findById(id).get();
		group.addQuestion(questRepo.save(new DiscussionQuestion(content)));
		groupRepo.save(group);
		return "redirect:/group?id=" + id;
	}

	@RequestMapping(value = "/removeQuestion/{questId}/{groupId}")
	public String removeQuestion(@PathVariable("questId") long questId, @PathVariable("groupId") long groupId) {
		ReadingGroup group = groupRepo.findById(groupId).get();
		DiscussionQuestion quest = questRepo.findById(questId).get();
		group.removeQuestion(quest);
		groupRepo.save(group);
		questRepo.deleteById(questId);

		return "redirect:/group?id=" + groupId;

	}

	@GetMapping("/deleteGoal")
	public String deleteGoal(@RequestParam(required = true) String name, long id) {
		ReadingGroup group = groupRepo.findById(id).get();
		Goal goal = goalRepo.findByName(name);
		group.removeGoal(goal);
		groupRepo.save(group);
		goalRepo.deleteById(goalRepo.findByName(name).getId());
		return "redirect:/group?id=" + id;
	}

	@RequestMapping(value = "/removeGoal/{goalId}/{groupId}")
	public String removeGoal(@PathVariable("goalId") long goalId, @PathVariable("groupId") long groupId) {
		ReadingGroup group = groupRepo.findById(groupId).get();
		Goal goal = goalRepo.findById(goalId).get();
		group.removeGoal(goal);
		groupRepo.save(group);
		goalRepo.deleteById(goalId);
		return "redirect:/group?id=" + groupId;
	}

	@PostMapping("/addGoal")
	public String addAGoalToAGroup(@RequestParam(required = true) String name, long id) {
		ReadingGroup group = groupRepo.findById(id).get();
		group.addGoal(goalRepo.save(new Goal(name)));
		groupRepo.save(group);
		return "redirect:/group?id=" + id;
	}

	@PostMapping("/addGroup")
	public String createGroup(@CookieValue(value = "LibrarianId") long librarianId,
			@RequestParam(required = true) String groupName, String topic) {
		Librarian lib = libRepo.findById(librarianId).get();
		ReadingGroup group = new ReadingGroup(groupName, topic);
		group.setLibrarian(lib);
		groupRepo.save(group);
		return "redirect:/groups";
	}

	@GetMapping("/deleteGroup")
	public String deleteGroup(@RequestParam(required = true) String groupName) {
		ReadingGroup group = groupRepo.findByGroupName(groupName);
		Collection<GroupBook> groupBooks = bookRepo.findByReadingGroupsContains(group);
		for (GroupBook groupBook : groupBooks) {
			groupBook.removeReadingGroup(group);
			bookRepo.save(groupBook);
		}
	
		groupRepo.delete(group);
		return "redirect:/groups";
	}

	@GetMapping("/deleteReader")
	public String deleteSingleReaderFromGroup(@RequestParam(required = true) String username, long id) {

		ReadingGroup group = groupRepo.findById(id).get();
		group.removeMember(readerRepo.findByUsername(username));
		groupRepo.save(group);
		return "redirect:/group?id=" + id;
	}

	@PostMapping("/addReader")
	public String addReaderToGroup(@RequestParam(required = true) String username, long id) {
		ReadingGroup group = groupRepo.findById(id).get();
		Reader reader = readerRepo.findByUsername(username);
		group.addMember(reader);
		groupRepo.save(group);
		return "redirect:/group?id=" + id;
	}

	@PostMapping("/addbooktogroup")
	public String addBookToGroup(String title, long groupId) {
		ReadingGroup group = groupRepo.findById(groupId).get();
		GroupBook book = bookRepo.findByTitle(title);
		group.addBook(book);
		groupRepo.save(group);
		return "redirect:/group?id=" + groupId;
	}

	@PostMapping("/saveanswer")
	public String saveAnswer(@CookieValue(value = "readerId") long readerId,
			@RequestParam(required = true) String answer, long id, long groupid) {
		DiscussionQuestion quest = questRepo.findById(id).get();
		Reader reader = readerRepo.findById(readerId).get();
		DiscussionAnswer newAnswer = ansRepo.save(new DiscussionAnswer(answer));
		quest.addAnswer(newAnswer);
		reader.saveAnswer(newAnswer);
		newAnswer.setReader(reader);
		ansRepo.save(newAnswer);
		questRepo.save(quest);
		readerRepo.save(reader);
		return "redirect:/singlegroupquestions?id=" + groupid;
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
				return "redirect:/groupBook?id=" + groupBookId;
			}
		}
		ReaderProgressRecord readerProgressRecord = new ReaderProgressRecord(groupBookResult, readerResult,
				monthFinished, dayOfMonthFinished, yearFinished);
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

	@RequestMapping("/reader/{readerId}/friends")
	public String readerFriends(@PathVariable long readerId, Model model) {
		model.addAttribute("reader", readerRepo.findById(readerId).get());
		model.addAttribute("friends", readerRepo.findById(readerId).get().getFriends());
		model.addAttribute("pendingFriends", readerRepo.findById(readerId).get().getPendingFriends());
		model.addAttribute("pendingFriendOf", readerRepo.findById(readerId).get().getPendingFriendOf());
		return "readerFriends";
	}

}
