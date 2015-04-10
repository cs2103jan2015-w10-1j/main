package com.done.storage;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

public class JSONStorageTest {

	JSONStorage test = JSONStorage.getInstance();

	//@author A0111830X
	@Test
	public void testJsonNameToPrefs() throws FileNotFoundException, IOException {

		assertTrue(test.setJsonNameToPref("tasks//tasks"));

	}
	
	@Test
	public void testGetJsonName() {
		test.setJsonNameToPref("tasks//testJson");
		// if input to Properties is "testJson.json"
		// then the output after retrieving should be the same
		assertThat(new String(test.getJsonNameFromPref()), is("tasks//testJson.json"));
	}

}
