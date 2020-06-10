package com.bluersw

import com.cloudbees.groovy.cps.NonCPS

class LibHelper {
	private script
	LibHelper(script) {
		this.script = script
	}

	void sayHelloTo(String name) {
		script.echo("LibHelper says hello to $name!")
	}

	@NonCPS
	List<Integer> nonCpsDouble(List<Integer> integers) {
		integers.collect { it * 2 }
	}
}
