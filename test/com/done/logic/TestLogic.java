package com.done.logic;

import static org.junit.Assert.*;

import org.junit.Test;

import stubs.ParserUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.done.model.Done;
import com.done.model.DoneFloatingTask;
import com.done.storage.DoneStorage;
import com.done.storage.InMemStorage;
 
import com.done.parser.CommandParser;
import com.done.command.Command.CommandType;

public class TestLogic{
	
	private static final int ZERO = 0;
	
	Logic testLogic = new Logic();
	
	@Test
	public void testAddTask(){
		
		String testString = "hahaha";
		testLogic.addTask(testString);
		assertEquals(testLogic.getTasksForUI().get(ZERO), testString);
	}
	
	@Test
	public void testDeleteTask(){
		
		testLogic.deleteTask(ZERO);
		assertEquals(testLogic.getTasksForUI().get(0), ZERO);
	}
	
	@Test
	public void testExecuteCommand(){
		String testString1 = "add Batman";
		String testString2 = "add Ironman is the best";
		String testString3 = "delete Batman";
		testLogic.executeCommand(testString1);
		testLogic.executeCommand(testString2);
		testLogic.executeCommand(testString3);
		assertEquals(testLogic.getTasksForUI().get(ZERO), testString2);
		
	}
}