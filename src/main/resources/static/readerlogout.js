(function(){
    btn = document.getElementById('logout');
    console.log('Loaded');
    btn.addEventListener('click', function(){
        console.log('We clicked');
        deleteCookie('readerId');
        deleteCookie('LibrarianId')
        window.location.href = `/`;
    })

    function deleteCookie(cname) {
        var d = new Date(); //Create an date object
        d.setTime(d.getTime() - (1000*60*60*24)); //Set the time to the past. 1000 milliseonds = 1 second
        var expires = "expires=" + d.toGMTString(); //Compose the expirartion date
        window.document.cookie = cname+"="+"; "+expires;//Set the cookie with name and the expiration date
     
    }
})();