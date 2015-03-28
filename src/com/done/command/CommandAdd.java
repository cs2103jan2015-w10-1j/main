package com.done.command;

import java.util.ArrayList;

import com.done.logic.Logic;
import com.done.model.Done;
import com.done.parser.ParserUtils;
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
		super(CommandType.ADD);
		this.task = task;
	}
	
	public ArrayList<String> getContent(){
		return this.content;
	}
	
	public void setContent(String content){
		this.content = ParserUtils.processContent(content);
	}
	@Override
	public void execute() {
		// get the instance of ur storage
		InMemStorage inMemStorage = InMemStorage.getInstance();
		// store task into ur storage
		inMemStorage.store(task);
		// gao ding

	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub

	}

}
