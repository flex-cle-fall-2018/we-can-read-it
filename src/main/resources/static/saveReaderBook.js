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

