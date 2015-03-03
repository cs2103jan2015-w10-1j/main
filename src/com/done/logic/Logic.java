package com.done.logic;
 
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.done.Done;
import com.done.storage.DoneStorage;
import com.done.storage.JSONStorage;
 
public class Logic {
 	
	private static final String MESSAGE_DELETE = "Task %1$s deleted!";
	private static final String MESSAGE_ADD = "Task \"%1$s\"added!";
	
	private DoneStorage doneStorage;
	private List<Done> tasks;
	private DoneStorage jsonStorage;
	
	public Logic(){
		this.jsonStorage = new JSONStorage();
		this.tasks = jsonStorage.load();
	}
 	
 	public void add(String title){
 		Done task = new Done(title);
		doneStorage = new JSONStorage();
		doneStorage.store(task);
		tasks.add(task);
		jsonStorage.store(tasks);
		System.out.println(String.format(MESSAGE_ADD, title));
	}

	// skeletal purpose method display() 
	public String display() {
		StringBuilder sb = new StringBuilder();
		
		Iterator<Done> listIterator = tasks.iterator(); 
		while(listIterator.hasNext()){
			sb.append(listIterator.next().getTitle()"\n");
		}
		
		return sb.toString();
 	}
	
	public void delete(int deleteIndex){
		
		String strToDelete;
		strToDelete = new String(tasks.elementAt(deleteIndex - 1).toString());   
		tasks.remove(deleteIndex - 1);
		System.out.println(String.format(MESSAGE_DELETE, strToDelete));
		jsonStorage.store(tasks);
	
	}
 
 }