package com.done;

import com.done.logic.Logic;
import com.done.parser.CommandParser;
import com.done.parser.CommandUtils;
import com.done.parser.CommandParser.CommandType;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class UIController {
	@FXML 
	private TextField commandField;
	
	@FXML
	private TextArea mainOutput;
	
	private CommandParser cmdParser;
	private Logic mainLogic;
	
	public UIController(){
		cmdParser = new CommandParser();
		mainLogic = new Logic();
	}

	@FXML
	public void initialize() {
		
		commandField.setOnAction((event) -> {
			processInput();
		});
		
	}
	
	public void processInput(){
		String userCommand = commandField.getText();
		executeCommand(userCommand);
	}
	
	public void executeCommand(String userCommand){
		
		String task = CommandUtils.removeFirstWord(userCommand);
		CommandType commandType = cmdParser.getCommandType(userCommand);
		
		switch(commandType){
			case ADD:
				mainLogic.add(task);
				mainOutput.appendText(task + "\n");
				break;
			case CLEAR:
				mainOutput.clear();
				break;
			case EXIT:
				System.exit(0);
				break;
			default:
				break;
		}
		
		commandField.clear();
	}
	
	

}
