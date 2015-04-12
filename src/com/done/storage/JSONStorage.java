package com.done.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import com.done.model.Done;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;

public class JSONStorage {

	private static JSONStorage instance = null;

	private static final String DIR_PREF = "prefs//";
	private static final String DIR_TASKS = "tasks//";
	private static final String FILE_JSON_EXT = ".json";
	private static final String FILE_TASKS_JSON = DIR_TASKS + "tasks";
	private static final String FILE_PREFS_XML = DIR_PREF + "done_prefs.xml";

	private Gson gson;
	private Properties pref;
	private String jsonName;
	private boolean isNewJson;

	private JSONStorage() {
		setUpDirectories();
		this.gson = new GsonBuilder()
				.registerTypeAdapter(Done.class, new DoneAdapter())
				.setPrettyPrinting().serializeNulls().create();
		pref = new Properties();
		jsonName = getJsonNameFromPref();
		isNewJson = false;
	}

	public static synchronized JSONStorage getInstance() {
		if (instance == null) {
			instance = new JSONStorage();
		}

		return instance;
	}

	//@author A0111830X
	public List<Done> load() {
		StorageLogger.getStorageLogger().log(Level.INFO, "Load from JSON");
		FileReader inFileRead = null;
		List<Done> tasks = null;

		// isNewJson to check if it's a different filename from preference file
		if (isNewJson) {
			jsonName = getJsonNameFromPref();
		}
		File inFile = openFile(jsonName);

		// read the actual JSON file
		try {
			inFileRead = new FileReader(jsonName);
		} catch (IOException e) {
			StorageLogger.getStorageLogger().log(Level.WARNING,
					"File not found!", e);
			// if IOException occurs while reading file, return empty List of
			// Done
			return new ArrayList<Done>();
		}

		// check if the JSON file has objects
		// if not return empty new ArrayList for loading
		if (inFile.length() <= 0) {
			assert inFile.length() <= 0; // assert that the file is indeed empty
			return new ArrayList<Done>();
		} else {
			assert inFile.length() > 0;
			// else get from JSON object in file into ArrayList
			Type collectionType = new TypeToken<Done>() {
			}.getType();
			try {
				tasks = gson.fromJson(inFileRead, collectionType);
				assert tasks != null; // assert that the tasks ArrayList is not
										// null
			} catch (JsonIOException e) {
				StorageLogger.getStorageLogger().log(Level.WARNING,
						"Unable to read JSON file, creating new empty List", e);
				// if JSONIOException occurs, return an empty List of Done
				return new ArrayList<Done>();
			}
			return tasks;
		}

	}

	public boolean store(List<Done> task) {
		StorageLogger.getStorageLogger().log(Level.INFO, "Store to JSON");

		try {
			FileWriter outFile = new FileWriter(jsonName);
			outFile.write(gson.toJson(task, Done.class));
			outFile.flush();
			outFile.close();
			return true;
		} catch (IOException e) {
			StorageLogger.getStorageLogger().log(Level.WARNING,
					"Unable to write file!", e);
			// if IOException occurs, return false for Logic to set isSuccessful
			// to false;
			return false;
		}
	}

	public boolean setJsonNameToPref(String jsonName) {
		StorageLogger.getStorageLogger().log(Level.INFO,
				"Setting JSON name: " + jsonName);
		pref.setProperty("jsonName", jsonName + FILE_JSON_EXT);
		try {
			File prefFile = openFile(FILE_PREFS_XML);
			pref.storeToXML(new FileOutputStream(prefFile), "store to XML");
			return true;
		} catch (Exception e) {
			StorageLogger.getStorageLogger().log(Level.WARNING,
					"Unable to write file!", e);
			// if IOException occurs, return false for Logic to set isSuccessful
			// to false;
			return false;
		}
	}

	public String getJsonNameFromPref() {

		String jsonName = "";

		try {
			File prefFile = openFile(FILE_PREFS_XML);
			pref.loadFromXML(new FileInputStream(prefFile));
			jsonName = pref.getProperty("jsonName", FILE_TASKS_JSON);
		} catch (IOException e) {
			StorageLogger.getStorageLogger().log(Level.WARNING,
					"Unable to read preference file! Using default.");
			// If IOException occur, set default preference file name
			setJsonNameToPref(FILE_TASKS_JSON);
		}
		StorageLogger.getStorageLogger().log(Level.INFO,
				"Retrieving JSON name: " + jsonName);
		return jsonName;

	}

	// Utility Methods
	private static File openFile(String fileName) {
		File file = new File(fileName);

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.exit(0);
			}
		}
		return file;
	}

	private void setUpDirectories() {
		File tasksDir = new File(DIR_TASKS);
		File prefDir = new File(DIR_PREF);
		if (!tasksDir.exists()) {
			tasksDir.mkdir();
		}
		if (!prefDir.exists()) {
			prefDir.mkdir();
		}

	}

	// -- End of utility methods

	// @author generated
	public boolean isNewJson() {
		return isNewJson;
	}

	public void setNewJson(boolean isNewJson) {
		this.isNewJson = isNewJson;
	}

}
