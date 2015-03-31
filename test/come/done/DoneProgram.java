package come.done;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.done.command.Command;
import com.done.command.Command.CommandType;
import com.done.logic.Logic;
import com.done.logic.LogicFacade;
import com.done.parser.CommandParser;
import com.done.result.ExecutionResult;
import com.done.storage.InMemStorage;

public class DoneProgram {

	// Integration test

	@Test
	public void testAddFloatingFromFacade() {
		String input = "add facade test";
		InMemStorage inMemStorage = InMemStorage.getInstance();

		LogicFacade logicFacade = new LogicFacade();
		ExecutionResult result = logicFacade.getExecutionResult(input);

		assertThat(result.getCommandContent(), is("facade test"));
		assertThat(result.getCommandType(), is(CommandType.ADD));
		assertTrue(inMemStorage.getTasks().size() > 0);

	}
	
	// redundant test from Logic (one level higher)
	@Test
	public void testAddFloatingFromLogic() {
		Logic logic = new Logic();
		InMemStorage inMemStorage = InMemStorage.getInstance();

		String input = "add logic test";

		String commandContent = logic.getCmdContent(input);
		CommandParser parser = CommandParser.getInstance();
		Command command = parser.parseInputToMakeCommand(input);

		logic.executeCommand(command);

		assertThat(logic.isSuccessful(), is(true));
		assertThat(commandContent, is("logic test"));
		assertThat(logic.getCmdType(input), is(CommandType.ADD));
		assertThat(command.getCommandType(), is(CommandType.ADD));
		assertTrue(inMemStorage.getTasks().size() > 0);
	}

	@Test
	public void testUndo() {
		InMemStorage inMemStorage = InMemStorage.getInstance();
		String input = "add undo test";
		String undoInput = "undo";
		
		// test with add
		LogicFacade logicFacade = new LogicFacade();
		ExecutionResult resultAdd = logicFacade.getExecutionResult(input);
		assertTrue(resultAdd.isSuccessful());
		// with an empty list, add one task should return size of 1
		assertThat(inMemStorage.getTasks().size(), is(1));
		
		// test undo
		ExecutionResult resultUndo = logicFacade.getExecutionResult(undoInput);
		assertTrue(resultUndo.isSuccessful());
		assertThat(resultUndo.getCommandType(), is(CommandType.UNDO));
		// after undo, means removing the added task above, size should return 0
		assertThat(inMemStorage.getTasks().size(), is(0));
		
	}
	
	@Test
	public void testSearch(){
		LogicFacade logicFacade = new LogicFacade();
		InMemStorage inMemStorage = InMemStorage.getInstance();
		ExecutionResult result = null;
		
		// add 4 items 
		result = logicFacade.getExecutionResult("add hello");
		assertTrue(result.isSuccessful());
		assertThat(result.getCommandType(), is(CommandType.ADD));
		result = logicFacade.getExecutionResult("add hello2");
		assertTrue(result.isSuccessful());
		assertThat(result.getCommandType(), is(CommandType.ADD));
		result = logicFacade.getExecutionResult("add hello3");
		assertTrue(result.isSuccessful());
		assertThat(result.getCommandType(), is(CommandType.ADD));
		result = logicFacade.getExecutionResult("add test");
		assertTrue(result.isSuccessful());
		assertThat(result.getCommandType(), is(CommandType.ADD));
		
		// search for "hello", size of WorkingTask(search) List should be 3
		// as there are 3 instances of "hello"
		result = logicFacade.getExecutionResult("search hello");
		assertTrue(result.isSuccessful());
		assertThat(result.getCommandType(), is(CommandType.SEARCH));
		assertTrue(inMemStorage.getWorkingTasks().size() == 3);
	}
	
	@Before
	public void setUp(){
		// delete all tasks from memory and JSON first before performing tests
		InMemStorage inMemStorage = InMemStorage.getInstance();
		inMemStorage.delete(null, true);
	}
	
}
