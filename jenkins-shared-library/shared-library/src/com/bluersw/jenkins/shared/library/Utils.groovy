package com.bluersw.jenkins.shared.library

class Utils implements Serializable{

	def script

	Utils(script){
		this.script = script
	}

	void TestPrint(){
		this.script.println("Test Print OK")
	}
}