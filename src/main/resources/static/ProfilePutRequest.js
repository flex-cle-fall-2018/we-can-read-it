(function(){

    //getting Elements
    //Need to add Button Elements and TextBoxes to fulfill values
    var submitBtn = document.getElementById('submit');


    //Values of different areas of readers
    var interest = document.getElementById('interestAreaChangeBox').value;
    var firstName = document.getElementById('').value;
    var lastName = document.getElementById('').value;


    //Event Listeners
    //submit for interestupdate
    submitBtn.addEventListener('click', function(){

        putInterestUpdate('/api/updateCurrentInterests', { interest })
            .then(res => {
                //Update page with new interests Needs Done


            });   
    });
    //Update FirstName
    submitBtn.addEventListener('click', function(){

        putFirstName('/api/updateFirstName', { firstName })
            .then(res => {
                //update FirstName Area Needs Done


            });
    });
    //Update LastName
    submitBtn.addEventListener('click', function(){

        putLastName('/api/updateLastName', { lastName })
            .then(res =>{
                //update lastName Area
            })
    });
    

    function putInterestUpdate(url = ``, data = {}) {
        // Default options are marked with *
          return fetch(url, {
              method: "PUT", // *GET, POST, PUT, DELETE, etc.
              mode: "cors", // no-cors, cors, *same-origin
              cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
              credentials: "same-origin", // include, *same-origin, omit
              headers: {
                  "Content-Type": "application/json; charset=utf-8",
                  // "Content-Type": "application/x-www-form-urlencoded",
              },
              redirect: "follow", // manual, *follow, error
              referrer: "no-referrer", // no-referrer, *client
              body: JSON.stringify(data), // body data type must match "Content-Type" header
          })
          .then(response => response.json()); // parses response to JSON
      }

      function putFirstName(url = ``, data = {}) {
        // Default options are marked with *
          return fetch(url, {
              method: "PUT", // *GET, POST, PUT, DELETE, etc.
              mode: "cors", // no-cors, cors, *same-origin
              cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
              credentials: "same-origin", // include, *same-origin, omit
              headers: {
                  "Content-Type": "application/json; charset=utf-8",
                  // "Content-Type": "application/x-www-form-urlencoded",
              },
              redirect: "follow", // manual, *follow, error
              referrer: "no-referrer", // no-referrer, *client
              body: JSON.stringify(data), // body data type must match "Content-Type" header
          })
          .then(response => response.json()); // parses response to JSON
      }

      function putLastName(url = ``, data = {}) {
        // Default options are marked with *
          return fetch(url, {
              method: "PUT", // *GET, POST, PUT, DELETE, etc.
              mode: "cors", // no-cors, cors, *same-origin
              cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
              credentials: "same-origin", // include, *same-origin, omit
              headers: {
                  "Content-Type": "application/json; charset=utf-8",
                  // "Content-Type": "application/x-www-form-urlencoded",
              },
              redirect: "follow", // manual, *follow, error
              referrer: "no-referrer", // no-referrer, *client
              body: JSON.stringify(data), // body data type must match "Content-Type" header
          })
          .then(response => response.json()); // parses response to JSON
      }
})()

