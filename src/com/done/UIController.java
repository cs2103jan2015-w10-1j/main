package com.done;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import org.controlsfx.control.Notifications;

import com.done.logic.Logic;
import com.done.parser.CommandParser;
import com.done.parser.CommandParser.CommandType;
import com.done.parser.CommandUtils;

public class UIController {
	@FXML 
	private TextField commandField;
	
	@FXML
	private TableView<Done> tableViewTasks;
	
	private CommandParser cmdParser;
	private Logic mainLogic;
	private final int ARRAY_DELETE_OFFSET = 1;
	
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
		
		CommandType commandType = cmdParser.getCommandType(userCommand);
		
		/* TODO: Commands are temporary and for skeletal purpose
		* some will be removed or changed when UI has been updated
		*/
		switch(commandType){
			case ADD:
				String task = CommandUtils.removeFirstWord(userCommand);
				mainLogic.addFloating(task);
				Notifications.create().text(task + " added to list").showInformation();
				display();
				break;
			case DISPLAY:
				/* TODO: Enable display command to fit in parameters such that
				 * it will be able to display <all> <floating> <timed> <deadline> 	
				 */
				break;
			case DELETE:
				int deleteIndex = Integer.parseInt(CommandUtils.removeFirstWord(userCommand));
				if(mainLogic.isExistingTask(deleteIndex)){
					mainLogic.delete(deleteIndex - ARRAY_DELETE_OFFSET);
					Notifications.create().text(deleteIndex + " deleted").showInformation();
					display();
				}
				else{
					Notifications.create().text("Invalid delete index").showError();
				}
				break;
			case EXIT:
				System.exit(0);
				break;
			default:
				Notifications.create().text("Invalid command").showError();
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

}
