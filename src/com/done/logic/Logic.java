package com.done.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.done.Done;
import com.done.storage.DoneStorage;
import com.done.storage.JSONStorage;

public class Logic {
	
	private List<Done> tasks;
	private DoneStorage jsonStorage;
	
	public Logic(){
		this.jsonStorage = new JSONStorage();
		this.tasks = jsonStorage.load();
	}
	
	public void add(String title){
		Done task = new Done(title);
		tasks.add(task);
		jsonStorage.store(tasks);
	}

	// skeletal purpose method display() 
	public String display() {
		StringBuilder sb = new StringBuilder();
		
		Iterator<Done> listIterator = tasks.iterator(); 
		while(listIterator.hasNext()){
			sb.append(listIterator.next().getTitle()+"\n");
		}
		
		return sb.toString();
	}

}
