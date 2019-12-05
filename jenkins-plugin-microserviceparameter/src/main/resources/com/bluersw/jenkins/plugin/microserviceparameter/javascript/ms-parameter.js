function initProfiles(profileDIV,profiles){
		var profileArray = profiles.split(",");
		console.log(profileArray)

		if(profileArray.length>1){
			var checkBox = document.createElement("input");
            checkBox.setAttribute("type", "checkbox");
            checkBox.setAttribute("name", "com-bluersw-selectAll");
            checkBox.setAttribute("value", "all");
            checkBox.setAttribute("onclick", "selAll(this)");
            var label = document.createElement("label");
            label.innerHTML = "ALL";
            profileDIV.appendChild(checkBox);
            profileDIV.appendChild(label);
		}

		for(var i=0;i<profileArray.length;i++){
        	addProfile(profileDIV,profileArray[i],profileArray[i])
		}
}

	function addProfile(profileDIV,value,labelVel) {
		var checkBox = document.createElement("input");
		checkBox.setAttribute("type", "checkbox");
		checkBox.setAttribute("name", "selectProfile");
		checkBox.setAttribute("value", value);
		checkBox.setAttribute("onclick", "sel(this)");
		var label = document.createElement("label");
		label.innerHTML = labelVel;
		console.log(profileDIV)
		profileDIV.appendChild(checkBox);
		profileDIV.appendChild(label);
	}

	//全选
	function selAll(es) {
		var selects = document.getElementsByName("selectProfile");
		for (var i = 0; i < selects.length; i++) {
			selects[i].checked = es.checked;
		}
	}

	function sel(es) {
		var elements = document.getElementsByName("com-bluersw-selectAll");
		console.log(elements[0])
		if(elements[0].checked && !es.checked){
		elements[0].checked = false;
		}
	}
