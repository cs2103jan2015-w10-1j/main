package com.done.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.done.model.Done;
import com.done.model.DoneFloatingTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;

public class JSONStorage implements DoneStorage {

	private static final String FILE_JSON_EXT = ".json";
	private static final String FILE_TASKS_JSON = "tasks";
	private static final String FILE_PREFS_XML = "done_prefs.xml";
	private static Logger logger = Logger.getLogger("JSONStorage");
	private FileHandler fileHandler;

	private Gson gson;
	private Properties pref;
	private String prefName;

	public JSONStorage() {
		// setUpLogger();
		this.gson = new GsonBuilder()
				.registerTypeAdapter(Done.class, new DoneAdapter())
				.setPrettyPrinting().create();
		pref = new Properties();
		prefName = FILE_PREFS_XML;
	}

	@Override
	public List<Done> load() {
		logger.log(Level.INFO, "load() method executed");

		String jsonName = getJsonNameFromPref();

		FileReader inFileRead = null;
		File inFile = FileCheck.openFile(jsonName);
		List<Done> tasks = null;

		// read the actual JSON file
		try {
			inFileRead = new FileReader(jsonName);
		} catch (FileNotFoundException e) {
			logger.log(Level.WARNING, "File not found!", e);
		}

		// check if the JSON file has objects
		// if not return empty new ArrayList for loading
		if (inFile.length() <= 0) {
			assert inFile.length() <= 0; // assert that the file is indeed empty
			return new ArrayList<Done>();
		} else {

			// else get from JSON object in file into ArrayList
			Type collectionType = new TypeToken<Done>() {
			}.getType();
			try {
				tasks = gson.fromJson(inFileRead, collectionType);
				assert tasks != null; // assert that the tasks ArrayList is not
										// null
			} catch (JsonIOException e) {
				logger.log(Level.WARNING, "Unable to read JSON file", e);
			}

			return tasks;
		}

	}

	@Override
	public boolean store(List<Done> task) {
		logger.log(Level.INFO, "store() method executed");

		String jsonName = getJsonNameFromPref();
		try {
			FileWriter outFile = new FileWriter(jsonName);
			outFile.write(gson.toJson(task, Done.class));
			outFile.flush();
			outFile.close();
			return true;
		} catch (IOException e) {
			logger.log(Level.WARNING, "Unable to write file!", e);
		}
		return false;
	}

	public boolean setJsonNameToPref(String jsonName) {
		logger.log(Level.INFO, "Setting JSON name");
		pref.setProperty("jsonName", jsonName + FILE_JSON_EXT);
		try {
			File prefFile = FileCheck.openFile(prefName);
			pref.storeToXML(new FileOutputStream(prefFile), "store to XML");
			return true;
		} catch (Exception e) {
			logger.log(Level.WARNING, "Unable to write file!", e);
			return false;
		}
	}

	public String getJsonNameFromPref() {
		logger.log(Level.INFO, "Retrieving JSON name");
		try {
			File prefFile = FileCheck.openFile(prefName);
			if (prefFile.length() <= 0) {
				logger.log(Level.INFO,
						"Preference file not found, using default name");
				setJsonNameToPref(FILE_TASKS_JSON);
			}
			pref.loadFromXML(new FileInputStream(prefFile));
		} catch (IOException e) {
			logger.log(Level.WARNING, "Unable to read preference file!", e);
		}
		return pref.getProperty("jsonName", FILE_TASKS_JSON);
	}

	private void setUpLogger() {
		SimpleFormatter sf = new SimpleFormatter();

		try {
			fileHandler = new FileHandler("Done.log");
			logger.addHandler(fileHandler);
			fileHandler.setFormatter(sf);
		} catch (SecurityException | IOException e) {
			logger.log(Level.WARNING, "Unable to read file!", e);
		}
	}

}
