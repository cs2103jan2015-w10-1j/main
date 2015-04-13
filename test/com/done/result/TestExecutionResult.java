package com.done.result;

import static org.junit.Assert.*;

import org.junit.Test;

import com.done.result.ExecutionResult;
import com.done.command.Command.CommandType;

public class TestExecutionResult {
	
	//@author A0088821X
	@Test
	public void testExecutionResult() {
		ExecutionResult result = new ExecutionResult(CommandType.ADD, true, "meet John at 6");
		
		//test the getters
		assertEquals(result.getCommandType(), CommandType.ADD);
		assertEquals(result.isSuccessful(), true);
		assertEquals(result.getCommandContent(), "meet John at 6");
		
		//test the setters
		result.setCommandType(CommandType.DELETE);
		assertEquals(result.getCommandType(), CommandType.DELETE);
		result.setSuccessful(false);
		assertEquals(result.isSuccessful(),false);
		assertEquals(result.getCommandContent(), null); //if isSuccessful is false, commandContent must be null
		result.setSuccessful(true); //set isSuccessful back to true
		result.setCommandContent("finish CS2103 homework");
		assertEquals(result.getCommandContent(), "finish CS2103 homework");
	}
}
