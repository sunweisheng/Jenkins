package com.bluersw

import org.jenkinsci.plugins.workflow.cps.CpsScript
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.jenkinsci.plugins.workflow.job.WorkflowRun

class LibHelper {

	private CpsScript script
	private WorkflowRun workflowRun
	private WorkflowJob workflowJob

	LibHelper(CpsScript script){
		this.script = script
		this.workflowRun = this.script.$build()
		this.workflowJob = workflowRun.getParent()
		def projectSetting = "project Setting"
		this.script.setProperty("projectSetting",projectSetting)
		this.script.print(this.script.projectSetting)
		this.script.print("构建完成。")
	}

	void echoInfo(){
		this.script.print(this.script.projectSetting)
	}
}
