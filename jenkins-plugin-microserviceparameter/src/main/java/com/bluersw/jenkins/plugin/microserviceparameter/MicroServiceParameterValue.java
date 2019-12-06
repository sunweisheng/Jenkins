package com.bluersw.jenkins.plugin.microserviceparameter;

import hudson.model.StringParameterValue;
import org.kohsuke.stapler.DataBoundConstructor;

//构建时选择参数后创建该对象，该对象包含了构建时选择的参数结果
public class MicroServiceParameterValue extends StringParameterValue {

	private static final long serialVersionUID = 8L;

	@DataBoundConstructor
	public MicroServiceParameterValue(String name, String value) {
		super(name, value);
	}
}
