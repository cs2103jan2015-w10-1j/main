package com.done.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.done.model.Done;
import com.done.model.DoneFloatingTask;
import com.done.storage.DoneStorage;
import com.done.storage.InMemStorage;
 
import com.done.storage.JSONStorage;
import com.done.parser.CommandParser;
import com.done.parser.CommandParser.CommandType;
import com.done.parser.CommandUtils;

public class Logic {
 	
	private static final String MESSAGE_DELETE = "Task %1$s deleted!";
	private static final String MESSAGE_ADD = "Task \"%1$s\" added!";
	private static final String MESSAGE_CLEAR = "all content deleted";
	
	private static final String ERROR_SEARCH="Exception in search method";
	private static final String ERROR_ADD="Exception in add method";
	private static final String ERROR_CLEAR = "Exception in clear method";
	
	private List<Done> tasks;
	private DoneStorage inMemStorage;
	
	private CommandParser cmdParser;
	
	public Logic(){
		this.inMemStorage = new InMemStorage();
		cmdParser = new CommandParser();
	}
 	
	//method to add floating task
 	public void addTask(String userCommand){
	 	try{
 			Done task = new DoneFloatingTask(userCommand);
			tasks.add(task);
			//updateTaskID();
			inMemStorage.store(tasks);
			System.out.println(String.format(MESSAGE_ADD, userCommand));
	 	} catch (Exception e) {
			System.out.println(ERROR_ADD + e.getMessage());
		}
	}

	private void updateTaskID() {
		Iterator<Done> listIterator = tasks.iterator();
		int i=1;
		while(listIterator.hasNext()){
			listIterator.next().setId(i);
			i++;
		}
	}
	
	public boolean isExistingTask(int deleteIndex){
		Iterator<Done> listIterator = tasks.iterator();
		int i=0;
		while(listIterator.hasNext()){
			i++;
			listIterator.next().setId(i);
		}
		return (deleteIndex >=1) && (deleteIndex <= i);
	}
	
	public String deleteTask(int deleteIndex){
		String taskName = tasks.remove(deleteIndex).getTitle();
		inMemStorage.store(tasks);
		return taskName;
	}

	/**
	 * @return the tasks
	 */
	public List<Done> getTasks() {
		this.tasks = inMemStorage.load();
		return tasks;
	}
	
	public CommandType getCmdType(String usercommand){
		//this.getCommandType(usercommand);
		return cmdParser.getCommandType(usercommand);
	}
	
	public String getCmdContent(String usercommand){
		return cmdParser.getCommandContentString(usercommand);
	}
	
	public Done getTask(String usercommand){
		int index = 0;
		return this.searchTask(usercommand).get(index);
	}
	
	public List<Done> searchTask(String searchString) {
		try {
			int flag = 0;
			List<Done> searchVector = new ArrayList<Done>(); 
			for (int i = 0; i < tasks.size(); i++) {
				String temp = tasks.get(i).toString().toLowerCase(); 
				if (temp.contains(searchString.toLowerCase())) {
					flag = 1;
					searchVector.add(new DoneFloatingTask((i+1)+". "+tasks.get(i))); 
				} 
			}
			
			if (flag == 0)
				System.out.println("String Not Found!");
			
			return searchVector;

		} catch (Exception e) {
			System.out.println(ERROR_SEARCH + e.getMessage());
		}
		return null;
	}
	
	public void undo(){
		
	}
	
	//private service method useful to implement setReminder(), markdone() and other methods.
	private void addTag(String tagString){
		
	}
	
	public void clearTasks(){
		try {
			tasks.clear();
			System.out.println(MESSAGE_CLEAR);
			updateTaskID();
			inMemStorage.store(tasks);
		} catch (Exception e) {
			System.out.println(ERROR_CLEAR + e.getMessage());
		}
		
	}
	
	//to mark the task is done
	public void markDone(){
		
	}
	
	//set a reminder to the task and remind the user at the date
	public void setReminder(String date){
		
	}
	
	public void storeTo(String jsonName){
		JSONStorage jsonPref = new JSONStorage();
		jsonPref.setJsonNameToPref(jsonName);
	}
 
 }