(function() {

var monthFinishedList = document.getElementById("monthFinished");

//Create array of options to be added
var months = ["January","February","March","April","May","June","July","August","September","October","November","December"];

//Create and append select list


//Create and append the options
for (var i = 0; i < months.length; i++) {
    var option = document.createElement("option");
    option.value = i + 1;
    option.text = months[i];
    monthFinishedList.appendChild(option);
}

var dayOfMonthList = document.getElementById("dayOfMonthFinished");

//Create and append the options
for (var i = 1; i < 32; i++) {
    var option = document.createElement("option");
    option.value = i;
    option.text = i;
    dayOfMonthList.appendChild(option);
}

/**
 * Returns the name of the month, with January = 0, February = 1, etc.
 * @param {number} monthNumber 
 */
const monthName = function(monthNumber) {
    if (monthNumber < 0 || monthNumber > 11) {
        throw Error('Month index out of range.');
    }
    return [
        'January',
        'February',
        'March',
        'April',
        'May',
        'June',
        'July',
        'August',
        'September',
        'October',
        'November',
        'December',
    ][monthNumber];
}





const addEventListener = function() {
    const updateDateFinishedButton = document.querySelector('#updateDateFinishedButton');
    updateDateFinishedButton.addEventListener('click', newFinishedDate);
  };
  

  
  const newFinishedDate = function() {
	    const monthFinished = document.querySelector('#monthFinished').value;
	    const dayOfMonthFinished = document.querySelector('#dayOfMonthFinished').value;
	    const yearFinished = document.querySelector('#yearFinished').value;
	    const readerBookId = document.querySelector('#readerBookId').value;
	    const finishedDate = {
	    		monthFinished, 
	    		dayOfMonthFinished, 
	    		yearFinished,
	    		readerBookId,
	    		
	    };
	    console.log(finishedDate);
	    sendItemUpdateRequest(finishedDate);
	    
	 };
	  
	 sendItemUpdateRequest = function(finishedDate) {
		    const xhr = new XMLHttpRequest();
		  
		    xhr.onreadystatechange = function() {
		      if (this.status === 200 && this.readyState === 4) {
		    	console.log(this.responseText);
		        const updatedBook = JSON.parse(this.responseText);
		        console.log(updatedBook);
		        const dateFinishedParagraph = document.querySelector('#dateFinishedParagraph');
		        updateDateFinished(dateFinishedParagraph, updatedBook);
		      }
		    };
		    
		    xhr.open('PUT', '/api/updateFinishedDate');
		    xhr.setRequestHeader('Content-Type', 'application/json');
		    const body = JSON.stringify(finishedDate);
		    xhr.send(body);
		  };
		  
	const updateDateFinished = function(dateFinishedParagraph, updatedBook) {
	/*	const {
    		monthFinished, 
    		dayOfMonthFinished, 
    		yearFinished,
    		readerBookId,
    		
    } = updatedFinishedDate;
		monthName(monthFinished);
		dateFinishedParagraph.innerText = ""
	}
  */
		dateFinishedParagraph.innerText = updatedBook.stringDateFinished;
	}
		
  addEventListener();



})();