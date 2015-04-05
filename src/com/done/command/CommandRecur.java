package com.done.command;

import java.util.logging.Level;

import com.done.command.Command.CommandType;
import com.done.model.Done;
import com.done.storage.InMemStorage;

public class CommandRecur extends Command {
	
	private Done task;
	private String period;
	
	public CommandRecur(int recurIndex, String period){
		super(CommandType.RECUR, true);
		this.task = InMemStorage.getInstance().getTask(recurIndex);
		this.period = period;
		commandLogger.log(Level.INFO, "Recur Command Created");
	}

	public CommandRecur(Done task,String period){
		super(CommandType.RECUR,true);
		this.task = task;
		this.period = period;
		commandLogger.log(Level.INFO, "Recur Command Created");
	}
	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub

	}

}
