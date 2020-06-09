package com.bluersw

import com.mkobit.jenkins.pipelines.codegen.LocalLibraryRetriever
import org.jenkinsci.plugins.workflow.libs.GlobalLibraries
import org.jenkinsci.plugins.workflow.libs.LibraryConfiguration
import org.jenkinsci.plugins.workflow.libs.LibraryRetriever
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.jenkinsci.plugins.workflow.job.WorkflowRun
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.jvnet.hudson.test.JenkinsRule

class JenkinsGlobalLibraryUsageTest {

	@Rule
	public JenkinsRule rule = new JenkinsRule()

	@Before
	void configureGlobalLibraries() {
		rule.timeout = 30
		final LibraryRetriever retriever = new LocalLibraryRetriever()
		final LibraryConfiguration localLibrary =
				new LibraryConfiguration('testLibrary', retriever)
		localLibrary.implicit = true
		localLibrary.defaultVersion = 'unused'
		localLibrary.allowVersionOverride = false
		GlobalLibraries.get().setLibraries(Collections.singletonList(localLibrary))
	}

	@Test
	void testingMyLibrary() {
		CpsFlowDefinition flow = new CpsFlowDefinition('''
        import com.bluersw.LibHelper

        final libHelper = new LibHelper(this)
        libHelper.sayHelloTo('bluersw')
    '''.stripIndent(), true)
		WorkflowJob workflowJob = rule.createProject(WorkflowJob, 'project')
		workflowJob.definition = flow
		WorkflowRun result = rule.buildAndAssertSuccess(workflowJob)
		rule.assertLogContains('LibHelper says hello to bluersw!', result)
	}

	@Test
	void testingMyGlobalVar() {
		CpsFlowDefinition flow = new CpsFlowDefinition('''
        import myGlobal

        myGlobal()
    '''.stripIndent(), true)
		WorkflowJob workflowJob = rule.createProject(WorkflowJob, 'project')
		workflowJob.definition = flow
		WorkflowRun result = rule.buildAndAssertSuccess(workflowJob)
		rule.assertLogContains('Hello from myGlobal', result)
	}
}