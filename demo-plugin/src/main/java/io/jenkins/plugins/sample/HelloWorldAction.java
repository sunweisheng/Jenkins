package io.jenkins.plugins.sample;

import hudson.model.Action;
import hudson.model.Run;
import jenkins.model.RunAction2;

public class HelloWorldAction implements RunAction2 {

	private String name;
	private transient Run run;

	public HelloWorldAction(String name){
		this.name = name;
	}

	@Override
	public String getIconFileName(){
		return "document.png";
	}

	@Override
	public String getDisplayName(){
		return "Greeting";
	}

	@Override
	public String getUrlName(){
		return "greeting";
	}

	public String getName(){
		return name;
	}

	@Override
	public void onAttached(Run<?,?> run){
		this.run = run;
	}

	@Override
	public void onLoad(Run<?,?> run){
		this.run = run;
	}

	public Run getRun(){
		return run;
	}
}
