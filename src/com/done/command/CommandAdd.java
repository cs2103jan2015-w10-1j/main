package com.done.command;

import java.util.logging.Level;

import com.done.model.Done;
import com.done.model.Done.TaskType;
import com.done.model.DoneTimedTask;
import com.done.storage.InMemStorage;
import com.done.task.TaskReminder;

public class CommandAdd extends Command {

	private Done task;
	private TaskReminder reminderTask;
	
	private static final String MESSAGE_CREATION = "Add Command Created"; 

	//@author A0115777W
	public CommandAdd(Done task) {
		super(CommandType.ADD, true);
		this.task = task;
		
		if(task.getType().equals(TaskType.TIMED)){
			this.reminderTask = new TaskReminder((DoneTimedTask) task);
		}
		
		commandLogger.log(Level.INFO, MESSAGE_CREATION);
	}

	public Done getTask() {
		return this.task;
	}

	public void setTask(Done task) {
		this.task = task;
	}

	//@author A0111830X
	@Override
	public void execute() throws Exception {
		if (this.task != null) {
			InMemStorage memory = InMemStorage.getInstance();
			memory.store(task);
		} else {
			throw new Exception("TaskNullException");
		}

	}

	@Override
	public void undo() {
		if (task.getType().equals(TaskType.TIMED)) {
			reminderTask.stopTimer();
			CommandDelete command = new CommandDelete(task);
			command.execute();
		} else {
			CommandDelete command = new CommandDelete(task);
			command.execute();
		}
	}
	
	//@author A0088821X
	@Override
	public String getCommandContent(){
		return task.getTitle();
	}

}
