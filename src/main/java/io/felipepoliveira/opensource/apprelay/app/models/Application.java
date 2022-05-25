package io.felipepoliveira.opensource.apprelay.app.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import io.felipepoliveira.opensource.apprelay.Assert;
import io.felipepoliveira.opensource.apprelay.app.extconf.yaml.ExecuteConfiguration;
import io.felipepoliveira.opensource.apprelay.app.extconf.yaml.AppYamlConfiguration;
import io.felipepoliveira.opensource.apprelay.app.functions.FunctionExecutor;
import io.felipepoliveira.opensource.apprelay.app.models.compfields.application.OnLoad;
import io.felipepoliveira.opensource.apprelay.app.models.compfields.application.Run;

public class Application implements Serializable{

	/**
	 * This SUID is used for serialization database
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * An unique name used to identify the application
	 */
	private String name;
	
	/**
	 * The directory used as reference to execute the application
	 */
	private String appDirectory;
	
	private OnLoad onLoadEvent;
	
	private Run runEvent;
	
	public static Application fromYaml(File yamlFile) throws FileNotFoundException {
		Yaml yaml = new Yaml(new Constructor(AppYamlConfiguration.class));
		var fileIS = new FileInputStream(yamlFile);
		AppYamlConfiguration yamlConfiguration = yaml.load(fileIS);
		
		
		// make required data assertions
		Assert.is(yamlConfiguration != null, "YAML can not be null");
		Assert.is(yamlConfiguration.getApp() != null, "YAML file should have 'app' configuration section");
		Assert.is(yamlConfiguration.getApp().getName() != null, "'app' configuration should have an 'name'");
		Assert.is(yamlConfiguration.getRun() != null, "YAML file hould have an 'run' configuration section with at least one 'execute' command");
		Assert.is(yamlConfiguration.getRun().getExecute() != null, "YAML file hould have an 'run' configuration section with at least one 'execute' command");
		
		// create the app object
		var app = new Application();
		
		// put 'app' conf data
		app.setAppDirectory(yamlFile.getParent());
		app.setName(yamlConfiguration.getApp().getName());
		
		// put 'run' conf data
		app.setRunEvent(new Run());
		app.getRunEvent().setEvent(yamlConfiguration.getRun().getExecute().createFunctionExecutorInstance(app.getAppDirectory()));
		
		// get optional 'onLoad' conf data
		if (yamlConfiguration.getOnLoad() != null) {
			app.setOnLoadEvent(new OnLoad());
			
			// take each 'execute' command into the app
			if (yamlConfiguration.getOnLoad().getExecute() != null && yamlConfiguration.getOnLoad().getExecute().size() > 0) {
				app.getOnLoadEvent().setEvents(new ArrayList<FunctionExecutor>(yamlConfiguration.getOnLoad().getExecute().size()));
				for (ExecuteConfiguration execute : yamlConfiguration.getOnLoad().getExecute()) {
					app.getOnLoadEvent().getEvents().add(execute.createFunctionExecutorInstance(app.getAppDirectory()));
				}
			}
		}
		
		return app;
	}
	
	public void triggerOnLoad() {
		
		if (this.onLoadEvent != null && this.onLoadEvent.getEvents() != null) {
			for (var event : this.onLoadEvent.getEvents()) {
				var result = event.execute(null);
				if (!event.validate(result)) {
					throw new RuntimeException("An error occur while executing 'onLoad' execution: ");
				}
			}
		}
	}
	
	public String getName() {
		return name;
	}

	public String getAppDirectory() {
		return appDirectory;
	}

	public void setAppDirectory(String appDirectory) {
		this.appDirectory = appDirectory;
	}

	public OnLoad getOnLoadEvent() {
		return onLoadEvent;
	}

	public void setOnLoadEvent(OnLoad onLoadEvent) {
		this.onLoadEvent = onLoadEvent;
	}

	public Run getRunEvent() {
		return runEvent;
	}

	public void setRunEvent(Run runEvent) {
		this.runEvent = runEvent;
	}

	public void setName(String name) {
		this.name = name;
	}
}
