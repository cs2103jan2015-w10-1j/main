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
import com.done.parser.CommandParser.CommandType;
import com.done.result.ExecutionResult;

public class UIController {
	@FXML 
	private TextField commandField;
	
	@FXML
	private TableView<Done> tableViewTasks;
	
	private LogicFacade logicFacade;
	private CommandType prevCommandType = null;
	
	private static final int ARRAY_DELETE_OFFSET = 1;
	private static final String EMPTY_STRING = "";
	//private static final String SPACE = " ";
	private static final String SHOWADD_SUCCESS_MESSAGE = "%1$s added to the list";
	private static final String SHOWADD_ERROR_MESSAGE = "Error: Adding task not successful";
	private static final String SHOWDELETE_ERROR_MESSAGE = "Error: Invalid delete";
	private static final String SHOWDELETE_SUCCESS_MESSAGE = "%1$s deleted";
	private static final String SHOWUNDO_ERROR_MESSAGE = "Error: No recent command available";
	private static final String SHOWUNDO_SUCCESS_MESSAGE = "Undo %1$s %2$s";
	private static final String SHOWINVALIDCOMMAND_ERROR_MESSAGE = "Error: Invalid command";
	
	public UIController(){
		logicFacade = new LogicFacade();
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
			processCommand(userCommand);	
		}
	}
	
	private void processCommand(String userCommand){
		
		/* TODO: Commands are temporary and for skeletal purpose
		* some will be removed or changed when UI has been updated
		*/
		
		ExecutionResult result = logicFacade.getExecutionResult(userCommand);
		CommandType currCommandType = result.getCommandType();
		String commandContent = result.getCommandContent();
		
		switch(currCommandType){
			case ADD:
				/*Done addedTask = mainLogic.getTask(userCommand);
				assert addedTask != null;
				showAdd(addedTask);
				
				mainLogic.addTask(commandContents);
				showAdd(commandContents);*/
				
				showAdd(result.isSuccessful(), commandContent);
				break;
			case DISPLAY:
				/* TODO: Enable display command to fit in parameters such that
				 * it will be able to display <all> <floating> <timed> <deadline> 	
				 */
				break;
			case DELETE:
				/*Done deletedTask = mainLogic.getTask(userCommand);
				showDelete(deletedTask);
				String taskName = mainLogic.deleteTask(Integer.parseInt(commandContents) - ARRAY_DELETE_OFFSET);
				showDelete(taskName);*/
				
				showDelete(result.isSuccessful(), commandContent);
				break;
			case LOAD:
				showLoad(result.isSuccessful(), commandContent);
				break;
			case UNDO:
				showUndo(result.isSuccessful(), commandContent);
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
	
	private void showLoad(boolean isSuccessful, String commandContent){
		display();
	}

	private void showUndo(boolean isSuccessful, String commandContent) {
		if(prevCommandType!=null){
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

}
