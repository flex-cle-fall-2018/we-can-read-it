package org.wecanreadit;

import java.util.Collection;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import javax.annotation.Resource;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

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
	

	@Resource
	AuthService auth;

	@PostMapping("/giveReaderPoints")
	public String giveReaderPoints(@RequestParam(required = true) String userName, int points, long groupId) {
		Reader reader = readerRepo.findByUsername(userName);
		reader.addPoints(points);
		readerRepo.save(reader);
		return "redirect:/group?id=" + groupId;
	}

	@RequestMapping("/questionlist")
	public String findQuestions(@CookieValue(value = "readerId") long readerId, Model model) {

		model.addAttribute("groups", readerRepo.findById(readerId).get().getGroups());
		model.addAttribute("points", readerRepo.findById(readerId).get().getPoints());
		return "groupquestionlist";
	}

	@RequestMapping("/goalComplete/{groupId}/{goalId}")
	public String completeGoal(@CookieValue(value = "readerId") long readerId, @PathVariable("groupId") long groupId,
			@PathVariable("goalId") long goalId) {
		Goal goal = goalRepo.findById(goalId).get();
		Reader reader = readerRepo.findById(readerId).get();
		if (goal.containsReader(reader)) {
			return "redirect:/singlegroupquestions?id=" + groupId;
		}
		goal.addReader(reader);
		reader.addPoints(goal.getPoints());
		goalRepo.save(goal);
		readerRepo.save(reader);
		return "redirect:/singlegroupquestions?id=" + groupId;
	}

	@RequestMapping("/singlegroupquestions")
	public String getSingleGroupsQuestions(@RequestParam(required = true) long id, @CookieValue(value = "readerId") long readerId, Model model) {
		ReadingGroup group = groupRepo.findById(id).get();
		model.addAttribute("points", readerRepo.findById(readerId).get().getPoints());
		
		Optional<Reader> identity = auth.getReaderIdentity();
		if (identity.isPresent()) {
			Reader readerLoggedIn = identity.get();
			Collection<Reader> readersInGroup = group.getAllMembers();
			if (readersInGroup.contains(readerLoggedIn)) {
				model.addAttribute("group", group);
				model.addAttribute("books", group.getBooks());
				model.addAttribute("questions", group.getQuestions());
				model.addAttribute("goals", group.getGoals());
				model.addAttribute("posts", group.getPosts());
				model.addAttribute("reader", readerLoggedIn);
				return "singlegroupquestions";
			}
		}
			return "notAuthorized";
	}

	@PostMapping("/createnewreader")
	public String createNewReader(@CookieValue(value = "LibrarianId") long librarianId, String username,
			String password, String firstName, String lastName) {
		Reader reader = readerRepo.findByUsername(username);
		if (reader == null) {
		Reader newReader = new Reader(username, password, firstName, lastName);
		newReader.setLibrarian(libRepo.findById(librarianId).get());
		readerRepo.save(newReader);
		}
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
	public String findAllReader(@CookieValue(required = false, value = "LibrarianId") Long librarianId, Model model) {
		if (librarianId != null) {
		Librarian lib = libRepo.findById(librarianId).get();
		model.addAttribute("readers", lib.getAllReaders());
		model.addAttribute("groups", lib.getAllGroups());
		model.addAttribute("books", lib.getBooks());
		return "readers";
		} else {
			return "notAuthorized";
		}
	}

	@RequestMapping("/reader")
	public String findAReader(@RequestParam(required = true) long id, Model model) {
		boolean isOwner = false;
		boolean isFriend = false;
		boolean isLibrarian = false;

		Reader profileOwner = readerRepo.findById(id).get();

		
		Optional<Reader> readerIdentity = auth.getReaderIdentity();
		Optional<Librarian> librarianIdentity = auth.getLibrarianIdentity();
	    	
	    if (librarianIdentity.isPresent() || readerIdentity.isPresent()) {
	    	
	    	if (readerIdentity.isPresent()) {
	    		model.addAttribute("points", readerIdentity.get().getPoints());
	    		
	    		Reader readerLoggedIn = readerIdentity.get();
	    		if(readerLoggedIn == profileOwner) {
	    			isOwner = true;
	    		} else {
	    			Collection<Reader> ownerFriends = profileOwner.getFriends();
	    			if (ownerFriends.contains(readerLoggedIn)) {
	    				isFriend = true;
	    			}
	    		}
	    	}
	    	
	    	if (librarianIdentity.isPresent()) {
	    		
	    		Librarian librarian = librarianIdentity.get();
	    		Collection<Reader> librarianReaders = librarian.getAllReaders();
	    		if (librarianReaders.contains(profileOwner)) {
	    			isLibrarian = true;
	    		}
	    	}
	    	
	    	
	    model.addAttribute("isOwner", isOwner);	
	    model.addAttribute("isFriend", isFriend);

		model.addAttribute("isLibrarian", isLibrarian);
		model.addAttribute("reader", profileOwner);
		model.addAttribute("readerProgressRecords", readerProgressRecordRepo.findByReader(profileOwner));
        
		return "reader";
	    }
	    return "notAuthorized";
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
		Optional<Librarian> librarianIdentity = auth.getLibrarianIdentity();
		
		if(librarianIdentity.isPresent()) {
		model.addAttribute("group", group);
		model.addAttribute("readers", group.getAllMembers());
		model.addAttribute("goals", group.getGoals());
		model.addAttribute("questions", group.getQuestions());
		model.addAttribute("groupBooks", group.getAllGroupBooks());
		model.addAttribute("posts", group.getPosts());
		return "group";
		}
		return "notAuthorized";
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
	public String addAGoalToAGroup(@RequestParam(required = true) String name, long id, int pointValue) {
		ReadingGroup group = groupRepo.findById(id).get();
		group.addGoal(goalRepo.save(new Goal(name, pointValue)));
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
		readerProgressResult.getReader().addPoints(-(readerProgressResult.getGroupBook().getPoints()));
		long readerId = readerProgressResult.getReader().getId();
		readerProgressRecordRepo.delete(readerProgressResult);
		return "redirect:/reader?id=" + readerId;
	}

	@RequestMapping("/reader/{readerId}/friends")
	public String readerFriends(@PathVariable long readerId, Model model) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		
		Cookie readerIdCookie = WebUtils.getCookie(request, "readerId");
		model.addAttribute("points", readerRepo.findById(readerId).get().getPoints());

		if (readerIdCookie != null) {
			Long readerLoggedInId = new Long(readerIdCookie.getValue());
			Reader readerLoggedIn = readerRepo.findById(readerLoggedInId).get();
			Reader profileOwner = readerRepo.findById(readerId).get();
			if (readerLoggedIn == profileOwner) {
				model.addAttribute("reader", readerRepo.findById(readerId).get());
				model.addAttribute("friends", readerRepo.findById(readerId).get().getFriends());
				model.addAttribute("pendingFriends", readerRepo.findById(readerId).get().getPendingFriends());
				model.addAttribute("pendingFriendOf", readerRepo.findById(readerId).get().getPendingFriendOf());
				return "readerFriends";
			}
		}

		return "notAuthorized";
	}

}
