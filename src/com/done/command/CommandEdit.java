package com.done.command;

import java.util.List;
import java.util.logging.Level;

import com.done.command.Command.CommandType;
import com.done.model.Done;
import com.done.storage.InMemStorage;

public class CommandEdit extends Command {

	private int editIndex;
	private Done task;
	private Done subbedTask;

	public CommandEdit(int index, Done task) {
		super(CommandType.EDIT, true);
		this.editIndex = index;
		this.task = task;
		commandLogger.log(Level.INFO, "Edit Command Created");
	}

	@Override
	// This method edits First tasks with the same Title
	public void execute() throws Exception {
		commandLogger.log(Level.INFO, "Edit Command called");
		InMemStorage memory = InMemStorage.getInstance();
		this.subbedTask = memory.getTask(editIndex);
		memory.edit(task, editIndex - 1);
		commandLogger.log(Level.INFO, "Edit Command successfully executed");
	}

	@Override
	public void undo() {
		InMemStorage memory = InMemStorage.getInstance();
		memory.edit(subbedTask, editIndex);
	}

}
