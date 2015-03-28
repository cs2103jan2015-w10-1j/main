package com.done.command;

import com.done.storage.InMemStorage;

public class CommandClear extends Command {
	
	public CommandClear(){
		super(CommandType.CLEAR);
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		InMemStorage.getInstance().delete(0, true);

	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub

	}

}
