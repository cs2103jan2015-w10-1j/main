package com.done;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class DoneUI extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			final FXMLLoader loader = new FXMLLoader(getClass().getResource("UILayout.fxml"));
	        
	        AnchorPane anchorPane = (AnchorPane) loader.load();
	        Scene scene = new Scene(anchorPane);
	        
	        primaryStage.setTitle("Done!");
	        primaryStage.setScene(scene);
	        primaryStage.setResizable(false);
	        primaryStage.show();
	        
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
