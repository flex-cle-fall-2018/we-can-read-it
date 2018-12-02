package org.wecanreadit;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReaderRestController {

	@Resource
	ReaderRepository readerRepo;

	@PutMapping("/addFriend")
	public NewFriend addFriend(@RequestBody NewFriend newFriend) {
		Reader reader = readerRepo.findById(newFriend.readerId).get();
		Reader friend = readerRepo.findByUsername(newFriend.friendUsername);
		if (friend == null) {
			return null;
		} else if (friend.getUsername().equals(reader.getUsername())) {
			newFriend.isUser = true;
			return newFriend;
		}
		Collection<Reader> readerFriends = reader.getFriends();
		for (Reader readerFriend : readerFriends) {
			if (readerFriend.getUsername() == friend.getUsername()) {
				newFriend.alreadyFriends = true;
				return newFriend;
			}
		}
		Collection<Reader> readerPendingFriends = reader.getPendingFriends();
		for (Reader readerPendingFriend : readerPendingFriends) {
			if (readerPendingFriend.getUsername() == friend.getUsername()) {
				newFriend.alreadyAdded = true;
				return newFriend;
			}
		}
		Collection<Reader> friendPendingFriends = friend.getPendingFriends();
		for (Reader friendPendingFriend : friendPendingFriends) {
			if (friendPendingFriend.getUsername() == reader.getUsername()) {
				reader.getFriends().add(friend);
				friend.getFriends().add(reader);
				friend.getPendingFriends().remove(reader);
				reader.getPendingFriends().remove(friend);
				readerRepo.save(reader);
				readerRepo.save(friend);
				return newFriend;
			}
		}
		reader.getPendingFriends().add(friend);
		readerRepo.save(reader);
		newFriend.pendingFriend = true;
		return newFriend;
	}

	public static class NewFriend {
		public String friendUsername;
		public long readerId;
		public boolean alreadyFriends = false;
		public boolean alreadyAdded = false;
		public boolean pendingFriend = false;
		public boolean isUser = false;
	}

	@PutMapping("/declineFriend")
	public DeclineFriend declineFriend(@RequestBody DeclineFriend declineFriend) {
		Reader reader = readerRepo.findById(declineFriend.readerId).get();
		Reader friend = readerRepo.findByUsername(declineFriend.friendUsername);
		friend.getPendingFriends().remove(reader);
		readerRepo.save(friend);
		return declineFriend;
	}

	public static class DeclineFriend {
		public String friendUsername;
		public long readerId;
	}

}
