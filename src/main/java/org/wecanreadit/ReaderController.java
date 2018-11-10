package org.wecanreadit;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	DiscussionQuestionRepository ansRepo;

	@RequestMapping("/questionlist")
	public String findQuestions(Model model) {
		model.addAttribute("groups", groupRepo.findAll());
		return "groupquestionlist";
	}

	@RequestMapping("/singlegroupquestions")
	public String getSingleGroupsQuestions(@RequestParam(required = true) long id, Model model) {
		ReadingGroup group = groupRepo.findById(id).get();
		model.addAttribute("groups", group);
		model.addAttribute("questions", group.getQuestions());
		return "singlegroupquestions";
	}

	@RequestMapping("/readers")
	public String findAllReader(Model model) {
		model.addAttribute("readers", readerRepo.findAll());
		model.addAttribute("groups", groupRepo.findAll());
		return ("readers");
	}

	@RequestMapping("/groups")
	public String findAllGroups(Model model) {
		model.addAttribute("groups", groupRepo.findAll());
		return "groups";
	}

	@RequestMapping("/group")
	public String findAGroup(@RequestParam(required = true) long id, Model model) {
		ReadingGroup group = groupRepo.findById(id).get();
		model.addAttribute("groups", group);
		model.addAttribute("readers", group.getAllMembers());
		model.addAttribute("goals", group.getGoals());
		model.addAttribute("questions", group.getQuestions());
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
	public String createGroup(@RequestParam(required = true) String groupName, String topic) {
		groupRepo.save(new ReadingGroup(groupName, topic));

		return "redirect:/groups";
	}

	@GetMapping("/deleteGroup")
	public String deleteGroup(@RequestParam(required = true) String groupName) {
		groupRepo.deleteById(groupRepo.findByGroupName(groupName).getId());
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

}
