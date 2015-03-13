package com.done.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.done.Done;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;

public class JSONStorage implements DoneStorage{
	
	private Gson gson;
	
	public JSONStorage(){
		this.gson = new Gson();
	}

	@Override
	public List<Done> load() {
		FileReader inFileRead = null;
		File inFile = FileHandler.openFile("tasks.json");
		List<Done> tasks = null;
		
		try {
			inFileRead = new FileReader("tasks.json");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		if(inFile.length()<=0){
			assert inFile.length() > 0;
			return new ArrayList<Done>();
		}
		
		Type collectionType = new TypeToken<List<Done>>() {
		}.getType();
		try{
			tasks = gson.fromJson(inFileRead, collectionType);
		}catch(JsonIOException e){
			System.err.println("Unable to read JSON file");
		}
		
		
		return tasks;
		
	}

	@Override
	public void store(List<Done> task) {
		
		try {
			FileWriter outFile = new FileWriter("tasks.json");
			outFile.write(gson.toJson(task));
			outFile.flush();
			outFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
