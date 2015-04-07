package com.done.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.done.model.Done;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;

public class JSONStorage {

	private static JSONStorage instance = null;
	
	private static final String DIR_LOG = "log\\";
	private static final String DIR_PREF = "prefs\\";
	private static final String DIR_TASKS = "tasks\\";
	private static final String FILE_JSON_EXT = ".json";
	private static final String FILE_TASKS_JSON = DIR_TASKS + "tasks";
	private static final String FILE_PREFS_XML = DIR_PREF + "done_prefs.xml";
	private static Logger logger = Logger.getLogger("JSONStorage");

	private Gson gson;
	private Properties pref;
	private String jsonName;
	private boolean isNewJson;

	private FileHandler fileHandler;

	private JSONStorage() {
		setUpLogger();
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
		logger.log(Level.INFO, "load() method executed");
		FileReader inFileRead = null;
		List<Done> tasks = null;

		if (isNewJson) {
			jsonName = getJsonNameFromPref();
		}
		File inFile = openFile(jsonName);

		// read the actual JSON file
		try {
			inFileRead = new FileReader(jsonName);
		} catch (IOException e) {
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
				assert tasks != null; // assert that the tasks ArrayList is not null
			} catch (JsonIOException e) {
				logger.log(Level.WARNING,
						"Unable to read JSON file, creating new empty List", e);
				return new ArrayList<Done>();
			}
			return tasks;
		}

	}

	public boolean store(List<Done> task) {
		logger.log(Level.INFO, "store() method executed");

		try {
			FileWriter outFile = new FileWriter(jsonName);
			outFile.write(gson.toJson(task, Done.class));
			outFile.flush();
			outFile.close();
			return true;
		} catch (IOException e) {
			logger.log(Level.WARNING, "Unable to write file!", e);
			return false;
		}
	}

	public boolean setJsonNameToPref(String jsonName) {
		logger.log(Level.INFO, "Setting JSON name");
		pref.setProperty("jsonName", jsonName + FILE_JSON_EXT);
		try {
			File prefFile = openFile(FILE_PREFS_XML);
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
			File prefFile = openFile(FILE_PREFS_XML);
			pref.loadFromXML(new FileInputStream(prefFile));
		} catch (IOException e) {
			logger.log(Level.WARNING,
					"Unable to read preference file! Using default.");
			setJsonNameToPref(FILE_TASKS_JSON);
		}

		return pref.getProperty("jsonName", FILE_TASKS_JSON);

	}

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

	private void setUpLogger() {
		SimpleFormatter sf = new SimpleFormatter();

		try {
			String out = new SimpleDateFormat(
					"'StorageLog-'dd-MM-yyyy HH-mm'.log'").format(new Date());
			File file = new File(DIR_LOG);
			file.mkdir();
			fileHandler = new FileHandler(DIR_LOG + out);
			logger.addHandler(fileHandler);
			fileHandler.setFormatter(sf);
		} catch (SecurityException | IOException e) {
			logger.log(Level.WARNING, "Unable to read file!", e);
		}
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

	//@author generated
	public boolean isNewJson() {
		return isNewJson;
	}

	public void setNewJson(boolean isNewJson) {
		this.isNewJson = isNewJson;
	}

}
