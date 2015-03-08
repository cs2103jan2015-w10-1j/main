package com.done.logic;

import java.util.Iterator;
import java.util.List;

import com.done.Done;
import com.done.storage.DoneStorage;
import com.done.storage.JSONStorage;
 
public class Logic {
 	
	private static final String MESSAGE_DELETE = "Task %1$s deleted!";
	private static final String MESSAGE_ADD = "Task \"%1$s\" added!";
	
	private List<Done> tasks;
	private DoneStorage jsonStorage;
	
	public Logic(){
		this.jsonStorage = new JSONStorage();
		this.tasks = jsonStorage.load();
	}
 	
 	public void addFloating(String title){
 		Done task = new Done(title);
		tasks.add(task);
		updateTaskID();
		jsonStorage.store(tasks);
		System.out.println(String.format(MESSAGE_ADD, title));
	}

	private void updateTaskID() {
		Iterator<Done> listIterator = tasks.iterator();
		int i=1;
		while(listIterator.hasNext()){
			listIterator.next().setId(i);
			i++;
		}
	}
	
	public void delete(int deleteIndex){
		tasks.remove(deleteIndex);
		updateTaskID();
		jsonStorage.store(tasks);
		/*String strToDelete;
		strToDelete = new String(tasks.get(deleteIndex - 1).toString());   
		tasks.remove(deleteIndex - 1);
		System.out.println(String.format(MESSAGE_DELETE, strToDelete));
		jsonStorage.store(tasks);*/
	
	}

	/**
	 * @return the tasks
	 */
	public List<Done> getTasks() {
		return tasks;
	}
 
 }