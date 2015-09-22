$(document).ready(function() {
	
	initDatePicker();
		
	salutationTrigger();

	$("#sex").change(function() {		
		salutationTrigger();
	});
		
	trigger();
});

function initDatePicker() {

    $("#birthday").datepicker({
    	maxDate: new Date()
    }); 
}
	
function salutationTrigger() {
		
	var $selectDropdown = 
		$("#salutations")
			.empty()
			.html(' ');
	    
	// clear contents
	    	
	var sex = $("#sex").val();
	    
	    
	var salutationMale = ["Mr", "Sir", "Senior", "Count"];
	var salutationFemale = ["Miss", "Ms", "Mrs", "Madame", "Majesty", "Seniora"];
	    
	if(sex == "Male") {
		for(var i = 0; i < salutationMale.length; i++) {
			$("#salutations").append($("<option></option>").attr("value",salutationMale[i]).text(salutationMale[i]));
		}
	} 
	else {
		for(var i = 0; i < salutationMale.length; i++) {
			$("#salutations").append($("<option></option>").attr("value",salutationFemale[i]).text(salutationFemale[i]));
		}
	}
	
	$selectDropdown.trigger('contentChanged');
}

function trigger() {
	$("#firstname").on("input", function() {
		if(!(checkNames($("#firstname").val()))) {
			$("#s_firstname").show();
		}
		else {
			$("#s_firstname").hide();
		}
	});
	
	$("#lastname").on("input", function() {
		if(!(checkNames($("#lastname").val()))) {
			$("#s_lastname").show();
		}
		else {
			$("#s_lastname").hide();
		}
	});
	
	$("#birthday").on("change", function() {
		if(!checkAge($("#birthday").val())) {
			$("#s_birthday").show();
		}
		else {
			$("#s_birthday").hide();
		}
	});
	
	$("#username").on("input", function() {
		if(!(checkUsername($("#username").val()))) {
			$("#s_username").show();
		}
		else {
			$("#s_username").hide();
		}
	});
	
	$("#password").on("input", function() {
		if(!(checkPassword($("#password").val()))) {
			$("#s_password").show();
		}
		else {
			$("#s_password").hide();
		}
	});
	
}

function validate() {
	if(!(checkNames($("#firstname").val())) || !(checkNames($("#lastname").val())) || !checkAge($("#birthday").val()) || !(checkUsername($("#username").val()))
			|| !(checkPassword($("#password").val()))) {
		return false;
	} 
	return true;
}

function checkPassword(password) {
	return /^[^ ]*$/.test(password);
}

function checkUsername(username) {
	return /^[a-zA-Z0-9_]*$/.test(username);
}

function checkNames(name) {
	return /^[a-zA-Z0-9 ]*$/.test(name);
}

function checkAge(birthday) {
	
	var birthdate = new Date(birthday); 
    var today = new Date();
    var thisYear = 0;
    if (today.getMonth() < birthdate.getMonth()) {
        thisYear = 1;
    } else if ((today.getMonth() == birthdate.getMonth()) && today.getDate() < birthdate.getDate()) {
        thisYear = 1;
    }
    var age = today.getFullYear() - birthdate.getFullYear() - thisYear;
    
    
    if(age > 19)
    	return true;
    else
    	return false;
}
	