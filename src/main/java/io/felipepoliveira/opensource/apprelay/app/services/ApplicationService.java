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
	
	public Application addApplication(Application application, boolean replace) {
		
		// check if there is already an app with the given name
		if (applicationDB.findByName(application.getName()) != null && !replace) {
			throw new RuntimeException("There is alerady an app with name:" + application.getName() + ". Use the 'replace' flag to forceto an update of the registered app");
		}
		
		// trigger 'onLoad' event
		application.triggerOnLoad();
		
		// include the app into the database
		applicationDB.insertOrReplace(application);
		
		return application;
	}
	
	/**
	 * Start an application
	 * @param appName
	 * @param appArgs
	 * @throws NotFoundException
	 */
	public FunctionExecutionResult execute(String appName, String... appArgs) throws NotFoundException {
		var app = applicationDB.findByName(appName);
		
		// Validate if the app exists
		if (app == null) {
			throw new NotFoundException(
					String.format("There is no application name \"%s\"", appName)
					);
		}
		
		// Execute the run routine
		return app.getRunEvent().getEvent().execute(appArgs);
	}

}
