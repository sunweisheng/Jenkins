package com.bluersw.jenkins.shared.library

import hudson.model.Job
import hudson.model.ParameterDefinition
import hudson.model.ParameterValue
import hudson.model.ParametersDefinitionProperty
import hudson.model.StringParameterValue
import net.sf.json.JSONObject

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

	void PrintInfo(){
		script.println(script.toString())
		script.println(script instanceof Job)
		def o = script.$build()
		script.println(o.toString())
		script.println(o instanceof Job)
		def oo =this.script.$build().getParent()
		script.println(oo.toString())
		script.println(oo instanceof Job)

		Job job = this.script.$build().getParent()

		script.println(job.getName())

	}

	void TestJsonPrint(JSONObject json){
		Iterator iterator = json.keys()
		while (iterator.hasNext()) {
			String key = (String) iterator.next()
			Object obj = json.get(key)
			if (obj instanceof JSONObject && !((JSONObject) obj).isEmpty()) {
				TestJsonPrint((JSONObject) obj)
			}
			else {
				script.println("key:" + key + " value:" + obj)
			}
		}
	}
}