//@author A0088821X
package com.done;

import java.util.Collections;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

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
	
	private static final String EMPTY_STRING = "";
	private static final String SHOWADD_SUCCESS_MESSAGE = "%1$s added to the list";
	private static final String SHOWADD_ERROR_MESSAGE = "Error: Adding task not successful";
	private static final String SHOWDELETE_ERROR_MESSAGE = "Error: Invalid delete";
	private static final String SHOWDELETE_SUCCESS_MESSAGE = "%1$s deleted";
	private static final String SHOWEDIT_SUCCESS_MESSAGE = "Task %1$s";
	private static final String SHOWEDIT_ERROR_MESSAGE = "Error: Editing task not successful";
	private static final String SHOWCLEAR_SUCCESS_MESSAGE = "All tasks cleared";
	private static final String SHOWCLEAR_ERROR_MESSAGE = "Error: Clearing tasks not successful";
	private static final String SHOWMOVE_SUCCESS_MESSAGE = "Moved from %1$s";
	private static final String SHOWMOVE_ERROR_MESSAGE = "Error: Moving task not successful";
	private static final String SHOWSEARCH_SUCCESS_MESSAGE = "Showing search for: %1$s";
	private static final String SHOWSEARCH_ERROR_MESSAGE = "Error: Search not successful";
	private static final String SHOWLOAD_SUCCESS_MESSAGE = "%1$s.json loaded";
	private static final String SHOWLOAD_ERROR_MESSAGE = "Error: File load not successful";
	private static final String SHOWSHOWALL_SUCCESS_MESSAGE = "Showing all tasks";
	private static final String SHOWRECUR_SUCCESS_MESSAGE = "%1$s";
	private static final String SHOWRECUR_ERROR_MESSAGE = "Error: Recurrence cannot be set for this task";
	private static final String SHOWREMIND_SUCCESS_MESSAGE = "Set reminder for %1$s";
	private static final String SHOWREMIND_ERROR_MESSAGE = "Error: Reminder cannot be set for this task";
	private static final String SHOWUNDO_ERROR_MESSAGE = "Error: No recent command available";
	private static final String SHOWUNDO_SUCCESS_MESSAGE = "Undo %1$s";
	private static final String SHOWDONE_ERROR_MESSAGE = "Error: Invalid attempt to mark a task as done";
	private static final String SHOWDONE_SUCCESS_MESSAGE = "%1$s marked as done";
	private static final String SHOWCLEARDONE_ERROR_MESSAGE = "Error: Invalid attempt to clear tasks marked as done";
	private static final String SHOWCLEARDONE_SUCCESS_MESSAGE = "All done tasks cleared";
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
			case MOVE:
				showMove(executionResult.isSuccessful(), commandContent);
				break;
			case SEARCH:
				showSearch(executionResult.isSuccessful(), commandContent);
				break;
			case SHOWALL:
				showShowAll();
				break;
			case RECUR:
				showRecur(executionResult.isSuccessful(), commandContent);
				break;
			case REMIND:
				showRemind(executionResult.isSuccessful(), commandContent);
				break;
			case UNDO:
				showUndo(executionResult.isSuccessful(), commandContent);
				break;
			case DONE:
				showDone(executionResult.isSuccessful(), commandContent);
				break;
			case CLEARDONE:
				showClearDone(executionResult.isSuccessful());
				break;
			case EXIT:
				System.exit(0);
				break;
			default:	
				showInvalidCommand();
				break;
		}
		commandField.clear();
	}
	
	public void updateReminder(int taskId){
		String reminder = logicFacade.getReminder(taskId);
		Notifications.create().title("Task Reminder").text(String.format(UPDATEREMINDER_MESSAGE, reminder)).showWarning();
		highlightReminderRow(taskId);
	}

	private void highlightReminderRow(int taskId) {
		tableViewTasks.setRowFactory(new Callback<TableView<Done>, TableRow<Done>>() {
	        @Override
	        public TableRow<Done> call(TableView<Done> tableView) {
	            final TableRow<Done> row = new TableRow<Done>() {
	                @Override
	                protected void updateItem(Done task, boolean empty){
	                    super.updateItem(task, empty);
	                    /*if(task == null || empty){
	                    	 getStyleClass().removeAll(Collections.singleton("reminderRow"));
	                    }*/
	                    //else{
		                    if(task.getId() == taskId){
		                    	if (!getStyleClass().contains("reminderRow")) {
		                            getStyleClass().add("reminderRow");
		                    	}
		                //    }
		                    /*else{
		                    	 getStyleClass().removeAll(Collections.singleton("reminderRow"));
		                    }*/
	                    }
	                   
	                }
	            };
	            return row;
	        }
	    });
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
		}
		else{
			Notifications.create().text(SHOWDELETE_ERROR_MESSAGE).showError();
		}
		display();
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
			Notifications.create().text(String.format(SHOWLOAD_SUCCESS_MESSAGE, commandContent)).showInformation();
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
	
	private void showMove(boolean isSuccessful, String commandContent){
		if(isSuccessful){
			Notifications.create().text(String.format(SHOWMOVE_SUCCESS_MESSAGE, commandContent)).showInformation();
		}
		else{
			Notifications.create().text(SHOWMOVE_ERROR_MESSAGE).showError();
		}
		display();
	}
	
	private void showSearch(boolean isSuccessful, String commandContent){
		if(isSuccessful){
			Notifications.create().text(String.format(SHOWSEARCH_SUCCESS_MESSAGE, commandContent)).showInformation();
		}
		else{	
			Notifications.create().text(SHOWSEARCH_ERROR_MESSAGE).showError();
		}
		displaySearches();
	}
	
	private void showShowAll(){
		Notifications.create().text(SHOWSHOWALL_SUCCESS_MESSAGE).showInformation();
		display();
	}
	
	private void showRecur(boolean isSuccessful, String commandContent){
		if(isSuccessful){
			Notifications.create().text(String.format(SHOWRECUR_SUCCESS_MESSAGE, commandContent)).showInformation();
		}
		else{
			Notifications.create().text(SHOWRECUR_ERROR_MESSAGE).showError();
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
			Notifications.create().text(String.format(SHOWUNDO_SUCCESS_MESSAGE, commandContent)).showInformation();
		}
		else{
			Notifications.create().text(SHOWUNDO_ERROR_MESSAGE).showError();
		}
		display();
		
	}
	
	private void showDone(boolean isSuccessful, String commandContent) {
		if(isSuccessful){
			Notifications.create().text(String.format(SHOWDONE_SUCCESS_MESSAGE, commandContent)).showInformation();
		}
		else{
			Notifications.create().text(SHOWDONE_ERROR_MESSAGE).showError();
		}
		display();
	}
	
	private void showClearDone(boolean isSuccessful) {
		if(isSuccessful){
			Notifications.create().text(SHOWCLEARDONE_SUCCESS_MESSAGE).showInformation();
		}
		else{
			Notifications.create().text(SHOWCLEARDONE_ERROR_MESSAGE).showError();
		}
		
		display();
	}

	private void showInvalidCommand() {
		Notifications.create().text(SHOWINVALIDCOMMAND_ERROR_MESSAGE).showError();
	}
	

	private void display() {
		createDisplayTable();
		strikethroughDoneRows();
	}

	private void createDisplayTable() {
		List<Done> tasks = logicFacade.getTasks();
		ObservableList<Done> tableTasks = FXCollections.observableArrayList(tasks);		
		tableViewTasks.setItems(tableTasks);
		buildTableColumns();
		
		/*tableViewTasks.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
		tableViewTasks.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("title"));
		tableViewTasks.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("startTime"));
		tableViewTasks.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("endTime"));
		tableViewTasks.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("endTime"));
		*/
	}

	private void buildTableColumns() {
		TableColumn<Done,Integer> idCol = new TableColumn<Done,Integer>("No.");
		idCol.setCellValueFactory(new PropertyValueFactory("id"));
		idCol.setPrefWidth(50.0);
		idCol.setResizable(false);
		TableColumn<Done,String> titleCol = new TableColumn<Done,String>("Task");
		titleCol.setCellValueFactory(new PropertyValueFactory("title"));
		titleCol.setPrefWidth(280.0);
		titleCol.setResizable(false);
		TableColumn<Done,String> startTimeCol = new TableColumn<Done,String>("Start");
		startTimeCol.setCellValueFactory(new PropertyValueFactory("startTime"));
		startTimeCol.setPrefWidth(65.0);
		startTimeCol.setResizable(false);
		TableColumn<Done,String> endTimeCol = new TableColumn<Done,String>("End");
		endTimeCol.setCellValueFactory(new PropertyValueFactory("endTime"));
		endTimeCol.setCellFactory(new Callback<TableColumn<Done,String>,TableCell<Done,String>>(){
			@Override
		    public TableCell<Done, String> call(TableColumn<Done, String> tableColumn) {
		        final TableCell<Done, String> cell = new TableCell<Done, String>() {
		            @Override
		            protected void updateItem(final String item, boolean empty)
		            {
		                super.updateItem(item, empty);
		                if(empty){
		                	this.setText(EMPTY_STRING);
		                }else{
		                	if(item != null && item.length() == 5){
		                		this.setText(item); 
		                	} else{
		                		this.setText(EMPTY_STRING);
		                	}
		                }
		            }
		        };
		        return cell;
		    }
		});
		endTimeCol.setPrefWidth(65.0);
		endTimeCol.setResizable(false);
		TableColumn<Done,String> deadlineCol = new TableColumn<Done,String>("Deadline");
		deadlineCol.setCellValueFactory(new PropertyValueFactory("endTime"));
		deadlineCol.setCellFactory(new Callback<TableColumn<Done,String>,TableCell<Done,String>>(){
			@Override
		    public TableCell<Done, String> call(TableColumn<Done, String> tableColumn) {
		        final TableCell<Done, String> cell = new TableCell<Done, String>() {
		            @Override
		            protected void updateItem(final String item, boolean empty)
		            {
		                super.updateItem(item, empty);
		                if(empty){
		                	this.setText(EMPTY_STRING);
		                }else{
		                	if(item != null && item.length() > 5){
		                		this.setText(item); 
		                	} else{
		                		this.setText(EMPTY_STRING);
		                	}
		                }
		            }
		        };
		        return cell;
		    }
		});
		deadlineCol.setPrefWidth(128.0);
		deadlineCol.setResizable(false);
		tableViewTasks.getColumns().setAll(idCol, titleCol, startTimeCol, endTimeCol, deadlineCol);
	}
	
	private void displaySearches() {
		createDisplaySearchesTable();
		strikethroughDoneRows();
	}

	private void createDisplaySearchesTable() {
		List<Done> tasks = logicFacade.getSearchResult();
		ObservableList<Done> tableTasks = FXCollections.observableArrayList(tasks);
		tableViewTasks.setItems(tableTasks);
		buildTableColumns();
	}
	
	private void strikethroughDoneRows() {
		tableViewTasks.setRowFactory(new Callback<TableView<Done>, TableRow<Done>>() {
	        @Override
	        public TableRow<Done> call(TableView<Done> tableView) {
	            final TableRow<Done> row = new TableRow<Done>() {
	                @Override
	                protected void updateItem(Done task, boolean empty){
	                    super.updateItem(task, empty);
	                    if(task == null || empty){
	                    	 getStyleClass().removeAll(Collections.singleton("doneRow"));
	                    }
	                    else{
		                    if(task.isCompleted()){
		                    	if (!getStyleClass().contains("doneRow")) {
		                            getStyleClass().add("doneRow");
		                    	}
		                    }
		                    else{
		                    	 getStyleClass().removeAll(Collections.singleton("doneRow"));
		                    }
	                    }
	                   
	                }
	            };
	            return row;
	        }
	    });
		if(!tableViewTasks.getColumns().get(0).getStyleClass().contains("firstColumn")){
			tableViewTasks.getColumns().get(0).getStyleClass().add("firstColumn");
		}
	}
}
