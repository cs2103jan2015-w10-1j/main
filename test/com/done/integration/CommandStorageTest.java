//@author A0111830X
package com.done.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.done.command.Command;
import com.done.command.CommandAdd;
import com.done.command.CommandClear;
import com.done.command.CommandLoad;
import com.done.command.CommandSearch;
import com.done.model.DoneFloatingTask;
import com.done.storage.InMemStorage;
import com.done.storage.JsonStorage;

public class CommandStorageTest {

	InMemStorage inMem = InMemStorage.getInstance();
	JsonStorage jsonStore = JsonStorage.getInstance();

	@Before
	public void setUp() {
		// use "testJson.json" file for testing
		Command cmd = new CommandLoad("testJson");
		try {
			cmd.execute();
		} catch (Exception e) {
			System.out.println("Command failed to execute.");
		}
		// delete all tasks from memory and JSON first before performing tests
		inMem.delete(null, true);
	}

	@Test
	public void testAdd() {
		CommandAdd cmd = null;
		try {
			cmd = new CommandAdd(new DoneFloatingTask("INTEGRATION TEST"));
			cmd.execute();
			// added one task, size of inMemory should be one
			assertThat(inMem.getTasks().size(), is(1));
		} catch (Exception e) {
			System.out.println("Command failed to execute.");
		}
	}

	@Test
	public void testSearch() {
		Command cmd = null;
		try {
			// add 4 tasks with one different
			cmd = new CommandAdd(new DoneFloatingTask("INTEGRATION TEST"));
			cmd.execute();
			cmd = new CommandAdd(new DoneFloatingTask("INTEGRATION TEST2"));
			cmd.execute();
			cmd = new CommandAdd(new DoneFloatingTask("INTEGRATION TEST3"));
			cmd.execute();
			cmd = new CommandAdd(new DoneFloatingTask("HELLO WORLD!"));
			cmd.execute();

			// search for "INTEGRATION"
			cmd = new CommandSearch("INTEGRATION");
			cmd.execute();

			// memory storage should have size of 4
			assertThat(inMem.getTasks().size(), is(4));

			// memory workingTasks list should have a size of 3
			assertThat(inMem.getWorkingTasks().size(), is(3));
		} catch (Exception e) {
			System.out.println("Command failed to execute.");
		}
	}

	@Test
	public void testLoad() {
		Command cmd = null;
		try {
			// remove all from memory first
			inMem.delete(null, true);
			// add one task into current file
			cmd = new CommandAdd(new DoneFloatingTask("INTEGRATION TEST"));
			cmd.execute();
			// added one task into testJson, size of memory should be 1 
			assertThat(inMem.getTasks().size(), is(1));
			cmd = new CommandLoad("testJson2");
			cmd.execute();
			
			// loaded file testJson2, so in preferences the name should be testJson2.json
			assertThat(jsonStore.getJsonNameFromPref(), is("tasks//testJson2.json"));
			// remove all from memory first
			inMem.delete(null, true);
			
			// add 2 tasks into new file
			cmd = new CommandAdd(new DoneFloatingTask("INTEGRATIONLOAD TEST"));
			cmd.execute();
			cmd = new CommandAdd(new DoneFloatingTask("INTEGRATIONLOAD TEST2"));
			cmd.execute();
			// added 2 tasks into testJson2, size of memory should be 2 (not 3)
			assertThat(inMem.getTasks().size(), is(2));
			
		} catch (Exception e) {
			System.out.println("Command failed to execute.");
		}

	}
	
	@Test
	public void testClear() {
		Command cmd = null;
		try {
			cmd = new CommandAdd(new DoneFloatingTask("INTEGRATION TEST"));
			cmd.execute();
			cmd = new CommandAdd(new DoneFloatingTask("INTEGRATION TEST"));
			cmd.execute();
			cmd = new CommandAdd(new DoneFloatingTask("INTEGRATION TEST"));
			cmd.execute();
			// added 3 tasks, size should be 3
			assertThat(inMem.getTasks().size(), is(3));
			
			cmd = new CommandClear();
			cmd.execute();
			
			// clear executed, size should be 0
			assertThat(inMem.getTasks().size(), is(0));
			
		} catch (Exception e) {
			System.out.println("Command failed to execute.");
		}
	}

}
