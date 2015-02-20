package com.done;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MyController {
	@FXML 
	private TextField commandField;
	
	@FXML
	private TextArea mainOutput;
	
	public MyController(){
		
	}

	@FXML
	public void initialize() {
		Utils utils = new Utils();
		
		commandField.setOnAction((event) -> {
			String command = utils.getFirstWord(commandField.getText());
			String task = utils.removeFirstWord(commandField.getText());
			
			if(command.equals("add")){
				mainOutput.appendText(task + "\n");
			}else if(command.equals("clear")){
				mainOutput.clear();
			}
			
			
			commandField.clear();
			
		});
		
	}
	
	

}
