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

public class UIControllerStub extends UIController {
	@FXML 
	private TextField commandField;
	
	@FXML
	private TableView<Done> tableViewTasks;
	
	private Logic mainLogic;
	private CommandType prevCommandType = null;
	
	private static final int ARRAY_DELETE_OFFSET = 1;
	private static final String EMPTY_STRING = "";
	private static final String SPACE = " ";
	private static final String SHOWADD_SUCCESS_MESSAGE = "%1$s added to the list";
	private static final String SHOWDELETE_ERROR_MESSAGE = "Error: Invalid delete";
	private static final String SHOWDELETE_SUCCESS_MESSAGE = "%1$s deleted";
	private static final String SHOWUNDO_ERROR_MESSAGE = "Error: No recent command available";
	private static final String SHOWUNDO_SUCCESS_MESSAGE = "Undo %1$s %2$s";
	private static final String SHOWINVALIDCOMMAND_ERROR_MESSAGE = "Error: Invalid command";
	
	public UIControllerStub(){
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
		if(!userCommand.equals(EMPTY_STRING)){
			executeCommand(userCommand);	
		}
	}
	
	private void executeCommand(String userCommand){
		
		/* TODO: Commands are temporary and for skeletal purpose
		* some will be removed or changed when UI has been updated
		*/
		CommandType currCommandType = mainLogic.getCmdType(userCommand);
		String commandContents = mainLogic.getCmdContent(userCommand);
		
		switch(currCommandType){
			case ADD:
				/*Done addedTask = mainLogic.getTask(userCommand);
				assert addedTask != null;
				showAdd(addedTask);*/
				
				mainLogic.addTask(commandContents);
				showAdd(commandContents);
				
				break;
			case DISPLAY:
				/* TODO: Enable display command to fit in parameters such that
				 * it will be able to display <all> <floating> <timed> <deadline> 	
				 */
				break;
			case DELETE:
				/*Done deletedTask = mainLogic.getTask(userCommand);
				showDelete(deletedTask);*/
				String taskName = mainLogic.deleteTask(Integer.parseInt(commandContents) - ARRAY_DELETE_OFFSET);
				showDelete(taskName);
				break;
			case LOAD:
				mainLogic.storeTo(commandContents);
				display();
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
				Notifications.create().text(String.format(SHOWUNDO_SUCCESS_MESSAGE, prevCommandType, undoneTitle)).showInformation();
			}
			else{
				Notifications.create().text(String.format(SHOWUNDO_SUCCESS_MESSAGE, prevCommandType, EMPTY_STRING)).showInformation();
			}
			prevCommandType = null;
		}
		else{
			Notifications.create().text(SHOWUNDO_ERROR_MESSAGE).showError();
		}
	}

	private void showInvalidCommand() {
		Notifications.create().text(SHOWINVALIDCOMMAND_ERROR_MESSAGE).showError();
	}

	private void showDelete(String task) {
		if(task != null){
			//String deletedTitle = task.getTitle();
			Notifications.create().text(String.format(SHOWDELETE_SUCCESS_MESSAGE, task)).showInformation();
			display();
		}
		else{
			Notifications.create().text(SHOWDELETE_ERROR_MESSAGE).showError();
		}
	}

	private void showAdd(String task) {
		//String addedTitle = task.getTitle();
		Notifications.create().text(String.format(SHOWADD_SUCCESS_MESSAGE, task)).showInformation();
		display();
	}

	private void display() {
		List<Done> tasks = mainLogic.getTasks();
		ObservableList<Done> tableTasks = FXCollections.observableArrayList(tasks);
		tableViewTasks.setItems(tableTasks);
		tableViewTasks.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
		tableViewTasks.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("title"));
		tableViewTasks.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("title"));
	}

}
