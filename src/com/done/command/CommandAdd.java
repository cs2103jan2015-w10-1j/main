package com.done.command;

import java.util.ArrayList;

import com.done.logic.Logic;
import com.done.model.Done;
import com.done.parser.CommandParser;
import com.done.storage.InMemStorage;

public class CommandAdd extends Command {
	
	private ArrayList<String> content;
	// task to be added through this command !
	private Done task;

	
/*	public CommandAdd(String content){
		super(CommandType.ADD);
		this.content = ParserUtils.processContent(content);
	}*/
	
	public CommandAdd(Done task){
		//what does this super does from the parent class?
		super(CommandType.ADD, true);
		this.task = task;
	}
	
	public ArrayList<String> getContent(){
		return this.content;
	}
	
	public void setContent(String content){
		CommandParser cmdParser = new CommandParser();
		this.content = cmdParser.getCommandContent(content);
	}
	@Override
	public void execute() {
		InMemStorage inMemStorage = InMemStorage.getInstance();
		inMemStorage.store(task);
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		InMemStorage inMemStorage = InMemStorage.getInstance();
		// yes just delete it !
		inMemStorage.delete(task.getId(), false);
	}

}
