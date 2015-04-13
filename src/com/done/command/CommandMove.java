package com.done.command;

import java.util.List;
import java.util.logging.Level;

import com.done.model.Done;
import com.done.storage.InMemStorage;

public class CommandMove extends Command {

	private static final String MESSAGE_CREATION = "Move Command Created"; 
	private static final String MOVE_COMMAND_CONTENT = "%1$s to %2$s";

	private int origin;
	private int destination;

	//@author A0115777W
	public CommandMove(int origin, int destination) throws Exception {
		super(CommandType.MOVE, true);
		if (origin > (InMemStorage.getInstance().getTasks().size())
				|| destination > (InMemStorage.getInstance().getTasks().size())) {
			throw new Exception("Too large Index Value");
		}
		this.origin = origin - 1;
		this.destination = destination - 1;
		commandLogger.log(Level.INFO, MESSAGE_CREATION);
	}

	@Override
	public void execute() throws Exception {
		InMemStorage memory = InMemStorage.getInstance();
		memory.move(origin, destination);
	}

	@Override
	public void undo() throws Exception {
		InMemStorage memory = InMemStorage.getInstance();
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
