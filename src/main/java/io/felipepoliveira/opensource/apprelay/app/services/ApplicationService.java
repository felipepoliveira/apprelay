package io.felipepoliveira.opensource.apprelay.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.felipepoliveira.opensource.apprelay.app.db.ApplicationDB;
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

}
