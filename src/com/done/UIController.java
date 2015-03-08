package com.done;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.controlsfx.control.Notifications;

import com.done.logic.Logic;
import com.done.parser.CommandParser;
import com.done.parser.CommandUtils;
import com.done.parser.CommandParser.CommandType;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/* Interface is basically TextBuddy style for now.
 * TODO: Update view to ListView
 * Return view from List to ListView
*/

public class UIController {
	@FXML 
	private TextField commandField;
	
	@FXML
	private TextArea mainOutput;
	
	@FXML
	private TableView<Done> tableViewTasks;
	
	private CommandParser cmdParser;
	private Logic mainLogic;
	
	public UIController(){
		cmdParser = new CommandParser();
		mainLogic = new Logic();
	}

	@FXML
	public void initialize() {
		display();
		
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
		
		/* TODO: Commands are temporary and for skeletal purpose
		* some will be removed or changed when UI has been updated
		*/
		switch(commandType){
			case ADD:
				mainLogic.addFloating(task);
				//mainOutput.appendText(task + "\n");
				//mainOutput.setText(task + " added to list");
				Notifications.create().text(task + " added to list").showInformation();
				//waitAndClear(2500);
				display();
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

	private void display() {
		List<Done> tasks = mainLogic.getTasks();
		ObservableList<Done> tableTasks = FXCollections.observableArrayList(tasks);
		tableViewTasks.setItems(tableTasks);
		tableViewTasks.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
		tableViewTasks.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("title"));
	}
	
	
	// For fun experimental purpose only
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
