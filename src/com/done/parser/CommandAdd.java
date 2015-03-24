package com.done.parser;

import java.util.ArrayList;

import com.done.logic.Logic;
import com.done.parser.CommandUtils;

public class CommandAdd extends Command {
	
	private ArrayList<String> content;

	public CommandAdd(String content){
		super(CommandType.ADD);
		this.content = CommandUtils.processContent(content);
	}
	
	public ArrayList<String> getContent(){
		return this.content;
	}
	
	public void setContent(String content){
		this.content = CommandUtils.processContent(content);
	}
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub

	}

}
