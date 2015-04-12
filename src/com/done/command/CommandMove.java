package com.done.command;

import java.util.List;
import java.util.logging.Level;

import com.done.model.Done;
import com.done.storage.InMemStorage;

public class CommandMove extends Command {

	private int origin;
	private int destination;
	private static final String MOVE_COMMAND_CONTENT = "%1$s to %2$s";

	//@author A0115777W
	public CommandMove(int origin, int destination) throws Exception {
		super(CommandType.MOVE, true);
		if (origin > (InMemStorage.getInstance().getTasks().size())
				|| destination > (InMemStorage.getInstance().getTasks().size())) {
			throw new Exception("Too large Destination Index Value");
		}
		this.origin = origin - 1;
		this.destination = destination - 1;
		commandLogger.log(Level.INFO, "Move Command Created");
	}

	@Override
	public void execute() throws Exception {
		commandLogger.log(Level.INFO, "Move Command Execution called");
		InMemStorage memory = InMemStorage.getInstance();
		// List<Done> tasks = memory.getTasks();
		// swap(tasks, origin, destination);
		memory.move(origin, destination);
		commandLogger.log(Level.INFO, "Move Command Execution Successful");
	}

	@Override
	public void undo() throws Exception {
		InMemStorage memory = InMemStorage.getInstance();
		// List<Done> tasks = memory.getTasks();
		// swap(tasks, destination, origin);
		memory.move(destination, origin);
	}

	//@author A0115777W-unused
	private void swap(List<Done> tasks, int origin, int destination) {
		Done temp = tasks.get(destination);
		tasks.set(destination, tasks.get(origin));
		tasks.set(origin, temp);
	}

	//@author A0088821X
	@Override
	public String getCommandContent() {
		return String.format(MOVE_COMMAND_CONTENT, origin, destination);
	}

}
