package com.bluersw.jenkins.plugin.microserviceparameter;

import java.io.IOException;

import javax.annotation.CheckForNull;
import javax.servlet.ServletException;

import hudson.Extension;
import hudson.model.ParameterDefinition;
import hudson.model.ParameterValue;
import hudson.util.FormValidation;
import net.sf.json.JSONObject;
import org.jenkinsci.Symbol;
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

	public void setSubdirectory(String subdirectory){
		this.subdirectory = subdirectory;
	}

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

	//@Symbol定义的名字可以在脚本模式下使用此名字使用插件对象
	@Symbol("msParameter")
	@Extension
	public static final class DescriptorImpl extends ParameterDescriptor
	{
		public DescriptorImpl() {
			//加载JenkinsRoot目录下的该对象xml文件内容，一般是对象默认值。
			load();
		}

		public FormValidation doCheckName(@QueryParameter("name") String name)
		throws IOException, ServletException {
			if(name.length() == 0)
				return FormValidation.error("名称不能空。");
			return FormValidation.ok();
		}

		//在页面上显示的名字（在插件管理里显示的名字在POM文件里设置项目名称）
		@Override
		public String getDisplayName() {
			return Messages.MicroServiceParameterDefinition_DescriptorImpl_DisplayName();
		}

	}
}
