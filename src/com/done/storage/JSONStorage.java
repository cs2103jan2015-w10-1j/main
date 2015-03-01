package com.done.storage;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;

import com.done.Done;

public class JSONStorage implements DoneStorage{
	
	FileWriter outFile;

	@Override
	public void load() {
		
	}

	@Override
	public void store(Done task) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("title", task.getTitle());
		
		try {
			outFile = new FileWriter("tasks.json",true);
			outFile.write(jsonObj.toJSONString());
			outFile.flush();
			outFile.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
