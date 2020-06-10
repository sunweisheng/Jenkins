package com.bluersw

import com.mkobit.jenkins.pipelines.codegen.LocalLibraryRetriever
import hudson.model.BooleanParameterDefinition
import hudson.model.ChoiceParameterDefinition
import hudson.model.ParametersDefinitionProperty
import hudson.model.StringParameterDefinition
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
        
        import groovy.transform.Field
        
        @Field public def globalVariable

        LibHelper libHelper = new LibHelper(this)
        libHelper.echoInfo()
    '''.stripIndent(), true)
		WorkflowJob workflowJob = rule.createProject(WorkflowJob, 'project')
		workflowJob.definition = flow
		WorkflowRun result = rule.buildAndAssertSuccess(workflowJob)
		rule.assertLogContains('global Variable', result)
		rule.assertLogContains('project Setting', result)
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

	@Test
	void testResources(){
		CpsFlowDefinition flow = new CpsFlowDefinition('''
       final resource = libraryResource('com/bluersw/res.txt')
      echo "Resource Text: $resource"
    '''.stripIndent(), true)
		WorkflowJob workflowJob = rule.createProject(WorkflowJob, 'project')
		workflowJob.definition = flow
		WorkflowRun result = rule.buildAndAssertSuccess(workflowJob)
		rule.assertLogContains('test resources.', result)
	}

	@Test
	void testNonCPSMethod(){
		CpsFlowDefinition flow = new CpsFlowDefinition('''
       import com.bluersw.LibHelper
      
       final libHelper = new LibHelper(this)
       echo "Numbers: ${libHelper.nonCpsDouble([1, 2])}"
       
    '''.stripIndent(), true)
		WorkflowJob workflowJob = rule.createProject(WorkflowJob, 'project')
		workflowJob.definition = flow
		WorkflowRun result = rule.buildAndAssertSuccess(workflowJob)
		rule.assertLogContains('Numbers: [2, 4]', result)
	}

	@Test
	void testParameterized(){
		WorkflowJob workflowJob = rule.createProject(WorkflowJob, 'project')
		final StringParameterDefinition string = new StringParameterDefinition('myString', 'myDefault')
		final BooleanParameterDefinition bool = new BooleanParameterDefinition('myBoolean', false, 'boolean parameter description')
		final ChoiceParameterDefinition choice = new ChoiceParameterDefinition('myChoice', ['choice1', 'choice2'] as String[], 'choice parameter description')
		ParametersDefinitionProperty parameterizedProperty = new ParametersDefinitionProperty(string, bool, choice)
		workflowJob.addProperty(parameterizedProperty)
		workflowJob.definition = new CpsFlowDefinition('''
        echo "String param: ${params.myString}"
        echo "Boolean param: ${params.myBoolean}"
        echo "Choice param: ${params.myChoice}"
    '''.stripIndent(), true)

		WorkflowRun result = rule.buildAndAssertSuccess(workflowJob)
		rule.assertLogContains('String param: myDefault', result)
		rule.assertLogContains('Boolean param: false', result)
		rule.assertLogContains('Choice param: choice1', result)
	}
}