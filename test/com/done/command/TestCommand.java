package com.done.command;

import static org.junit.Assert.*;

import org.junit.Test;

import com.done.command.Command.CommandType;
import com.done.model.DoneFloatingTask;

public class TestCommand {

	//@author A0115777W
	@Test
	public void testCommandAdd() {
		Command command = new CommandAdd(new DoneFloatingTask("aaa"));
		assertEquals(command.getCommandType(), CommandType.ADD);
	}

	@Test
	public void testCommandDelete() throws Exception {
		Command command = new CommandDelete(1);
		assertEquals(command.getCommandType(), CommandType.DELETE);
	}

	@Test
	public void testCommandClear() {
		Command command = new CommandClear();
		assertEquals(command.getCommandType(), CommandType.CLEAR);
	}

	@Test
	public void testCommandSearch() {
		Command command = new CommandSearch("aaa");
		assertEquals(command.getCommandType(), CommandType.SEARCH);
	}

	@Test
	public void testCommandRecur() throws Exception {
		Command command = new CommandRecur(1, "weekly", 1);
		assertEquals(command.getCommandType(), CommandType.RECUR);
	}

	@Test
	public void testCommandRemind() {
		// Command command = new CommandRemind(1,"weekly");
		// assertEquals(command.getCommandType(), CommandType.REMIND);
	}

}
