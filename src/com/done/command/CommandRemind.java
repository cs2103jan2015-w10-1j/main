package com.done.command;

import java.util.logging.Level;

import com.done.model.Done;
import com.done.model.DoneTimedTask;
import com.done.storage.InMemStorage;
import com.done.task.TaskReminder;

public class CommandRemind extends Command {
	
	private static final String MESSAGE_CREATION = "Remind Command Created"; 

	private Done task;
	private String date;
	private String time;
	private TaskReminder reminderTask;
	
	//@author A0115777W
	public CommandRemind(int remindIndex, String date, String time) throws Exception {
		super(CommandType.REMIND, true);
		assert remindIndex>0;
		if (remindIndex > InMemStorage.getInstance().getTasks().size()) {
			throw new Exception("Too large Destination Index Value");
		}
		this.task = InMemStorage.getInstance().getTask(remindIndex);
		this.date = date;
		this.time = time;
		commandLogger.log(Level.INFO, MESSAGE_CREATION);
	}

	@Override
	public void execute() throws Exception {
		this.reminderTask = new TaskReminder(task, date, time);
	}

	@Override
	public void undo() {
		//Currently not undoable
		//commandLogger.log(Level.INFO, "undo Remind Command called");
		//commandLogger.log(Level.INFO, "undo Remind Command successfully executed");
		reminderTask.stopTimer();
	}
	
	//@author A0088821X
	@Override
	public String getCommandContent(){
		return task.getTitle();
	}

}
