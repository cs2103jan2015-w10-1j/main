package com.done.logic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.done.command.CommandAdd;
import com.done.command.CommandInvalid;
import com.done.command.CommandMove;
import com.done.model.DoneFloatingTask;

public class TestLogic {

	Logic testLogic = new Logic();

	@Test
	public void testExecuteAdd() {
		CommandAdd cmd = new CommandAdd(new DoneFloatingTask("hello world!"));
		testLogic.executeCommand(cmd);
		assertTrue(testLogic.isSuccessful());
	}

	@Test
	public void testExecuteInvalid() {
		CommandInvalid cmd = new CommandInvalid();
		testLogic.executeCommand(cmd);
		assertTrue(testLogic.isSuccessful());
	}
	
	@Test
	public void testFail() {
		// move task to location that does not exist
		CommandMove cmd = new CommandMove(0, 10000);
		testLogic.executeCommand(cmd);
		assertFalse(testLogic.isSuccessful());
	}
}