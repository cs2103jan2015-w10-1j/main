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
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class JSONStorage implements DoneStorage{
	
	private List<Done> tasks;
	private Gson gson;
	
	public JSONStorage(){
		this.tasks = new ArrayList<Done>();
		this.gson = new Gson();
	}

	@Override
	public List<Done> load() {
		FileReader inFileRead = null;
		File inFile = FileHandler.openFile("tasks.json");
		
		try {
			inFileRead = new FileReader("tasks.json");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		if(inFile.length()<=0){
			return new ArrayList<Done>();
		}
		
		Type collectionType = new TypeToken<List<Done>>() {
		}.getType();
		List<Done> tasks = gson.fromJson(inFileRead, collectionType);
		
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
