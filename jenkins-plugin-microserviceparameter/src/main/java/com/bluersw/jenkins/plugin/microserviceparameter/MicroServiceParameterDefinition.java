package com.bluersw.jenkins.plugin.microserviceparameter;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.Logger;
import javax.annotation.CheckForNull;
import javax.servlet.ServletException;

import hudson.Extension;
import hudson.cli.CLICommand;
import hudson.model.ParameterDefinition;
import hudson.model.ParameterValue;
import hudson.util.FormValidation;
import net.sf.json.JSONObject;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

import static org.apache.commons.lang.StringUtils.isBlank;

//微服务构建参数类，构建参数必须继承ParameterDefinition，继承后可以在Add Parameter选项中出现
public class MicroServiceParameterDefinition extends ParameterDefinition implements ChangeMSParameterPlugin{

	private static final Logger LOG = Logger.getLogger(MicroServiceParameterDefinition.class.getName());

	//serialVersionUID串行化后的版本号用于在并行化时进行比较
	static final long serialVersionUID = 5;
	//构建时分组循环构建的分组名称组合，用","分割，多用于微服务项目构建。
	private String profiles;

	private final UUID uuid;

	//构造函数
	//@DataBoundConstructor注解告诉Jenkins用config.jelly中的Fields进行实例化
	@DataBoundConstructor
	public MicroServiceParameterDefinition(String name, String description, String profiles) {
		super(name, description);
		this.profiles = profiles;
		this.uuid = UUID.randomUUID();
	}

	//创建DIV元素的ID
	public String getDivUUID() {
		StringBuilder randomDIVID = new StringBuilder();
		randomDIVID.append(getName()).append("-").append(uuid);
		return randomDIVID.toString();
	}



	public String getProfiles(){return this.profiles;}

	public void setProfiles(String profiles){
		this.profiles = profiles;
	}

	//构建之前选择参数内容后创建构建过程需要的参数值对象
	@CheckForNull
	@Override
	public ParameterValue createValue(StaplerRequest staplerRequest, JSONObject jsonObject) {

		String name = jsonObject.getString("name");
		String selectValue[] = staplerRequest.getParameterValues("selectProfile");

		return this.createParameterValue(name,selectValue);
	}

	@CheckForNull
	@Override
	public ParameterValue createValue(StaplerRequest staplerRequest) {

		String name[] = staplerRequest.getParameterValues("name");
		String selectValue[] = staplerRequest.getParameterValues("selectProfile");

		if (name == null || name.length == 0 || isBlank(name[0])) {
			return new MicroServiceParameterValue(getName(), "");
		}

		return this.createParameterValue(name[0],selectValue);
	}

	private ParameterValue createParameterValue(String name,String selectValue[]){
		StringBuilder strValue = new StringBuilder();
		for(int i=0; i<selectValue.length; i++){
			strValue.append(selectValue[i]);
			if(i!=selectValue.length-1) {
				strValue.append(",");
			}
		}

		return new MicroServiceParameterValue(name,strValue.toString());
	}

	@Override
	public ParameterValue createValue(CLICommand command, String value) throws IOException, InterruptedException {
		return new MicroServiceParameterValue(getName(), value);
	}

	@Override
	public void Change(String profiles) {
		this.profiles = profiles;
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
