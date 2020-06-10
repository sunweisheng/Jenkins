package com.bluersw

import com.lesfurets.jenkins.unit.BasePipelineTest
import org.junit.Before
import org.junit.Test

class JPUExampleSpec extends BasePipelineTest{

	@Override
	@Before
	void setUp() throws Exception {
		helper.scriptRoots += 'vars'
		super.setUp()
	}

	@Test
	void "example unit test"() throws Exception {
		def script = loadScript('vars/myGlobal.groovy')
		script.call()
		printCallStack()
		assertJobStatusSuccess()
	}
}
