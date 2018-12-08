
(function () {

	const addEventListeners = function () {
		const addFriendButton = document.querySelector('#addFriendButton');
		addFriendButton.addEventListener('click', addFriend);
		const acceptFriendRequestButtons = document.querySelectorAll('.acceptFriendRequestButton');
		if (acceptFriendRequestButtons != null) {
			for(var i = 0; i < acceptFriendRequestButtons.length; i++ ) {
				acceptFriendRequestButtons[i].addEventListener('click', acceptFriend);
				}
		
	}; 
		const declineFriendRequestButtons = document.querySelectorAll('.declineFriendRequestButton');
		if (declineFriendRequestButtons != null) {
		for(var i = 0; i < declineFriendRequestButtons.length; i++ ) {
		declineFriendRequestButtons[i].addEventListener('click', declineFriend);
		}
		};
		
		const modalCloseButton = document.querySelector('#modal button');
		modalCloseButton.addEventListener('click', modalCloseButtonClickHandler);
	}
	
	

	
	const modalCloseButtonClickHandler = function() {
	      document.body.classList.remove('modal-open');
	      location.reload();
	    };
  	const addFriend = function() {
	    const friendUsername = document.querySelector('#friendUsername').value;
	    const readerId = document.querySelector('#readerId').value;
	    const friend = {
	    		friendUsername,
	    		readerId,
	    		
	    };
	    console.log(friend);
	    sendFriendRequest(friend);
	    
	 };
	 
		const acceptFriend = function() {
		    const friendUsername = this.parentNode.parentNode.getAttribute("data-requestor"); 
		    const readerId = document.querySelector('#readerId').value;
		    const friend = {
		    		friendUsername,
		    		readerId,
		    		
		    };
		    console.log(friend);
		    sendFriendRequest(friend);
		    
		 };
		 
		 const declineFriend = function() {
			 const friendUsername = this.parentNode.parentNode.getAttribute("data-requestor"); 
			    const readerId = document.querySelector('#readerId').value;
			    const declineFriend = {
			    		friendUsername,
			    		readerId,
			    		
			    };
			    console.log(declineFriend);
			    declineFriendRequest(declineFriend);
			    
			 };
	  

	sendFriendRequest = function (newFriend) {
		const xhr = new XMLHttpRequest();

		xhr.onreadystatechange = function () {
			if (this.status === 200 && this.readyState === 4) {
				console.log(this.responseText);
				if (this.responseText) {
					const newFriend = JSON.parse(this.responseText);
					console.log(newFriend);
					


					var message = "";
					if (newFriend.pendingFriend) {
						console.log(newFriend.pendingFriend);
						message = "This friend is pending, friend must add your username to confirm the connection";
					} else if (newFriend.alreadyFriends) {
						console.log(newFriend.alreadyFriends);
						message = "You are already friends with this reader";
					} else if (newFriend.alreadyAdded) {
						console.log(newFriend.alreadyAdded);
						message = "This friend is already pending";
					} else if (newFriend.isUser) {
						message = "This is you!";
					}
					else {
						message = "You and " + newFriend.friendUsername + " are now friends.";
					}
				} else {
					message = "There was no reader with that username";
				}

				console.log(message);
				addMessage(message);
				

				
				}
			};

		
			xhr.open('PUT', '/addFriend');
			xhr.setRequestHeader('Content-Type', 'application/json');
			const body = JSON.stringify(newFriend);
			xhr.send(body);
		};
		
		declineFriendRequest = function (declineFriend) {
			const xhr = new XMLHttpRequest();

			xhr.onreadystatechange = function () {
				if (this.status === 200 && this.readyState === 4) {
					console.log(this.responseText);
		
					const declineFriend = JSON.parse(this.responseText);
						console.log(declineFriend);
						


						var message = "";
						message = "You have declined the friend request from " + declineFriend.friendUsername;
						
					

					console.log(message);
					addMessage(message);

					
				};
			};

			
				xhr.open('PUT', '/declineFriend');
				xhr.setRequestHeader('Content-Type', 'application/json');
				const body = JSON.stringify(declineFriend);
				xhr.send(body);
			};



		const addMessage = function (message) {
			const messagePara = document.createElement('P');
			messagePara.innerText = message;
			console.log(messagePara);
			const messageDiv = document.querySelector('#messageDiv');
			messageDiv.appendChild(messagePara);
			document.body.classList.add('modal-open');
		
		}

		addEventListeners();

	}) ();