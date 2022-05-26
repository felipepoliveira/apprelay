package io.felipepoliveira.opensource.apprelay.app.db;

import java.util.Collection;

import io.felipepoliveira.opensource.apprelay.app.models.Application;

public interface ApplicationDB {
	
	/**
	 * Store an application into the application database. An application has an unique identifier using its 'name' parameter.
	 * If there is already an application with the same name this method will thrown an {@link RuntimeException}.
	 * @param app
	 * @param replaceIfExists
	 */
	void insertOrReplace(Application app);
	
	/**
	 * Find an application by its name
	 * @param appName
	 * @return
	 */
	Application findByName(String appName);
	
	/**
	 * List all registered apps
	 * @return
	 */
	Collection<Application> fetchAll();
	
	/**
	 * Remove the application from the database
	 * @param app
	 */
	void remove(Application app);
	
	/**
	 * Remove all applications from the database
	 */
	void removeAll();

}
