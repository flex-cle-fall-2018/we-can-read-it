var btn = document.getElementById('submit');

btn.addEventListener('click', function(){

    var name = document.getElementById('readerLogin').value;
    var password = document.getElementById('password').value;
    var url = `verifyLogin?name=${name}&password=${password}`;
    fetch(url)
    .then(function(response) {
         return response.json();
    })
    .then(function(myJson) {
        console.log(JSON.stringify(myJson));
    });
});

  function verifyLogin(url = ``, data = {}) {
    // Default options are marked with *
      return fetch(url, {
          method: "GET", // *GET, POST, PUT, DELETE, etc.
          mode: "cors", // no-cors, cors, *same-origin
          cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
          credentials: "same-origin", // include, *same-origin, omit
          headers: {
              "Content-Type": "application/json; charset=utf-8",
              // "Content-Type": "application/x-www-form-urlencoded",
          },
          redirect: "follow", // manual, *follow, error
          referrer: "no-referrer", // no-referrer, *client
          //body: JSON.stringify(data), // body data type must match "Content-Type" header
      })
      .then(response => response.json()); // parses response to JSON
  }
  