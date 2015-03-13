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
import java.util.logging.Level;
import java.util.logging.Logger;

import com.done.Done;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;

public class JSONStorage implements DoneStorage{
	
	private static Logger logger = Logger.getLogger("JSONStorage");
	
	private Gson gson;
	private Properties pref;
	private String prefName;
	private String jsonName;
	
	public JSONStorage(){
		this.gson = new Gson();
		pref = new Properties();
		prefName = "done_prefs.xml";
		jsonName = getJsonNameFromPref();
	}

	@Override
	public List<Done> load() {
		FileReader inFileRead = null;
		File inFile = FileHandler.openFile(jsonName);
		List<Done> tasks = null;
		
		try {
			inFileRead = new FileReader(jsonName);
			
		} catch (FileNotFoundException e) {
			logger.log(Level.WARNING, "File not found!", e);
		}
		
		if(inFile.length()<=0){
			assert inFile.length() > 0;
			return new ArrayList<Done>();
		}
		
		Type collectionType = new TypeToken<List<Done>>() {
		}.getType();
		try{
			assert tasks!=null;
			tasks = gson.fromJson(inFileRead, collectionType);
		}catch(JsonIOException e){
			logger.log(Level.WARNING, "Unable to read JSON file", e);
		}
		
		return tasks;
		
	}

	@Override
	public void store(List<Done> task) {
		try {
			FileWriter outFile = new FileWriter(jsonName);
			outFile.write(gson.toJson(task));
			outFile.flush();
			outFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean setJsonNameToPref(String jsonName){
		pref.setProperty("jsonName", jsonName);
		try {
			pref.storeToXML(new FileOutputStream(prefName), "store to XML");
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public String getJsonNameFromPref(){
		try {
			pref.loadFromXML(new FileInputStream(prefName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pref.getProperty("jsonName", "tasks.json");
	}
}
