//@author A0088821X
package com.done;


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
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			final FXMLLoader loader = new FXMLLoader(getClass().getResource("UILayout.fxml"));
	        
	        AnchorPane anchorPane = (AnchorPane) loader.load();
	        Scene scene = new Scene(anchorPane);
	        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("doneicon.png")));
	        primaryStage.setTitle("Done!");
	        primaryStage.setScene(scene);
	        primaryStage.setResizable(false);
	        primaryStage.show();
	        
	        Notifications.create().text(logicFacade.getJsonName().substring(6) + " loaded").showInformation();
	        
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
