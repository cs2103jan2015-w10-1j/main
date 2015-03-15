package com.done.logic;

import java.util.Iterator;
import java.util.List;

import com.done.model.Done;
import com.done.storage.DoneStorage;
import com.done.storage.InMemStorage;
 
public class Logic {
 	
	private static final String MESSAGE_DELETE = "Task %1$s deleted!";
	private static final String MESSAGE_ADD = "Task \"%1$s\" added!";
	private static final String ERROR_SEARCH="Exception in search function";
	
	private List<Done> tasks;
	private DoneStorage inMemStorage;
	
	public Logic(){
		this.inMemStorage = new InMemStorage();
		this.tasks = inMemStorage.load();
	}
 	
 	public void addFloating(String title){
 		Done task = new Done(title);
		tasks.add(task);
		updateTaskID();
		inMemStorage.store(tasks);
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
	
	public boolean isExistingTask(int deleteIndex){
		Iterator<Done> listIterator = tasks.iterator();
		int i=0;
		while(listIterator.hasNext()){
			i++;
			listIterator.next().setId(i);
		}
		return (deleteIndex >=1) && (deleteIndex <= i);
	}
	
	public void deleteItem(int deleteIndex){
		tasks.remove(deleteIndex);
		updateTaskID();
		inMemStorage.store(tasks);
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
	
	public CommandType getCmdType(String usercommand){
		this.getCommandType(usercommand);
	}
	
	public Vector<String> searchItem(String searchString) {
		try {
			int flag = 0;
			Vector<String> searchVector=new Vector<String>(); 
			for (int i = 0; i < tasks.size(); i++) {
				String temp = tasks.get(i).toString().toLowerCase(); 
				if (temp.contains(searchString.toLowerCase())) {
					flag = 1;
					searchVector.add((i+1)+". "+tasks.get(i)); 
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
 
 }