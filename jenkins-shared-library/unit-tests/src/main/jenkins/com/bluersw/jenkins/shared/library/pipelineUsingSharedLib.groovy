@Library('shared-library')

//以下这个注解声明只为了不在IDE里报错没有任何实际作用
@interface Library{String value()}

import com.bluersw.jenkins.shared.library.Utils

node() {
	stage('步骤：'){
		def utils = new Utils()
		utils.TestPrint()
		sayHello()
		sh "message"

		String json = libraryResource 'com/bluersw/jenkins/shared/library/request.json'
		println json
	}
}

