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

import com.done.Done;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;

public class JSONStorage implements DoneStorage{
	
	private static Logger logger = Logger.getLogger("JSONStorage");
	private FileHandler fileHandler;
	
	private Gson gson;
	private Properties pref;
	private String prefName;
	private String jsonName;
	
	public JSONStorage(){
		setUpLogger();
		this.gson = new Gson();
		pref = new Properties();
		prefName = "done_prefs.xml";
		jsonName = getJsonNameFromPref();
	}

	@Override
	public List<Done> load() {
		logger.log(Level.INFO, "load() method executed");
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
		if(inFile.length()<=0){
			assert inFile.length() <= 0; // assert that the file is indeed empty
			return new ArrayList<Done>();
		}else{
		
			// else get from JSON object in file into ArrayList
			Type collectionType = new TypeToken<List<Done>>() {
			}.getType();
			try{
				tasks = gson.fromJson(inFileRead, collectionType);
				assert tasks != null; // assert that the tasks ArrayList is not null
			}catch(JsonIOException e){
				logger.log(Level.WARNING, "Unable to read JSON file", e);
			}
			
			return tasks;
		}
		
	}

	@Override
	public void store(List<Done> task) {
		logger.log(Level.INFO, "store() method executed");
		
		try {
			FileWriter outFile = new FileWriter(jsonName);
			outFile.write(gson.toJson(task));
			outFile.flush();
			outFile.close();
		} catch (IOException e) {
			logger.log(Level.WARNING, "Unable to write file!", e);
		}
		
	}
	
	public boolean setJsonNameToPref(String jsonName){
		logger.log(Level.INFO, "Setting JSON name");
		pref.setProperty("jsonName", jsonName);
		try {
			pref.storeToXML(new FileOutputStream(prefName), "store to XML");
			return true;
		} catch (Exception e) {
			logger.log(Level.WARNING, "Unable to write file!", e);
			return false;
		}
	}
	
	public String getJsonNameFromPref(){
		logger.log(Level.INFO, "Retrieving JSON name");
		try {
			pref.loadFromXML(new FileInputStream(prefName));
		} catch (IOException e) {
			logger.log(Level.WARNING, "Unable to read file!", e);
		}
		return pref.getProperty("jsonName", "tasks.json");
	}
	
	private void setUpLogger() {
		try {
			fileHandler = new FileHandler("Done.log");
			logger.addHandler(fileHandler);
			SimpleFormatter sf = new SimpleFormatter();
			fileHandler.setFormatter(sf);
		} catch (SecurityException | IOException e) {
			logger.log(Level.WARNING,"Unable to read file!", e);
		}
	}
	
}
