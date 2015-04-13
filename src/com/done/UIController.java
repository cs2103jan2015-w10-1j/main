package com.done;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import org.controlsfx.control.Notifications;

import com.done.logic.LogicFacade;
import com.done.model.Done;
import com.done.command.Command.CommandType;
import com.done.result.ExecutionResult;

public class UIController {
	@FXML 
	private TextField commandField;
	@FXML
	private TableView<Done> tableViewTasks;
	private Stage primaryStage;
	private LogicFacade logicFacade;
	
	private static Logger uiLogger = Logger.getLogger("UI");
	
	private static final String EMPTY_STRING = "";
	
	private static final String SHOWADD_SUCCESS_MESSAGE = "%1$s added to the list";
	private static final String SHOWADD_ERROR_MESSAGE = "Error: Adding task not successful";
	private static final String SHOWADD_LOG_MESSAGE = "Show UI for add command";
	
	private static final String SHOWDELETE_ERROR_MESSAGE = "Error: Invalid delete";
	private static final String SHOWDELETE_SUCCESS_MESSAGE = "%1$s deleted";
	private static final String SHOWDELETE_LOG_MESSAGE = "Show UI for delete command";
	
	private static final String SHOWEDIT_SUCCESS_MESSAGE = "Task %1$s";
	private static final String SHOWEDIT_ERROR_MESSAGE = "Error: Editing task not successful";
	private static final String SHOWEDIT_LOG_MESSAGE = "Show UI for edit command";
	
	private static final String SHOWCLEAR_SUCCESS_MESSAGE = "All tasks cleared";
	private static final String SHOWCLEAR_ERROR_MESSAGE = "Error: Clearing tasks not successful";
	private static final String SHOWCLEAR_LOG_MESSAGE = "Show UI for clear command";
	
	private static final String SHOWMOVE_SUCCESS_MESSAGE = "Moved from %1$s";
	private static final String SHOWMOVE_ERROR_MESSAGE = "Error: Moving task not successful";
	private static final String SHOWMOVE_LOG_MESSAGE = "Show UI for move command";
	
	private static final String SHOWSEARCH_SUCCESS_MESSAGE = "Showing search for: %1$s";
	private static final String SHOWSEARCH_ERROR_MESSAGE = "Error: Search not successful";
	private static final String SHOWSEARCH_LOG_MESSAGE = "Show UI for search command";
	
	private static final String SHOWLOAD_SUCCESS_MESSAGE = "%1$s.json loaded";
	private static final String SHOWLOAD_ERROR_MESSAGE = "Error: File load not successful";
	private static final String SHOWLOAD_STAGE_TITLE = "Done! (%1$s)";
	private static final String SHOWLOAD_LOG_MESSAGE = "Show UI for load command";
	
	private static final String SHOWSHOWALL_SUCCESS_MESSAGE = "Showing all tasks";
	private static final String SHOWSHOWALL_LOG_MESSAGE = "Show UI for showall command";
	
	private static final String SHOWRECUR_SUCCESS_MESSAGE = "%1$s";
	private static final String SHOWRECUR_ERROR_MESSAGE = "Error: Recurrence cannot be set for this task";
	private static final String SHOWRECUR_LOG_MESSAGE = "Show UI for recur command";
	
	private static final String SHOWREMIND_SUCCESS_MESSAGE = "Set reminder for %1$s";
	private static final String SHOWREMIND_ERROR_MESSAGE = "Error: Reminder cannot be set for this task";
	private static final String SHOWREMIND_LOG_MESSAGE = "Show UI for remind command";
	
	private static final String SHOWUNDO_ERROR_MESSAGE = "Error: No recent command available";
	private static final String SHOWUNDO_SUCCESS_MESSAGE = "Undo %1$s";
	private static final String SHOWUNDO_LOG_MESSAGE = "Show UI for undo command";
	
