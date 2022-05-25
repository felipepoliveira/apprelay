package io.felipepoliveira.opensource.apprelay.app.db.serde;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractSerde<K, T> {
	
	@SuppressWarnings("unchecked")
	protected Map<K, T> desserializeFromFileOrEmpty(String filePath) throws IOException, ClassNotFoundException {
		var databaseFile = new File("../db/apps.db");
		
		// if the database file does not exists starts with an empty database
		if (!databaseFile.exists()) {
			return new HashMap<K, T>();
		}
		
		// load the database using serialization
		var databaseFileIS = new FileInputStream(databaseFile);
		var objectInputStream = new ObjectInputStream(databaseFileIS);

		// load the database using desserialization
		var database = (HashMap<K, T>) objectInputStream.readObject();
		
		objectInputStream.close();
		
		return database;
	}
	
	protected void serializeToFile(Map<K, T> database, String filePath) throws IOException {
		serializeToFile(database, new File(filePath));
	}
	
	
	protected void serializeToFile(Map<K, T> database, File file) throws IOException {
		// Assure that the file exists
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			file.createNewFile();
		}
		
		// Serialize the object into the file
		var databaseFileOS = new FileOutputStream(file);
		var objectOutputStream = new ObjectOutputStream(databaseFileOS);
		objectOutputStream.writeObject(database);
		objectOutputStream.close();
	}

}
