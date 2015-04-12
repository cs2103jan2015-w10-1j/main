package com.done.command;

import java.util.logging.Level;

import com.done.model.Done;
import com.done.model.DoneTimedTask;
import com.done.storage.InMemStorage;
import com.done.task.TaskReminder;

public class CommandRemind extends Command {
	
	private int remindIndex;
	private Done task;
	private String date;
	private String time;
	private TaskReminder reminderTask;
	
	//@author A0115777W
	public CommandRemind(int remindIndex, String date, String time) {
		super(CommandType.REMIND, true);
		this.task = InMemStorage.getInstance().getTask(remindIndex);
		this.remindIndex = remindIndex;
		this.date = date;
		this.time = time;
		commandLogger.log(Level.INFO, "Remind Command Created");
	}

	@Override
	public void execute() throws Exception {
		commandLogger.log(Level.INFO, "Remind Command called");
		this.reminderTask = new TaskReminder(task, date, time);
		commandLogger.log(Level.INFO, "Remind Command successfully executed");
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
