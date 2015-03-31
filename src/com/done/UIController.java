package com.done;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import org.controlsfx.control.Notifications;

import com.done.logic.LogicFacade;
import com.done.model.Done;
import com.done.command.Command.CommandType;
import com.done.result.ExecutionResult;
import com.done.observer.Observer;

public class UIController implements Observer {
	@FXML 
	private TextField commandField;
	
	@FXML
	private TableView<Done> tableViewTasks;
	
	private LogicFacade logicFacade;
	private CommandType prevCommandType = null;
	
	private static final String EMPTY_STRING = "";
	private static final String SHOWADD_SUCCESS_MESSAGE = "%1$s added to the list";
	private static final String SHOWADD_ERROR_MESSAGE = "Error: Adding task not successful";
	private static final String SHOWDELETE_ERROR_MESSAGE = "Error: Invalid delete";
	private static final String SHOWDELETE_SUCCESS_MESSAGE = "%1$s deleted";
	private static final String SHOWEDIT_SUCCESS_MESSAGE = "Task edited to %1$s";
	private static final String SHOWEDIT_ERROR_MESSAGE = "Error: Editing task not successful";
	private static final String SHOWCLEAR_SUCCESS_MESSAGE = "All tasks cleared";
	private static final String SHOWCLEAR_ERROR_MESSAGE = "Error: Clearing tasks not successful";
	private static final String SHOWLOAD_SUCCESS_MESSAGE = "%1$s loaded";
	private static final String SHOWLOAD_ERROR_MESSAGE = "Error: File load not successful";
	private static final String SHOWREMIND_SUCCESS_MESSAGE = "Set reminder for %1$s";
	private static final String SHOWREMIND_ERROR_MESSAGE = "Error: Reminder cannot be set for this task";
	private static final String SHOWUNDO_ERROR_MESSAGE = "Error: No recent command available";
	private static final String SHOWUNDO_SUCCESS_MESSAGE = "Undo %1$s %2$s";
	private static final String SHOWINVALIDCOMMAND_ERROR_MESSAGE = "Error: Invalid command";
	private static final String UPDATEREMINDER_MESSAGE = "Reminder for %1$s";
	
	public UIController(){
		logicFacade = new LogicFacade();
	}

	@FXML
	public void initialize() {
		display();
		UIController uiController = new UIController();
		logicFacade.addUI(uiController);
		commandField.setOnAction((event) -> {
			processInput();
		});
	}
	
	private void processInput(){
		String userCommand = commandField.getText();
		if(!userCommand.equals(EMPTY_STRING)){
			processCommand(userCommand);	
		}
		
	}
	
	private void processCommand(String userCommand){
		
		ExecutionResult executionResult = logicFacade.getExecutionResult(userCommand);
		CommandType currCommandType = executionResult.getCommandType();
		String commandContent = executionResult.getCommandContent();
		
		
		switch(currCommandType){
			case ADD:				
				showAdd(executionResult.isSuccessful(), commandContent);
				break;
			case DELETE:				
				showDelete(executionResult.isSuccessful(), commandContent);
				break;
			case EDIT:
				showEdit(executionResult.isSuccessful(), commandContent);
				break;
			case LOAD:
				showLoad(executionResult.isSuccessful(), commandContent);
				break;
			case CLEAR:
				showClear(executionResult.isSuccessful());
				break;
			case SEARCH:
				displaySearches();
				break;
			case REMIND:
				showRemind(executionResult.isSuccessful(), commandContent);
			case UNDO:
				showUndo(executionResult.isSuccessful(), commandContent);
				break;
			case DONE:
				// @peeraya: you can implement the showDone();
				break;
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
	
	public void updateReminder(int taskId){
		String reminder = logicFacade.getReminder(taskId);
		Notifications.create().title("Task Reminder").text(String.format(UPDATEREMINDER_MESSAGE, reminder)).showWarning();
	}

	private void showAdd(boolean isSuccessful, String commandContent) {
		if(isSuccessful){
			Notifications.create().text(String.format(SHOWADD_SUCCESS_MESSAGE, commandContent)).showInformation();
		}
		else{
			Notifications.create().text(SHOWADD_ERROR_MESSAGE).showError();
		}
		display();
	}
	
	private void showDelete(boolean isSuccessful, String commandContent) {
		if(isSuccessful){
			Notifications.create().text(String.format(SHOWDELETE_SUCCESS_MESSAGE, commandContent)).showInformation();
			display();
		}
		else{
			Notifications.create().text(SHOWDELETE_ERROR_MESSAGE).showError();
		}
	}
	
	private void showEdit(boolean isSuccessful, String commandContent) {
		if(isSuccessful){
			Notifications.create().text(String.format(SHOWEDIT_SUCCESS_MESSAGE, commandContent)).showInformation();
		}
		else{
			Notifications.create().text(SHOWEDIT_ERROR_MESSAGE).showError();
		}
		display();
	}
	
	private void showLoad(boolean isSuccessful, String commandContent){
		if(isSuccessful){
			Notifications.create().text(String.format(SHOWLOAD_SUCCESS_MESSAGE, commandContent+".json")).showInformation();
		}
		else{
			Notifications.create().text(SHOWLOAD_ERROR_MESSAGE).showError();
		}
		display();
	}
	
	private void showClear(boolean isSuccessful) {
		if(isSuccessful){
			Notifications.create().text(SHOWCLEAR_SUCCESS_MESSAGE).showInformation();
		}
		else{
			Notifications.create().text(SHOWCLEAR_ERROR_MESSAGE).showError();
		}
		display();
	}
	
	private void showRemind(boolean isSuccessful, String commandContent){
		if(isSuccessful){
			Notifications.create().text(String.format(SHOWREMIND_SUCCESS_MESSAGE, commandContent)).showInformation();
		}
		else{
			Notifications.create().text(SHOWREMIND_ERROR_MESSAGE).showError();
		}
		display();
	}

	private void showUndo(boolean isSuccessful, String commandContent)  {
		if(isSuccessful){
			assert prevCommandType!=null;
			if(commandContent!=null){
				Notifications.create().text(String.format(SHOWUNDO_SUCCESS_MESSAGE, prevCommandType, commandContent)).showInformation();
			}
			else{
				Notifications.create().text(String.format(SHOWUNDO_SUCCESS_MESSAGE, prevCommandType, EMPTY_STRING)).showInformation();
			}
			prevCommandType = null;
		}
		else{
			Notifications.create().text(SHOWUNDO_ERROR_MESSAGE).showError();
		}
		
		display();
		
	}

	private void showInvalidCommand() {
		Notifications.create().text(SHOWINVALIDCOMMAND_ERROR_MESSAGE).showError();
	}
	

	private void display() {
		List<Done> tasks = logicFacade.getTasks();
		ObservableList<Done> tableTasks = FXCollections.observableArrayList(tasks);
		tableViewTasks.setItems(tableTasks);
		tableViewTasks.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
		tableViewTasks.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("title"));
		tableViewTasks.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("startTime"));
		tableViewTasks.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("endTime"));
	}
	
	private void displaySearches() {
		List<Done> tasks = logicFacade.getSearchResult();
		ObservableList<Done> tableTasks = FXCollections.observableArrayList(tasks);
		tableViewTasks.setItems(tableTasks);
		tableViewTasks.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
		tableViewTasks.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("title"));
		tableViewTasks.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("startTime"));
		tableViewTasks.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("endTime"));
	}

}
