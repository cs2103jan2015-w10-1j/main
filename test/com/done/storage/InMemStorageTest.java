package com.done.storage;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.done.model.Done;
import com.done.model.DoneFloatingTask;
import com.done.storage.InMemStorage;

public class InMemStorageTest {
	
	InMemStorage inMem = new InMemStorage();

	@Test
	public void testStore() {
		List<Done> testDone = new ArrayList<Done>();
		testDone.add(new DoneFloatingTask("test1"));
		testDone.add(new DoneFloatingTask("test2"));
		// This is the equivalence partition for the Done ArrayList
		// whether is it [NULL] or [NOT NULL]
		assertNotNull(testDone.get(0));
		assertTrue(inMem.store(testDone));
		
	}
	
	@Test
	public void testStore2() {
		List<Done> testDone = null;
		assertNull(testDone);
	}

}
