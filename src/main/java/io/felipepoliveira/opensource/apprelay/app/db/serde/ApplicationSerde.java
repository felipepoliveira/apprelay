package io.felipepoliveira.opensource.apprelay.app.db.serde;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import io.felipepoliveira.opensource.apprelay.Assert;
import io.felipepoliveira.opensource.apprelay.app.db.ApplicationDB;
import io.felipepoliveira.opensource.apprelay.app.models.Application;

@Repository
public class ApplicationSerde extends AbstractSerde<String, Application> implements ApplicationDB {
	
	/**
	 * Database used in the SERDE database implementation
	 */
	private Map<String, Application> database;
	
	public static final String FILE_PATH = "../db/apps.db";
	
	@PostConstruct
	private void loadDatabase() throws IOException, ClassNotFoundException {
		this.database = this.desserializeFromFileOrEmpty(FILE_PATH);
	}
	
	private String normalizeKey(String appName) {
		Assert.is(appName != null, "Application name can not be null");
		return appName.toLowerCase();
	}

	@Override
	public void insertOrReplace(Application app) {
		var normalizedKey = normalizeKey(app.getName());
		
		// Include in the database and serialize it
		this.database.put(normalizedKey, app);
		try {
			this.serializeToFile(this.database, FILE_PATH);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Application findByName(String appName) {
		var normalizedKey = normalizeKey(appName);
		return this.database.get(normalizedKey);
	}

	@Override
	public Collection<Application> fetchAll() {
		return this.database.values();
	}

}
