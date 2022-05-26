package io.felipepoliveira.opensource.apprelay.app.db.serde;

import java.io.IOException;
import java.util.Collection;

import org.springframework.stereotype.Repository;

import io.felipepoliveira.opensource.apprelay.Assert;
import io.felipepoliveira.opensource.apprelay.app.db.ApplicationDB;
import io.felipepoliveira.opensource.apprelay.app.models.Application;

@Repository
public class ApplicationSerde extends AbstractSerde<String, Application> implements ApplicationDB {


	public static final String FILE_PATH = "../db/apps.db";

	private String normalizeKey(String appName) {
		Assert.is(appName != null, "Application name can not be null");
		return appName.toLowerCase();
	}

	@Override
	public void insertOrReplace(Application app) {
		var normalizedKey = normalizeKey(app.getName());
		
		try {
			var db = this.desserializeFromFileOrEmpty(FILE_PATH);
			db.put(normalizedKey, app);
			this.serializeToFile(db, FILE_PATH);
		} catch (IOException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Application findByName(String appName) {
		var normalizedKey = normalizeKey(appName);
		try {
			var db = this.desserializeFromFileOrEmpty(FILE_PATH);
			return db.get(normalizedKey);
		} catch (IOException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Collection<Application> fetchAll() {
		try {
			var db = this.desserializeFromFileOrEmpty(FILE_PATH);
			return db.values();
		} catch (IOException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void remove(Application app) {
		var normalizedKey = normalizeKey(app.getName());
		try {
			var db = this.desserializeFromFileOrEmpty(FILE_PATH);
			db.remove(normalizedKey);
			this.serializeToFile(db, FILE_PATH);
		} catch (IOException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void removeAll() {
		try {
			var db = this.desserializeFromFileOrEmpty(FILE_PATH);
			db.clear();
			this.serializeToFile(db, FILE_PATH);
		} catch (IOException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

}
