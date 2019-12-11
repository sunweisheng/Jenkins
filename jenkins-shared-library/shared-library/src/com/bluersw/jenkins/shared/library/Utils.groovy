package com.bluersw.jenkins.shared.library

import hudson.model.Job
import hudson.model.ParameterDefinition
import hudson.model.ParameterValue
import hudson.model.ParametersDefinitionProperty
import hudson.model.StringParameterValue
import net.sf.json.JSONObject
import netscape.javascript.JSObject

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
		if (ms != null) {
			this.script.println(ms.name)
			ParameterValue value = new StringParameterValue("newInstance","aaa,bbb,ccc")
			ms.copyWithDefaultValue(value)
		}
	}
}