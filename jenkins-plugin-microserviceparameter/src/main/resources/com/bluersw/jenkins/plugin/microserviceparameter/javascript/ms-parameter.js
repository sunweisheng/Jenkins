function initSubdirectory(subdirectory,parentDiv){
		var subdirectoryArray = subdirectory.split(",");
		console.log(subdirectory)

		for(var i=0;i<subdirectoryArray.length;i++){
		alert(subdirectoryArray[i])

        add(parentDiv,subdirectoryArray[i],subdirectoryArray[i])
		}
}

function add(parentDiv,value,labelVel) {
		var checkBox = document.createElement("input");
		checkBox.setAttribute("type", "checkbox");
		checkBox.setAttribute("name", "selectSD");
		checkBox.setAttribute("value", value);
		var label = document.createElement("label");
		label.innerHTML = labelVel;
		console.log(parentDiv)
		parentDiv.appendChild(checkBox);
		parentDiv.appendChild(label);
	}
