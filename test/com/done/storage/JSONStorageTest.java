package com.done.storage;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import com.done.storage.JSONStorage;

public class JSONStorageTest {
	
	JSONStorage test = new JSONStorage();

	@Test
	public void testJsonNameToPrefs() throws FileNotFoundException, IOException {
		
		assertTrue(test.setJsonNameToPref("tasks.json"));
		
	}
	
	@Test
	public void testGetJsonName(){
		test.setJsonNameToPref("testJson.json");
		// if input to Properties is "testJson.json"
		// then the output after retrieving should be the same 
		assertThat(new String(test.getJsonNameFromPref()), is("testJson.json"));
	}

}
