
(function () {

	const addEventListeners = function () {
		const addFriendButton = document.querySelector('#addFriendButton');
		addFriendButton.addEventListener('click', function(event){
			addFriend();
			event.preventDefault();
			sleep(5000).then(()=>{
				location.reload();
			})
		});
	};
	const sleep = (milliseconds)=>{
		return new Promise(res=> setTimeout(res, milliseconds))
 
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
	  
	 sendFriendRequest = function(newFriend) {
		    const xhr = new XMLHttpRequest();
		  
		    xhr.onreadystatechange = function() {
		      if (this.status === 200 && this.readyState === 4) {
		    	console.log(this.responseText);
		        const newFriend = JSON.parse(this.responseText);
		        console.log(newFriend);
		        const newFriendH2 = document.createElement('h2');
		        updateFriends(newFriendH2, newFriend);
		      }
		    };
		    
		    xhr.open('PUT', '/addFriend');
		    xhr.setRequestHeader('Content-Type', 'application/json');
		    const body = JSON.stringify(newFriend);
		    xhr.send(body);
		  };
		  
	const updateFriends = function(newFriendH2, newFriend) {
		console.log(newFriendH2);
		newFriendH2.innerText = newFriend.friendUsername;
		const friendsList = document.querySelector('#friendsList');
		console.log(friendsList);
		friendsList.appendChild(newFriendH2);
		

	}

	const addFriend = function () {
		const friendUsername = document.querySelector('#friendUsername').value;
		const readerId = document.querySelector('#readerId').value;
		const friend = {
			friendUsername,
			readerId,
		};
		console.log(friend);
		sendFriendRequest(friend);
		
		//alert("hello ");
	};

	sendFriendRequest = function (newFriend) {
		const xhr = new XMLHttpRequest();

		xhr.onreadystatechange = function () {
			if (this.status === 200 && this.readyState === 4) {
				console.log(this.responseText);
				if (this.responseText) {
					const newFriend = JSON.parse(this.responseText);
					console.log(newFriend);
					// Create a <p> element
//					const newFriendPara = document.createElement('P');


					var message = "";
					if (newFriend.pendingFriend) {
						console.log(newFriend.pendingFriend);
//						updatePendingFriends(newFriendPara, newFriend);
						message = "This friend is pending, friend must add your username to confirm connection";
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
//					updateFriends(newFriendPara, newFriend);
				} else {
					message = "There was no reader with this username";
				}

				console.log(message);
				addMessage(message);

				// window.location.reload();
				// window.onload = addMessage(message);
				}
			};

			// var t = document.createTextNode("This is a paragraph");       // Create a text node
			// para.appendChild(t);                                          // Append the text to <p>
			// document.body.appendChild(para);
			xhr.open('PUT', '/addFriend');
			xhr.setRequestHeader('Content-Type', 'application/json');
			const body = JSON.stringify(newFriend);
			xhr.send(body);
		};

//		const updateFriends = function (newFriendPara, newFriend) {
//			console.log(newFriendPara);
//			newFriendPara.innerText = newFriend.friendUsername;
//
//		}
//
//		const updatePendingFriends = function (newFriendPara, newFriend) {
//			console.log(newFriendPara);
//			newFriendPara.innerText = newFriend.friendUsername;
//
//		}

		const addMessage = function (message) {
			const messagePara = document.createElement('P');
			messagePara.innerText = message;
			console.log(messagePara);
			const messageDiv = document.querySelector('#messageDiv');
			messageDiv.appendChild(messagePara);
		}

		addEventListeners();

	}) ();