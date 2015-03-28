package com.done.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.done.model.Done;
import com.done.model.DoneFloatingTask;
import com.done.storage.DoneStorage;
import com.done.storage.InMemStorage;
 
import com.done.parser.CommandParser;
import com.done.command.Command;
import com.done.command.Command.CommandType;
import com.done.command.CommandAdd;
import com.done.parser.ParserUtils;

public class Logic {
 	
	private static final int ARRAY_DELETE_OFFSET = 1;
	private static final String MESSAGE_DELETE = "Task %1$s deleted!";
	private static final String MESSAGE_ADD = "Task \"%1$s\" added!";
	private static final String MESSAGE_CLEAR = "all content deleted";
	
	private static final String ERROR_SEARCH="Exception in search method";
	private static final String ERROR_ADD="Exception in add method";
	private static final String ERROR_CLEAR = "Exception in clear method";
	private static final String ERROR_PROCESS_COMMAND ="Error executing the command";
	
	private CommandParser cmdParser;
	private List<Done> tasks;
	private InMemStorage inMemStorage;
	private boolean isSuccessful;
	
	public Logic(){
		this.inMemStorage = InMemStorage.getInstance();
		this.tasks = inMemStorage.load();
		this.cmdParser = new CommandParser();
	}
 	
	public void executeCommand(Command command){
		try{
			
			// done..
			command.execute();
			isSuccessful = true;
			// ok wait
			// step 2. add command into undo Stack
			// step 3. done..
			
			//processCommand(commandType, commandContent);
		}
		catch(Exception e){
			isSuccessful = false;
		}
		
	}
	
	
	// There is no need to get the commandType anymore since you have already created command in parser
	// in order to processCommand, we need to have command first !
	private void processCommand(CommandType commandType, String commandContent){

		
 /*		try{	
 			
	 		switch(command){
		
	 		case EXIT:
	 			System.exit(0);
				break;
				
	 		case ADD:
	 			// JERRY: I HAVE MODIFIED THIS PART OF THE CODE
				//addTask(content);
				Command add = new CommandAdd(content);
				add.execute();
				isSuccessful = true;
				break;	
				
	 		case DELETE:
				int index= Integer.parseInt(content); 
				deleteTask(index-ARRAY_DELETE_OFFSET);
				break;
				
	 		case CLEAR:
				clearTasks();
				break;
				
	 		case DISPLAY:
				//need to discuss
				break;
			case SEARCH:
			 	searchTask(content);
				break;
			default:
				break;		
	 		}
 		}
	
 		catch(Exception e){
 			System.out.println(ERROR_PROCESS_COMMAND + e.getMessage());
 		}*/
	}
	
	
	public CommandType getCmdType(String userCommand){
		CommandType command = cmdParser.getCommandType(userCommand);
		return command;
	}
	
	public String getCmdContent(String userCommand){
		return ParserUtils.removeFirstWord(userCommand);
	}
	
	public boolean isSuccessful(){
		return isSuccessful;
	}
	//method to add floating task
 	/*public void addTask(String title){
	 	try{
 			Done task = new DoneFloatingTask(title);
			tasks.add(task);
			inMemStorage.store(tasks);
			isSuccessful = true;
			System.out.println(String.format(MESSAGE_ADD, title));
	 	} catch (Exception e) {
			System.out.println(ERROR_ADD + e.getMessage());
		}
	}*/
	
	public boolean isExistingTask(int deleteIndex){
		Iterator<Done> listIterator = tasks.iterator();
		int i=0;
		while(listIterator.hasNext()){
			i++;
			listIterator.next().setId(i);
		}
		return (deleteIndex >=1) && (deleteIndex <= i);
	}
	
	/*public void deleteTask(int deleteIndex){
		tasks.remove(deleteIndex);
		inMemStorage.store(tasks);
		isSuccessful = true;
		String strToDelete;
		strToDelete = new String(tasks.get(deleteIndex - 1).toString());   
		tasks.remove(deleteIndex - 1);
		System.out.println(String.format(MESSAGE_DELETE, strToDelete));
		jsonStorage.store(tasks);
	
	}*/

	/**
	 * @return the tasks
	 */
	public List<Done> getTasksForUI() {
		return tasks;
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
			
			isSuccessful = true;
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
	
	/*public void clearTasks(){
		try {
			tasks.clear();
			System.out.println(MESSAGE_CLEAR);
			inMemStorage.store(tasks);
			isSuccessful = true;
		} catch (Exception e) {
			System.out.println(ERROR_CLEAR + e.getMessage());
		}
		
	}*/
	
	/*public void editTask(int index, String editString){
		deleteTask(index-1);
		addTask(editString);
	}*/
	
	//to mark the task is done
	/*public void markDone(int index){
		String done = "You have done this task!";
		this.editTask(index, done);
	}*/
	
	//set a reminder to the task and remind the user at the date
	public void setReminder(String date){
		
	}
 
 }