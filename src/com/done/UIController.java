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
import com.done.model.Done;
import com.done.parser.CommandParser;
import com.done.parser.CommandParser.CommandType;
import com.done.parser.CommandUtils;

public class UIController {
	@FXML 
	private TextField commandField;
	
	@FXML
	private TableView<Done> tableViewTasks;
	
	private Logic mainLogic;
	private final int ARRAY_DELETE_OFFSET = 1;
	
	public UIController(){
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
		CommandType currCommandType = mainLogic.getCmdType(userCommand);
		Done currTask = mainLogic.getTask(userCommand);
		executeCommand(currCommandType, currTask);
	}
	
	public void executeCommand(CommandType commandType, Done task){
		
		/* TODO: Commands are temporary and for skeletal purpose
		* some will be removed or changed when UI has been updated
		*/
		switch(commandType){
			case ADD:
				String addedTitle = task.getTitle();
				Notifications.create().text(addedTitle + " added to list").showInformation();
				display();
				break;
			case DISPLAY:
				/* TODO: Enable display command to fit in parameters such that
				 * it will be able to display <all> <floating> <timed> <deadline> 	
				 */
				break;
			case DELETE:
				if(task != null){
					String deletedTitle = task.getTitle();
					Notifications.create().text(deletedTitle + " deleted").showInformation();
					display();
				}
				else{
					Notifications.create().text("Invalid delete").showError();
				}
				break;
			case UNDO:
				
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
