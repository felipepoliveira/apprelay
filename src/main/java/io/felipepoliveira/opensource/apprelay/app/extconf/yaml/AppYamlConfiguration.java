package io.felipepoliveira.opensource.apprelay.app.extconf.yaml;

public class AppYamlConfiguration {
	
	private AppConfiguration app;
	
	private OnLoadConfiguration onLoad;
	
	private RunConfiguration run;

	public AppConfiguration getApp() {
		return app;
	}

	public void setApp(AppConfiguration app) {
		this.app = app;
	}

	public OnLoadConfiguration getOnLoad() {
		return onLoad;
	}

	public void setOnLoad(OnLoadConfiguration onLoad) {
		this.onLoad = onLoad;
	}

	public RunConfiguration getRun() {
		return run;
	}

	public void setRun(RunConfiguration run) {
		this.run = run;
	}
}
