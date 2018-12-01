(function () {

	var monthFinishedList = document.getElementById("monthFinished");

	// Create array of options to be added
	var months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];


	// Create and append the options
	for (var i = 0; i < months.length; i++) {
		var option = document.createElement("option");
		option.value = i + 1;
		option.text = months[i];
		monthFinishedList.appendChild(option);
	}

	var dayOfMonthList = document.getElementById("dayOfMonthFinished");

	// Create and append the options
	for (var i = 1; i < 32; i++) {
		var option = document.createElement("option");
		option.value = i;
		option.text = i;
		dayOfMonthList.appendChild(option);
	}

	const addEventListeners = function () {
		const addReaderProgressRecordButton = document.querySelector('#addReaderProgressRecordButton');
		addReaderProgressRecordButton.addEventListener('click', addReaderProgressRecord);
	};

	const addReaderProgressRecord = function () {
		var addReaderProgressRecordForm = document.getElementById('addReaderProgressRecordForm');
		var formData = new FormData(addReaderProgressRecordForm);
		var newReaderProgressRecord = {};
		formData.forEach(function (value, key) {
			newReaderProgressRecord[key] = value;
		});
		sendAddReaderProgressRecordRequest(newReaderProgressRecord);
	};

	sendAddReaderProgressRecordRequest = function (newReaderProgressRecord) {
		const xhr = new XMLHttpRequest();

		xhr.onreadystatechange = function () {
			if (this.status === 200 && this.readyState === 4) {
				if (this.responseText) {
					console.log(this.responseText);
					const newReaderProgressRecord = JSON.parse(this.responseText);
					
					updateReaderProgressRecords(newReaderProgressRecord);
				}
			}
		};

		xhr.open('PUT', '/addReaderProgressRecord');
		xhr.setRequestHeader('Content-Type', 'application/json');
		const body = JSON.stringify(newReaderProgressRecord);
		xhr.send(body);
	};

	const updateReaderProgressRecords = function (newReaderProgressRecord) {
		var readerProgressRecordsPara1 = document.createElement('p');
		var readerProgressRecordsPara2 = document.createElement('p');
		
		const readerProgressRecordsContainer = document.createElement('div');
		console.log(readerProgressRecordsContainer);
		var gridContainer = document.querySelector('.gridContainer');
		console.log(gridContainer);
		readerProgressRecordsPara1.innerText = newReaderProgressRecord.reader.username
		readerProgressRecordsPara2.innerText = newReaderProgressRecord.stringDateFinished;
		readerProgressRecordsContainer.appendChild(readerProgressRecordsPara1);
		readerProgressRecordsContainer.appendChild(readerProgressRecordsPara2);
		gridContainer.appendChild(readerProgressRecordsContainer);
		console.log(gridContainer);
	}

	addEventListeners();

})();