	private static final String SHOWDONE_ERROR_MESSAGE = "Error: Invalid attempt to mark a task as done";
	private static final String SHOWDONE_SUCCESS_MESSAGE = "%1$s marked as done";
	private static final String SHOWDONE_LOG_MESSAGE = "Show UI for done command";
	
	private static final String SHOWCLEARDONE_ERROR_MESSAGE = "Error: Invalid attempt to clear tasks marked as done";
	private static final String SHOWCLEARDONE_SUCCESS_MESSAGE = "All done tasks cleared";
	private static final String SHOWCLEARDONE_LOG_MESSAGE = "Show UI for cleardone command";
	
	private static final String SHOWINVALIDCOMMAND_ERROR_MESSAGE = "Error: Invalid command";
	private static final String SHOWINVALIDCOMMAND_LOG_MESSAGE = "Show UI for invalid command";
	
	private static final String STYLECLASS_FIRSTCOLUMN = "firstColumn";
	private static final String STYLECLASS_DONEROW = "doneRow";
	
	private static final int ENDTIME_LENGTH = 12;
	
	private static final double ID_COL_WIDTH = 50.0;
	private static final double TITLE_COL_WIDTH = 280.0;
	private static final double STARTTIME_COL_WIDTH = 85.0;
	private static final double ENDTIME_COL_WIDTH = 85.0;
	private static final double DEADLINE_COL_WIDTH = 130.0;
	
	private static final String ID_COL_TEXT = "No.";
	private static final String TITLE_COL_TEXT = "Task";
	private static final String STARTTIME_COL_TEXT = "Start";
	private static final String ENDTIME_COL_TEXT = "End";
	private static final String DEADLINE_COL_TEXT = "Deadline";
	
	private static final String ID_COL_VALUEFACT = "id";
	private static final String TITLE_COL_VALUEFACT = "title";
	private static final String STARTTIME_COL_VALUEFACT = "startTime";
	private static final String ENDTIME_COL_VALUEFACT = "endTime";
	private static final String DEADLINE_COL_VALUEFACT = "endTime";
	
	//@author A0088821X
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
	
