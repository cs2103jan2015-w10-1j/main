//@author A0111830X
package com.done.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.done.command.Command;
import com.done.command.CommandAdd;
import com.done.command.CommandDone;
import com.done.command.CommandLoad;
import com.done.command.CommandSearch;
import com.done.command.CommandUndo;
import com.done.logic.Logic;
import com.done.model.DoneFloatingTask;
import com.done.storage.InMemStorage;
import com.done.storage.JsonStorage;

public class LogicStorageTest {

	InMemStorage inMem = InMemStorage.getInstance();
	JsonStorage jsonStore = JsonStorage.getInstance();
	Logic logic = new Logic();

	@Before
	public void setUp() {
		// use "testJson.json" file for testing
		Command cmd = new CommandLoad("testJson");
		logic.executeCommand(cmd);
		// delete all tasks from memory and JSON first before performing tests
		inMem.delete(null, true);
	}

	@Test
	public void testAdd() {
		Command cmd = new CommandAdd(new DoneFloatingTask("INTEGRATION TEST"));
		logic.executeCommand(cmd);
		// executing command should be successful
		assertTrue(logic.isSuccessful());
		// size of memory should be 1
		assertThat(inMem.getTasks().size(), is(1));
	}
	
	@Test
	public void testSearch() {
		Command cmd = new CommandAdd(new DoneFloatingTask("INTEGRATION TEST"));
		
		logic.executeCommand(cmd);
		assertTrue(logic.isSuccessful());
		
		cmd = new CommandAdd(new DoneFloatingTask("INTEGRATION TEST2"));
		logic.executeCommand(cmd);
		assertTrue(logic.isSuccessful());
		
		cmd = new CommandAdd(new DoneFloatingTask("HELLO"));
		logic.executeCommand(cmd);
		assertTrue(logic.isSuccessful());
		
		cmd = new CommandSearch("HELLO");
		logic.executeCommand(cmd);
		assertTrue(logic.isSuccessful());
		// search for HELLO, size should be 1 of the 3
		assertThat(inMem.getWorkingTasks().size(), is(1));
		
	}
	
	@Test
	public void testSetComplete() {
		Command cmd = new CommandAdd(new DoneFloatingTask("INTEGRATION TEST"));
		
		logic.executeCommand(cmd);
		assertTrue(logic.isSuccessful());
		// after adding, it is not a completed task
		assertFalse(inMem.getTask(1).isCompleted());
		
		try {
			cmd = new CommandDone(1);
			logic.executeCommand(cmd);
			assertTrue(logic.isSuccessful());
			
			// executed Done on the task, it is a completed task
			assertTrue(inMem.getTask(1).isCompleted());
		} catch (Exception e) {
			System.out.println("Command failed to execute.");
		}
	}

	@Test
	public void testUndo() {
		Command cmd = new CommandAdd(new DoneFloatingTask("INTEGRATION TEST"));
		
		logic.executeCommand(cmd);
		assertTrue(logic.isSuccessful());
		// after adding, it is not a completed task
		assertFalse(inMem.getTask(1).isCompleted());
		
		try {
			cmd = new CommandDone(1);
			logic.executeCommand(cmd);
			assertTrue(logic.isSuccessful());
			
			// executed Done on the task, it is a completed task
			assertTrue(inMem.getTask(1).isCompleted());
			
			cmd = new CommandUndo();
			logic.executeCommand(cmd);
			assertTrue(logic.isSuccessful());
			
			// after undo, the completed task should be incomplete
			assertFalse(inMem.getTask(1).isCompleted());
			
		} catch (Exception e) {
			System.out.println("Command failed to execute.");
		}
	}

}
