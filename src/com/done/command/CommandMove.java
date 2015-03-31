package com.done.command;

import java.util.List;
import java.util.logging.Level;

import com.done.model.Done;
import com.done.storage.InMemStorage;

public class CommandMove extends Command {
	
	private int origin;
	private int destination;
	
	public CommandMove(int origin, int destination){
		super(CommandType.MOVE,true);
		this.origin = origin;
		this.destination = destination;
		commandLogger.log(Level.INFO, "Move Command Created");
	}

	@Override
	public void execute() throws Exception {
		commandLogger.log(Level.INFO, "Move Command Execution called");
		InMemStorage memory = InMemStorage.getInstance();
		List<Done> tasks = memory.getTasks();
		swap(tasks, origin, destination);
	}

	@Override
	public void undo() {
		InMemStorage memory = InMemStorage.getInstance();
		List<Done> tasks = memory.getTasks();
		swap(tasks, destination, origin);
	}
	
	private void swap(List<Done> tasks, int origin, int destination){
		Done temp = tasks.get(destination);
		tasks.set(destination,tasks.get(origin));
		tasks.set(origin,temp);
	}

}
