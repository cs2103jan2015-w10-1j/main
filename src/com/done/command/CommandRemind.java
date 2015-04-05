package com.done.command;

import com.done.model.Done;

public class CommandRemind extends Command {
	
	private Done task;
	//to add a variable indicating time, in the correct type
	
	public CommandRemind(Done task){
		super(CommandType.REMIND, true);
		this.task = task;
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
