(function(){
    var btn = document.getElementById('submit');
    btn.addEventListener('click', function(){

        var name = document.getElementById('readerLogin').value;
        var password = document.getElementById('password').value;
        var userCookie;
        verifyLogin(`/verifyLogin`, {name, password})
        .then(function(myJson) {
            if(myJson.password == password){
                userCookie = getCookie("readerId");
                window.location.href = `/reader?id=${userCookie}`;
            }
            // console.log(JSON.stringify(myJson));
            
        });
    });

  function verifyLogin(url = ``, data = {}) {
    // Default options are marked with *
      return fetch(url, {
          method: "POST", // *GET, POST, PUT, DELETE, etc.
          mode: "cors", // no-cors, cors, *same-origin
          cache: "no-cache", // *default, no-cache, reload, force-cache,
								// only-if-cached
          credentials: "same-origin", // include, *same-origin, omit
          headers: {
              "Content-Type": "application/json; charset=utf-8",
              // "Content-Type": "application/x-www-form-urlencoded",
          },
          redirect: "follow", // manual, *follow, error
          referrer: "no-referrer", // no-referrer, *client
          body: JSON.stringify(data), // body data type must match
										// "Content-Type" header
      })
      .then(response => response.json()); // parses response to JSON
  }

  function getCookie(name){
    // creates a constructor to match text with a pattern
    var pattern = RegExp(name + "=.[^;]*");
    // verifys (boolean) if the cookie matches the pattern
    matched = document.cookie.match(pattern);
    // if cookie matches pattern return the cookie value
    if(matched){
        var cookie = matched[0].split('=');
        return cookie[1];
    }
    return false;
}

  
})()  