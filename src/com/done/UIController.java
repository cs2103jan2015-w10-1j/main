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
	private CommandType prevCommandType = null;
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
		executeCommand(userCommand);
	}
	
	public void executeCommand(String userCommand){
		
		/* TODO: Commands are temporary and for skeletal purpose
		* some will be removed or changed when UI has been updated
		*/
		CommandType currCommandType = mainLogic.getCmdType(userCommand);
		
		switch(currCommandType){
			case ADD:
				Done addedTask = mainLogic.getTask(userCommand);
				showAdd(addedTask);
				break;
			case DISPLAY:
				/* TODO: Enable display command to fit in parameters such that
				 * it will be able to display <all> <floating> <timed> <deadline> 	
				 */
				break;
			case DELETE:
				Done deletedTask = mainLogic.getTask(userCommand);
				showDelete(deletedTask);
				break;
			case UNDO:
				Done undoneTask = mainLogic.getTask(userCommand);
				showUndo(undoneTask);
			case EXIT:
				System.exit(0);
				break;
			default:
				showInvalidCommand();
				break;
		}
		prevCommandType = currCommandType;
		commandField.clear();
	}

	private void showUndo(Done task) {
		if(prevCommandType!=null){
			if(task!=null){
				String undoneTitle = task.getTitle();
				Notifications.create().text("Undo " + prevCommandType + " " + undoneTitle).showInformation();
			}
			else{
				Notifications.create().text("Undo " + prevCommandType).showInformation();
			}
			prevCommandType = null;
		}
		else{
			Notifications.create().text("No recent command available").showError();
		}
	}

	private void showInvalidCommand() {
		Notifications.create().text("Invalid command").showError();
	}

	private void showDelete(Done task) {
		if(task != null){
			String deletedTitle = task.getTitle();
			Notifications.create().text(deletedTitle + " deleted").showInformation();
			display();
		}
		else{
			Notifications.create().text("Invalid delete").showError();
		}
	}

	private void showAdd(Done task) {
		String addedTitle = task.getTitle();
		Notifications.create().text(addedTitle + " added to list").showInformation();
		display();
	}

	private void display() {
		List<Done> tasks = mainLogic.getTasks();
		ObservableList<Done> tableTasks = FXCollections.observableArrayList(tasks);
		tableViewTasks.setItems(tableTasks);
		tableViewTasks.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
		tableViewTasks.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("title"));
	}

}
