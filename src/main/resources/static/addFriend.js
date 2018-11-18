(function() {

 	const addEventListeners = function() {
 		const addFriendButton = document.querySelector('#addFriendButton');
    	addFriendButton.addEventListener('click', addFriend);
    
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
		
  addEventListeners();

})();