package com.bluersw.jenkins.shared.library

import com.lesfurets.jenkins.unit.BasePipelineTest
import javafx.scene.text.Text
import net.sf.json.JSONObject
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.rules.TemporaryFolder

import static com.lesfurets.jenkins.unit.global.lib.LibraryConfiguration.library
import static com.lesfurets.jenkins.unit.global.lib.LocalSource.localSource

class TestSharedLibrary extends BasePipelineTest {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder()

	String sharedLibs = this.class.getResource('/libs').getFile()

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Override
	@Before
	void setUp() throws Exception {
		scriptRoots += '../unit-tests/src/main/jenkins'
		super.setUp()
		binding.setVariable('scm', [branch: 'master'])

		def library = library().name('shared-library')
							   .defaultVersion("master")
							   .allowOverride(false)
							   .implicit(false)
							   .targetPath(sharedLibs)
							   .retriever(localSource(sharedLibs))
							   .build()
		helper.registerSharedLibrary(library)


	}

	@Test
	void library_annotation() throws Exception {
		boolean exception = false
		helper.registerAllowedMethod("readJSON",[String.class],{json->return JSONObject.fromObject(json)}
		)
		runScript('com/bluersw/jenkins/shared/library/pipelineUsingSharedLib.groovy')
		printCallStack()
	}

	@Test
	void verify_exception() throws Exception {

		helper.registerAllowedMethod("sh", [String.class], {cmd->
			// cmd.contains is helpful to filter sh call which should fail the pipeline
			if (cmd.contains("message")) {
				binding.getVariable('currentBuild').result = 'FAILURE'
			}
		})

		runScript("com/bluersw/jenkins/shared/library/pipelineUsingSharedLib.groovy")
		assertJobStatusFailure()
	}
}
