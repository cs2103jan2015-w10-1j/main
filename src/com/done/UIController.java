package com.done;

import java.util.Timer;
import java.util.TimerTask;

import com.done.logic.Logic;
import com.done.parser.CommandParser;
import com.done.parser.CommandUtils;
import com.done.parser.CommandParser.CommandType;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/* Interface is basically TextBuddy style for now.
 * TODO: Update view to ListView
 * Return view from List to ListView
*/

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
		
		/** TODO: Commands are temporary and for skeletal purpose
		* some will be removed or changed when UI has been updated
		*/
		switch(commandType){
			case ADD:
				mainLogic.add(task);
				//mainOutput.appendText(task + "\n");
				mainOutput.setText(task + " added to list");
				waitAndClear(2500);
				break;
			case DISPLAY:
				String output = mainLogic.display();
				mainOutput.setText(output);
				break;
			case CLEAR:
				mainOutput.clear();
				//mainOutput.setText("Output cleared");
				//waitAndClear(2500);
				break;
			case EXIT:
				System.exit(0);
				break;
			default:
				break;
		}
		
		commandField.clear();
		
		
	}

	private void waitAndClear(long millis) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						mainOutput.clear();
						
					}
				});
				
			}
		}, millis);
	}
	
	

}
