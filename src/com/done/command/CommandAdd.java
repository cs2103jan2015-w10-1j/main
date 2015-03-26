package com.done.command;

import java.util.ArrayList;

import com.done.logic.Logic;
import com.done.parser.ParserUtils;

public class CommandAdd extends Command {
	
	private ArrayList<String> content;

	public CommandAdd(String content){
		super(CommandType.ADD);
		this.content = ParserUtils.processContent(content);
	}
	
	public ArrayList<String> getContent(){
		return this.content;
	}
	
	public void setContent(String content){
		this.content = ParserUtils.processContent(content);
	}
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		// JERRY: I HAVE MODIFIED THIS PART OF THE CODE
		for (int i = 0; i < content.size(); i++) {
			if(content.get(i).equals("e")){
				
			}
		}

	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub

	}

}
