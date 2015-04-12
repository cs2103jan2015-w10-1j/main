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
	private static final String EDIT_COMMAND_CONTENT = "%1$s edited to %2$s";

	//@author A0115777W
	public CommandEdit(int editIndex, Done task) throws Exception {
		super(CommandType.EDIT, true);
		assert editIndex > 0;
		if (editIndex > InMemStorage.getInstance().getTasks().size()) {
			throw new Exception("Too large Destination Index Value");
		}
		this.editIndex = editIndex;
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
		commandLogger.log(Level.INFO, "Undo Command called");
		InMemStorage memory = InMemStorage.getInstance();
		memory.edit(subbedTask, editIndex - 1);
		commandLogger.log(Level.INFO, "Undo Command successfully executed");
	}

	//@author A0088821X
	@Override
	public String getCommandContent() {
		return String.format(EDIT_COMMAND_CONTENT, editIndex, task.getTitle());
	}
}
