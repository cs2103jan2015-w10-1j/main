package com.done.command;

import java.util.ArrayList;

import com.done.logic.Logic;
import com.done.model.Done;
import com.done.parser.CommandParser;
import com.done.storage.InMemStorage;

public class CommandAdd extends Command {

	private ArrayList<String> content;
	private Done task;

	public CommandAdd(Done task) {
		super(CommandType.ADD, true);
		this.task = task;
	}

	public ArrayList<String> getContent() {
		return this.content;
	}

	public void setContent(String content) {
		CommandParser cmdParser = CommandParser.getInstance();
		this.content = cmdParser.getCommandContent(content);
	}

	@Override
	public void execute() throws Exception {
		if (this.task != null) {
			InMemStorage inMemStorage = InMemStorage.getInstance();
			inMemStorage.store(task);
		} else {
			throw new Exception("TaskNullException");
		}

	}

	@Override
	public void undo() {
		CommandDelete command = new CommandDelete(task);
		command.execute();
	}

}
