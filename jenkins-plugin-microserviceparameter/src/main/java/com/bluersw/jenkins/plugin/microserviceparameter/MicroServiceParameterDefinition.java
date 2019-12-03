package com.bluersw.jenkins.plugin.microserviceparameter;

import javax.annotation.CheckForNull;

import hudson.Extension;
import hudson.model.ParameterDefinition;
import hudson.model.ParameterValue;
import net.sf.json.JSONObject;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

//微服务构建参数类，构建参数必须继承ParameterDefinition，继承后可以在Add Parameter选项中出现
public class MicroServiceParameterDefinition extends ParameterDefinition {

	private String data;

	//构造函数
	@DataBoundConstructor
	public MicroServiceParameterDefinition(String name, String description, String data) {
		super(name, description);
		this.data = data;
	}

	public String getData(){return this.data;}
	public void setData(String data){this.data = data;}

	@CheckForNull
	@Override
	public ParameterValue createValue(StaplerRequest staplerRequest, JSONObject jsonObject) {
		return null;
	}

	@CheckForNull
	@Override
	public ParameterValue createValue(StaplerRequest staplerRequest) {
		return null;
	}

	@Extension
	public static final class DescriptorImpl extends ParameterDescriptor
	{

		@Override
		public String getDisplayName() {
			return Messages.MicroServiceParameterDefinition_DescriptorImpl_DisplayName();
		}

	}
}
