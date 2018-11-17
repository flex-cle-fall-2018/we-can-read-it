(function(){
	var modal = document.getElementById('modal');
	var btn = document.getElementById('addbookbtn');
	var span = document.getElementById('close');
	
	
	btn.onclick = function(){
		console.log("step uno")
		modal.style.display = "none";
	}
	span.onclick = function(){
		modal.style.display = "block";
	}
	window.onclick = function(event){
		if(event.target == modal){
			modal.style.display = "block";
		}
	}
	
})();

