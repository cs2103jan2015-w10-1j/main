package com.done.command;

import java.util.logging.Level;

import com.done.command.Command.CommandType;
import com.done.model.Done;
import com.done.storage.InMemStorage;

public class CommandRemind extends Command {
	
	private Done task;
	private String period;
	
	public CommandRemind(int remindIndex, String period) {
		super(CommandType.REMIND, true);
		this.task = InMemStorage.getInstance().getTask(remindIndex);
		this.period = period;
		commandLogger.log(Level.INFO, "Remind Command Created");
	}
	
	public CommandRemind(Done task,String period){
		super(CommandType.REMIND, true);
		this.task = task;
		this.period = period;
		commandLogger.log(Level.INFO, "Remind Command Created");
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
