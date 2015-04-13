package com.done;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import org.controlsfx.control.Notifications;

import com.done.logic.LogicFacade;


public class DoneUI extends Application {
	
	LogicFacade logicFacade = new LogicFacade();
	
	private static Logger uiLogger = Logger.getLogger("UI");
	
	private static final String STAGE_TITLE = "Done! (%1$s)";
	private static final String LOAD_SUCCESS_MESSAGE = "%1$s loaded";
	private static final String FXML_FILE = "UILayout.fxml";
	private static final String ICON_FILE = "doneicon.png";
	private static final String START_LOG_MESSAGE = "Show starting UI stage";
	
	//@author A0088821X
	@Override
	public void start(Stage primaryStage) {
		try {
			final FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_FILE));
	        AnchorPane anchorPane = (AnchorPane) loader.load();
	        Scene scene = new Scene(anchorPane);
	        
	        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream(ICON_FILE)));
	        primaryStage.setTitle(String.format(STAGE_TITLE, logicFacade.getJsonName().substring(7))); 
	        primaryStage.setScene(scene);
	        primaryStage.setResizable(false);
	        primaryStage.show();
	        
	        UIController controller = loader.getController();
	        controller.setStage(primaryStage);
	        
	        Notifications.create().text(String.format(LOAD_SUCCESS_MESSAGE, logicFacade.getJsonName().substring(7))).showInformation();
	        uiLogger.log(Level.INFO, START_LOG_MESSAGE);
	        
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
