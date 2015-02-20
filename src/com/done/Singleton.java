package com.done;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Singleton {
	
	private static Singleton instance = new Singleton();
	
	private TextField textField;
	private TextArea textArea;
	
	public static Singleton getInstance(){
		return instance;
	}
	
	public TextField getTextField() {
		return textField;
	}

	public void setTextField(TextField textField) {
		this.textField = textField;
	}

	public TextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(TextArea textArea) {
		this.textArea = textArea;
	}

	

}
