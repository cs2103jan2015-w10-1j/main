//@author A0115777W
package com.done.parser;

import static org.junit.Assert.*;

import org.junit.Test;

import com.done.command.Command;
import com.done.command.Command.CommandType;

public class TestCommandParser {

	CommandParser testedParser = CommandParser.getInstance();

	//@author A0115777W
	@Test
	public void testGetValidCommand() {
		Command validCommandAdd = testedParser
				.parseInputToMakeCommand("add cs2103 ..s 1300 ..e 1500");
		assertEquals(validCommandAdd.getCommandType(), CommandType.ADD);
		Command validCommandDelete = testedParser
				.parseInputToMakeCommand("delete 1");
		assertEquals(validCommandDelete.getCommandType(), CommandType.DELETE);
		Command validCommandClear = testedParser
				.parseInputToMakeCommand("clear");
		assertEquals(validCommandClear.getCommandType(), CommandType.CLEAR);
		Command validCommandClearDone = testedParser
				.parseInputToMakeCommand("cleardone");
		assertEquals(validCommandClearDone.getCommandType(),
				CommandType.CLEARDONE);
		Command validCommandDone = testedParser
				.parseInputToMakeCommand("done 1");
		assertEquals(validCommandDone.getCommandType(), CommandType.DONE);
		Command validCommandEdit = testedParser
				.parseInputToMakeCommand("Edit 1 cs2104 ..s 1500 ..e 1700");
		assertEquals(validCommandEdit.getCommandType(), CommandType.EDIT);
		Command validCommandExit = testedParser.parseInputToMakeCommand("exit");
		assertEquals(validCommandExit.getCommandType(), CommandType.EXIT);
		Command validCommandLoad = testedParser
				.parseInputToMakeCommand("load anotherfile");
		assertEquals(validCommandLoad.getCommandType(), CommandType.LOAD);
		Command validCommandMove = testedParser
				.parseInputToMakeCommand("move 1 2");
		assertEquals(validCommandMove.getCommandType(), CommandType.MOVE);
		Command validCommandRecur = testedParser
				.parseInputToMakeCommand("recur 1 weekly 3");
		assertEquals(validCommandRecur.getCommandType(), CommandType.RECUR);
		Command validCommandRemind = testedParser
				.parseInputToMakeCommand("remind 1 15042015 0800");
		assertEquals(validCommandRemind.getCommandType(), CommandType.REMIND);
		Command validCommandSearch = testedParser
				.parseInputToMakeCommand("search cs");
		assertEquals(validCommandSearch.getCommandType(), CommandType.SEARCH);
		Command validCommandShowAll = testedParser
				.parseInputToMakeCommand("showall");
		assertEquals(validCommandShowAll.getCommandType(), CommandType.SHOWALL);
		Command validCommandUndo = testedParser.parseInputToMakeCommand("undo");
		assertEquals(validCommandUndo.getCommandType(), CommandType.UNDO);
	}

	@Test
	public void testGetInvalidCommand() {
		Command invalidCommandAdd = testedParser
				.parseInputToMakeCommand("add cs2103 ..s 1300 ..e 2500");
		assertEquals(invalidCommandAdd.getCommandType(), CommandType.INVALID);
		Command invalidCommandDelete = testedParser
				.parseInputToMakeCommand("delete 0");
		assertEquals(invalidCommandDelete.getCommandType(), CommandType.INVALID);
		Command invalidCommandClear = testedParser
				.parseInputToMakeCommand("clear done");
		assertEquals(invalidCommandClear.getCommandType(), CommandType.INVALID);
		Command invalidCommandDone = testedParser
				.parseInputToMakeCommand("done 0");
		assertEquals(invalidCommandDone.getCommandType(), CommandType.INVALID);
		Command invalidCommandEdit = testedParser
				.parseInputToMakeCommand("Edit 0 cs2104 ..s 1500 ..e 1700");
		assertEquals(invalidCommandEdit.getCommandType(), CommandType.INVALID);
		Command invalidCommandMove = testedParser
				.parseInputToMakeCommand("move 0 2");
		assertEquals(invalidCommandMove.getCommandType(), CommandType.INVALID);
		Command invalidCommandRecur = testedParser
				.parseInputToMakeCommand("recur 0 weekly 3");
		assertEquals(invalidCommandRecur.getCommandType(), CommandType.INVALID);
		Command invalidCommandRemind = testedParser
				.parseInputToMakeCommand("remind 0 15042015 0800");
		assertEquals(invalidCommandRemind.getCommandType(), CommandType.INVALID);

	}

	@Test
	public void testGetCommandType() {
		assertEquals(testedParser.getCommandType("add 1"), CommandType.ADD);
		assertEquals(testedParser.getCommandType("delete 2"),
				CommandType.DELETE);
		assertEquals(testedParser.getCommandType("clear"), CommandType.CLEAR);
		assertEquals(testedParser.getCommandType("cleardone"),
				CommandType.CLEARDONE);
		assertEquals(testedParser.getCommandType("done 1"), CommandType.DONE);
		assertEquals(
				testedParser.getCommandType("edit 1 cs2105 ..s 1300 ..e 1600"),
				CommandType.EDIT);
		assertEquals(testedParser.getCommandType("move 1 5"), CommandType.MOVE);
		assertEquals(testedParser.getCommandType("exit"), CommandType.EXIT);
		assertEquals(testedParser.getCommandType("aohuef"), CommandType.INVALID);
		assertEquals(testedParser.getCommandType("load anotherfile"),
				CommandType.LOAD);
		assertEquals(testedParser.getCommandType("recur 1 weekly 5"),
				CommandType.RECUR);
		assertEquals(testedParser.getCommandType("remind 1 15042015 0609"),
				CommandType.REMIND);
		assertEquals(testedParser.getCommandType("search cs"),
				CommandType.SEARCH);
		assertEquals(testedParser.getCommandType("showall"),
				CommandType.SHOWALL);
		assertEquals(testedParser.getCommandType("undo"), CommandType.UNDO);
	}

	@Test
	public void testGetCommandContent() {
		assertEquals(testedParser.getCommandContent("add abcde"), "abcde");
	}

}