	public void setStage(Stage stage) {
         this.primaryStage = stage;
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
	
	private void showAdd(boolean isSuccessful, String commandContent) {
		if(isSuccessful){
			Notifications.create().text(String.format(SHOWADD_SUCCESS_MESSAGE, commandContent)).showInformation();
		} else{
			Notifications.create().text(SHOWADD_ERROR_MESSAGE).showError();
		}
		display();
		uiLogger.log(Level.INFO, SHOWADD_LOG_MESSAGE);
	}
	
	private void showDelete(boolean isSuccessful, String commandContent) {
		if(isSuccessful){
			Notifications.create().text(String.format(SHOWDELETE_SUCCESS_MESSAGE, commandContent)).showInformation();
		} else{
			Notifications.create().text(SHOWDELETE_ERROR_MESSAGE).showError();
		}
		display();
		uiLogger.log(Level.INFO, SHOWDELETE_LOG_MESSAGE);
	}
	
	private void showEdit(boolean isSuccessful, String commandContent) {
		if(isSuccessful){
			Notifications.create().text(String.format(SHOWEDIT_SUCCESS_MESSAGE, commandContent)).showInformation();
		} else{
			Notifications.create().text(SHOWEDIT_ERROR_MESSAGE).showError();
		}
		display();
		uiLogger.log(Level.INFO, SHOWEDIT_LOG_MESSAGE);
	}
	
	private void showLoad(boolean isSuccessful, String commandContent){
		if(isSuccessful){
			Notifications.create().text(String.format(SHOWLOAD_SUCCESS_MESSAGE, commandContent)).showInformation();
			primaryStage.setTitle(String.format(SHOWLOAD_STAGE_TITLE, logicFacade.getJsonName().substring(7)));
		} else{
			Notifications.create().text(SHOWLOAD_ERROR_MESSAGE).showError();
		}
		display();
		uiLogger.log(Level.INFO, SHOWLOAD_LOG_MESSAGE);
	}
	
	private void showClear(boolean isSuccessful) {
		if(isSuccessful){
			Notifications.create().text(SHOWCLEAR_SUCCESS_MESSAGE).showInformation();
		} else{
			Notifications.create().text(SHOWCLEAR_ERROR_MESSAGE).showError();
		}
		display();
		uiLogger.log(Level.INFO, SHOWCLEAR_LOG_MESSAGE);
	}
	
	private void showMove(boolean isSuccessful, String commandContent){
		if(isSuccessful){
			Notifications.create().text(String.format(SHOWMOVE_SUCCESS_MESSAGE, commandContent)).showInformation();
		} else{
			Notifications.create().text(SHOWMOVE_ERROR_MESSAGE).showError();
		}
		display();
		uiLogger.log(Level.INFO, SHOWMOVE_LOG_MESSAGE);
	}
	
	private void showSearch(boolean isSuccessful, String commandContent){
		if(isSuccessful){
			Notifications.create().text(String.format(SHOWSEARCH_SUCCESS_MESSAGE, commandContent)).showInformation();
		} else{	
			Notifications.create().text(SHOWSEARCH_ERROR_MESSAGE).showError();
		}
		displaySearches();
		uiLogger.log(Level.INFO, SHOWSEARCH_LOG_MESSAGE);
	}
	
	private void showShowAll(){
		Notifications.create().text(SHOWSHOWALL_SUCCESS_MESSAGE).showInformation();
		display();
		uiLogger.log(Level.INFO, SHOWSHOWALL_LOG_MESSAGE);
	}
	
	private void showRecur(boolean isSuccessful, String commandContent){
		if(isSuccessful){
			Notifications.create().text(String.format(SHOWRECUR_SUCCESS_MESSAGE, commandContent)).showInformation();
		} else{
			Notifications.create().text(SHOWRECUR_ERROR_MESSAGE).showError();
		}
		display();
		uiLogger.log(Level.INFO, SHOWRECUR_LOG_MESSAGE);
	}
	
	private void showRemind(boolean isSuccessful, String commandContent){
		if(isSuccessful){
			Notifications.create().text(String.format(SHOWREMIND_SUCCESS_MESSAGE, commandContent)).showInformation();
		} else{
			Notifications.create().text(SHOWREMIND_ERROR_MESSAGE).showError();
		}
		display();
		uiLogger.log(Level.INFO, SHOWREMIND_LOG_MESSAGE);
	}

	private void showUndo(boolean isSuccessful, String commandContent)  {
		if(isSuccessful){
			Notifications.create().text(String.format(SHOWUNDO_SUCCESS_MESSAGE, commandContent)).showInformation();
		} else{
			Notifications.create().text(SHOWUNDO_ERROR_MESSAGE).showError();
		}
		display();
		uiLogger.log(Level.INFO, SHOWUNDO_LOG_MESSAGE);
	}
	
	private void showDone(boolean isSuccessful, String commandContent) {
		if(isSuccessful){
			Notifications.create().text(String.format(SHOWDONE_SUCCESS_MESSAGE, commandContent)).showInformation();
		} else{
			Notifications.create().text(SHOWDONE_ERROR_MESSAGE).showError();
		}
		display();
		uiLogger.log(Level.INFO, SHOWDONE_LOG_MESSAGE);
	}
	
	private void showClearDone(boolean isSuccessful) {
		if(isSuccessful){
			Notifications.create().text(SHOWCLEARDONE_SUCCESS_MESSAGE).showInformation();
		} else{
			Notifications.create().text(SHOWCLEARDONE_ERROR_MESSAGE).showError();
		}
		display();
		uiLogger.log(Level.INFO, SHOWCLEARDONE_LOG_MESSAGE);
	}

	private void showInvalidCommand() {
		Notifications.create().text(SHOWINVALIDCOMMAND_ERROR_MESSAGE).showError();
		uiLogger.log(Level.INFO, SHOWINVALIDCOMMAND_LOG_MESSAGE);
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
	}

	private void buildTableColumns() {
		TableColumn<Done,Integer> idCol = new TableColumn<Done,Integer>(ID_COL_TEXT);
		idCol.setCellValueFactory(new PropertyValueFactory(ID_COL_VALUEFACT));
		idCol.setPrefWidth(ID_COL_WIDTH);
		idCol.setResizable(false);
		
		TableColumn<Done,String> titleCol = new TableColumn<Done,String>(TITLE_COL_TEXT);
		titleCol.setCellValueFactory(new PropertyValueFactory(TITLE_COL_VALUEFACT));
		titleCol.setPrefWidth(TITLE_COL_WIDTH);
		titleCol.setResizable(false);
		
		TableColumn<Done,String> startTimeCol = new TableColumn<Done,String>(STARTTIME_COL_TEXT);
		startTimeCol.setCellValueFactory(new PropertyValueFactory(STARTTIME_COL_VALUEFACT));
		startTimeCol.setPrefWidth(STARTTIME_COL_WIDTH);
		startTimeCol.setResizable(false);
		
		TableColumn<Done,String> endTimeCol = new TableColumn<Done,String>(ENDTIME_COL_TEXT);
		endTimeCol.setCellValueFactory(new PropertyValueFactory(ENDTIME_COL_VALUEFACT));
		setCellFactoryEndTimeCol(endTimeCol);
		endTimeCol.setPrefWidth(ENDTIME_COL_WIDTH);
		endTimeCol.setResizable(false);
		
		TableColumn<Done,String> deadlineCol = new TableColumn<Done,String>(DEADLINE_COL_TEXT);
		deadlineCol.setCellValueFactory(new PropertyValueFactory(DEADLINE_COL_VALUEFACT));
		setCellFactoryDeadlineCol(deadlineCol);
		deadlineCol.setPrefWidth(DEADLINE_COL_WIDTH);
		deadlineCol.setResizable(false);
		
		tableViewTasks.getColumns().setAll(idCol, titleCol, startTimeCol, endTimeCol, deadlineCol);
	}

	private void setCellFactoryDeadlineCol(TableColumn<Done, String> deadlineCol) {
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
		                } else{
		                	if(item != null && item.length() > ENDTIME_LENGTH){
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
	}

	private void setCellFactoryEndTimeCol(TableColumn<Done, String> endTimeCol) {
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
		                } else{
		                	if(item != null && item.length() == ENDTIME_LENGTH){
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
	                    	 getStyleClass().removeAll(Collections.singleton(STYLECLASS_DONEROW));
	                    } else{
		                    if(task.isCompleted()){
		                    	if (!getStyleClass().contains(STYLECLASS_DONEROW)) {
		                            getStyleClass().add(STYLECLASS_DONEROW);
		                    	}
		                    } else{
		                    	 getStyleClass().removeAll(Collections.singleton(STYLECLASS_DONEROW));
		                    }
	                    }
	                   
	                }
	            };
	            return row;
	        }
	    });
		if(!tableViewTasks.getColumns().get(0).getStyleClass().contains(STYLECLASS_FIRSTCOLUMN)){
			tableViewTasks.getColumns().get(0).getStyleClass().add(STYLECLASS_FIRSTCOLUMN);
		}
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
	
	//@author A0088821X-unused
	//Reminder UI is handled directly in TaskReminder class
	/*public void updateReminder(int taskId){
		String reminder = logicFacade.getReminder(taskId);
		Notifications.create().title(UPDATEREMINDER_TITLE).text(String.format(UPDATEREMINDER_MESSAGE, reminder)).showWarning();
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
		                if(task.getId() == taskId){
		                	if (!getStyleClass().contains(STYLECLASS_REMINDERROW)) {
		                            getStyleClass().add(STYLECLASS_REMINDERROW);
		                    }
	                    }
	                }
	            };
	            return row;
	        }
	    });
	}*/
}