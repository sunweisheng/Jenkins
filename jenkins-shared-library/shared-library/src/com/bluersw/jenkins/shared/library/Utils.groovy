package com.bluersw.jenkins.shared.library

import hudson.model.Job
import hudson.model.ParameterDefinition
import hudson.model.ParametersDefinitionProperty

class Utils implements Serializable{

	def script

	Utils(script){
		this.script = script
	}

	void TestPrint(){
		this.script.println("Test Print OK")
	}

	void TestParam(){
		Job job = this.script.$build().getParent()
		ParametersDefinitionProperty paramsJobProperty = job.getProperty(ParametersDefinitionProperty.class)
		ParameterDefinition ms = paramsJobProperty.getParameterDefinition("MicroServiceParameter")
		this.script.println(ms.name)
	}
}