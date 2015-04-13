package com.done.command;

import static org.junit.Assert.*;

import org.junit.Test;

import com.done.command.Command.CommandType;
import com.done.model.DoneDeadlineTask;
import com.done.model.DoneFloatingTask;
import com.done.model.DoneTimedTask;

public class TestCommand {

	public final long START_TIME_FOR_TEST = Long.parseLong("1428901200000");
	public final long END_TIME_FOR_TEST = Long.parseLong("1428908400000");
	public final long DEADLINE_FOR_TEST = Long.parseLong("1429084800000");
	public final int FIRST_INDEX_TEST = 1;
	public final int SECOND_INDEX_TEST = 2;
	public final int LOWER_FAILURE_INDEX = 0;
	public final int UPPER_FAILURE_INDEX = 99999;
	public final int TIME_RECUR_TEST = 3;
	public final String JSON_NAME_TEST = "testfile";
	public final String DATE_STRING = "15042015";
	public final String TIME_STRING = "1350";
	public final String FREQUENCY_TEST = "weekly";

	//@author A0115777W
	@Test
	public void testCommandAdd() {
		Command commandAddFloat = new CommandAdd(new DoneFloatingTask("aaa"));
		assertEquals(commandAddFloat.getCommandType(), CommandType.ADD);
		Command commandAddTimed = new CommandAdd(new DoneTimedTask("bbb",
				START_TIME_FOR_TEST, END_TIME_FOR_TEST));
		assertEquals(commandAddTimed.getCommandType(), CommandType.ADD);
		Command commandAddDeadLine = new CommandAdd(new DoneDeadlineTask("ccc",
				DEADLINE_FOR_TEST));
		assertEquals(commandAddDeadLine.getCommandType(), CommandType.ADD);
	}

	@Test
	public void testCommandEdit() throws Exception {
		Command commandEditFloat = new CommandEdit(FIRST_INDEX_TEST,
				new DoneFloatingTask("aaa"));
		assertEquals(commandEditFloat.getCommandType(), CommandType.EDIT);
		Command commandEditTimed = new CommandEdit(
				FIRST_INDEX_TEST,
				new DoneTimedTask("bbb", START_TIME_FOR_TEST, END_TIME_FOR_TEST));
		assertEquals(commandEditTimed.getCommandType(), CommandType.EDIT);
		Command commandEditDeadLine = new CommandEdit(FIRST_INDEX_TEST,
				new DoneDeadlineTask("ccc", DEADLINE_FOR_TEST));
		assertEquals(commandEditDeadLine.getCommandType(), CommandType.EDIT);
	}
	
	@Test(expected = Exception.class)
	public void testCommandEditFail() throws Exception {
		Command commandEditLower = new CommandEdit(LOWER_FAILURE_INDEX,
				new DoneFloatingTask("aaa"));
		Command commandEditUpper = new CommandEdit(UPPER_FAILURE_INDEX,
				new DoneFloatingTask("aaa"));
	}

	@Test
	public void testCommandDelete() throws Exception {
		Command commandDelete = new CommandDelete(FIRST_INDEX_TEST);
		assertEquals(commandDelete.getCommandType(), CommandType.DELETE);
	}

	@Test(expected = Exception.class)
	public void testCommandDeleteFail() throws Exception {
		Command commandDeleteLower = new CommandDelete(LOWER_FAILURE_INDEX);
		Command commandDeleteUpper = new CommandDelete(UPPER_FAILURE_INDEX);
	}

	@Test
	public void testCommandClear() {
		Command commandClear = new CommandClear();
		assertEquals(commandClear.getCommandType(), CommandType.CLEAR);
	}

	@Test
	public void testCommandClearDone() {
		Command commandClearDone = new CommandClearDone();
		assertEquals(commandClearDone.getCommandType(), CommandType.CLEARDONE);
	}

	public void testCommandDone() throws Exception {
		Command commandDone = new CommandDone(1);
		assertEquals(commandDone.getCommandType(), CommandType.DONE);
	}

	@Test(expected = Exception.class)
	public void testCommandDoneFail() throws Exception {
		Command commandDoneLower = new CommandDone(LOWER_FAILURE_INDEX);
		Command commandDoneUpper = new CommandDone(UPPER_FAILURE_INDEX);
	}

	@Test
	public void testCommandMove() throws Exception {
		Command commandMove = new CommandMove(FIRST_INDEX_TEST,
				SECOND_INDEX_TEST);
		assertEquals(commandMove.getCommandType(), CommandType.MOVE);
	}

	@Test(expected = Exception.class)
	public void testCommandMoveFail() throws Exception {
		Command commandMoveLower = new CommandMove(LOWER_FAILURE_INDEX,
				SECOND_INDEX_TEST);
		Command commandMoveUpper = new CommandMove(FIRST_INDEX_TEST,
				UPPER_FAILURE_INDEX);
	}

	@Test
	public void testCommandSearch() {
		Command command = new CommandSearch("aaa");
		assertEquals(command.getCommandType(), CommandType.SEARCH);
	}

	@Test
	public void testCommandShowAll() {
		Command commandShowAll = new CommandShowAll();
		assertEquals(commandShowAll.getCommandType(), CommandType.SHOWALL);
	}

	@Test
	public void testCommandRecur() throws Exception {
		Command command = new CommandRecur(FIRST_INDEX_TEST, "FREQUENCY_TEST",
				TIME_RECUR_TEST);
		assertEquals(command.getCommandType(), CommandType.RECUR);
	}

	@Test(expected = Exception.class)
	public void testCommandRecurFail() throws Exception {
		Command commandRecurLower = new CommandRecur(LOWER_FAILURE_INDEX,
				FREQUENCY_TEST, TIME_RECUR_TEST);
		Command commandRecurUpper = new CommandRecur(UPPER_FAILURE_INDEX,
				FREQUENCY_TEST, TIME_RECUR_TEST);

	}

	@Test
	public void testCommandRemind() throws Exception {
		Command commandRemind = new CommandRemind(FIRST_INDEX_TEST,
				DATE_STRING, TIME_STRING);
		assertEquals(commandRemind.getCommandType(), CommandType.REMIND);
	}

	@Test(expected = Exception.class)
	public void testCommandRemindFail() throws Exception {
		Command commandRemindLower = new CommandRemind(LOWER_FAILURE_INDEX,
				DATE_STRING, TIME_STRING);
		Command commandRemindUpper = new CommandRemind(UPPER_FAILURE_INDEX,
				DATE_STRING, TIME_STRING);

	}

	@Test
	public void testCommandLoad() {
		Command commandLoad = new CommandLoad(JSON_NAME_TEST);
		assertEquals(commandLoad.getCommandType(), CommandType.LOAD);
	}

	@Test
	public void testCommandUndo() {
		Command commandUndo = new CommandUndo();
		assertEquals(commandUndo.getCommandType(), CommandType.UNDO);
	}

	@Test
	public void testCommandExit() {
		Command commandExit = new CommandExit(true);
		assertEquals(commandExit.getCommandType(), CommandType.EXIT);
	}

	@Test
	public void testCommandInvalid() {
		Command commandInvalid = new CommandInvalid();
		assertEquals(commandInvalid.getCommandType(), CommandType.INVALID);
	}

}
