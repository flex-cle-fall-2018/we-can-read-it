(function(){
var userName = document.getElementById('readerLogin');
var password = document.getElementById('password');
var btn = document.getElementById('submit');
var url = `/verifyLogin?=userName=${userName}&password=${password}`;

console.log('load test');

btn.addEventListener('click', function(){
    console.log('Button Event');
    verifyLogin(url, { })
    .then(res => console.log('success')) // JSON-string from `response.json()` call
    .catch(error => console.error(error));
    console.log('button event');
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

});