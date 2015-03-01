package com.done.logic;

import com.done.Done;
import com.done.storage.DoneStorage;
import com.done.storage.JSONStorage;

public class Logic {
	
	private DoneStorage doneStorage;
	
	public void add(String title){
		Done task = new Done(title);
		doneStorage = new JSONStorage();
		doneStorage.store(task);
	}

}
