package io.felipepoliveira.opensource.apprelay.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.felipepoliveira.opensource.apprelay.app.db.ApplicationDB;
import io.felipepoliveira.opensource.apprelay.app.excp.NotFoundException;
import io.felipepoliveira.opensource.apprelay.app.functions.FunctionExecutionResult;
import io.felipepoliveira.opensource.apprelay.app.models.Application;

@Service
public class ApplicationService {

	@Autowired
	private ApplicationDB applicationDB;

	/**
	 * Find an {@link Application} by name
	 * @param appName
	 * @return
	 * @throws NotFoundException
	 */
	protected Application findByName(String appName) throws NotFoundException {
		var app = applicationDB.findByName(appName);

		// Validate if the app exists
		if (app == null) {
			throw new NotFoundException(String.format("There is no application name \"%s\"", appName));
		}
		
		return app;
	}

	/**
	 * Add an new application on the database
	 * @param application
	 * @param replace
	 * @return
	 */
	public Application addApplication(Application application, boolean replace) {

		// check if there is already an app with the given name
		if (applicationDB.findByName(application.getName()) != null && !replace) {
			throw new RuntimeException("There is alerady an app with name:" + application.getName()
					+ ". Use the 'replace' flag to forceto an update of the registered app");
		}

		// trigger 'onLoad' event
		application.triggerOnLoad();

		// include the app into the database
		applicationDB.insertOrReplace(application);

		return application;
	}

	/**
	 * Start an application
	 * 
	 * @param appName
	 * @param appArgs
	 * @throws NotFoundException
	 */
	public FunctionExecutionResult execute(String appName, String... appArgs) throws NotFoundException {
		var app = findByName(appName);

		// Execute the run routine
		return app.getRunEvent().getEvent().execute(appArgs);
	}

	/**
	 * Execute 'onLoad' routine for all added apps
	 */
	public void executeOnLoad() {
		for (var app : applicationDB.fetchAll()) {
			System.out.println("Executing 'onLoad' routine for: " + app.getName());
			app.triggerOnLoad();
			System.out.println(app.getName() + " 'onLoad' routine finished successfully");
		}
	}

	public void removeApp(String appName) throws NotFoundException {
		// fetch the app by the given name
		var app = findByName(appName);
		
		applicationDB.remove(app);
	}
	
	public void removeAll() {
		applicationDB.removeAll();
	}
}
