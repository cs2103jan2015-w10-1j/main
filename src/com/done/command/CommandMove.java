package com.done.command;

import java.util.List;
import java.util.logging.Level;

import com.done.model.Done;
import com.done.storage.InMemStorage;

public class CommandMove extends Command {

	private int origin;
	private int destination;
	
	//@author A0115777W
	public CommandMove(int origin, int destination) {
		super(CommandType.MOVE, true);
		this.origin = origin-1;
		this.destination = destination-1;
		commandLogger.log(Level.INFO, "Move Command Created");
	}

	@Override
	public void execute() throws Exception {
		commandLogger.log(Level.INFO, "Move Command Execution called");
		InMemStorage memory = InMemStorage.getInstance();
		//List<Done> tasks = memory.getTasks();
		//swap(tasks, origin, destination);
		memory.move(origin, destination);
		commandLogger.log(Level.INFO, "Move Command Execution Successful");
	}

	@Override
	public void undo() throws Exception {
		InMemStorage memory = InMemStorage.getInstance();
		//List<Done> tasks = memory.getTasks();
		//swap(tasks, destination, origin);
		memory.move(destination, origin);
	}

	//@author A0115777W-unused
	private void swap(List<Done> tasks, int origin, int destination) {
		Done temp = tasks.get(destination);
		tasks.set(destination, tasks.get(origin));
		tasks.set(origin, temp);
	}

}
