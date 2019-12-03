package com.bluersw.jenkins.plugin.microserviceparameter;

import java.io.IOException;

import javax.annotation.CheckForNull;
import javax.servlet.ServletException;

import hudson.Extension;
import hudson.model.ParameterDefinition;
import hudson.model.ParameterValue;
import hudson.util.FormValidation;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

//微服务构建参数类，构建参数必须继承ParameterDefinition，继承后可以在Add Parameter选项中出现
public class MicroServiceParameterDefinition extends ParameterDefinition {

	private String subdirectory;

	//构造函数
	//@DataBoundConstructor注解告诉Jenkins用config.jelly中的Fields进行实例化
	@DataBoundConstructor
	public MicroServiceParameterDefinition(String name, String description, String subdirectory) {
		super(name, description);
		this.subdirectory = subdirectory;
	}

	public String getSubdirectory(){return this.subdirectory;}
	public void setSubdirectory(String subdirectory){this.subdirectory = subdirectory;}

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

		public FormValidation doCheckSubdirectory(@QueryParameter("subdirectory") String subdirectory)
		throws IOException, ServletException {
			if(subdirectory.length() == 0)
				return FormValidation.error("子目录不能为空。");
			return FormValidation.ok();
		}

		@Override
		public String getDisplayName() {
			return Messages.MicroServiceParameterDefinition_DescriptorImpl_DisplayName();
		}

	}
}
