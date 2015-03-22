package com.done.storage;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.done.model.Done;
import com.done.model.DoneFloatingTask;
import com.done.model.DoneTimedTask;

public class InMemStorageTest {

	DoneStorage inMem = InMemStorage.getInstance();

	@Test
	public void testStore() {
		List<Done> testDone = new ArrayList<Done>();
		testDone.add(new DoneFloatingTask("test1"));
		testDone.add(new DoneTimedTask("test2", 676723, 98891999));
		// This is the equivalence partition for the Done ArrayList
		// whether is it [NULL] or [NOT NULL]
		assertNotNull(testDone.get(0));
		assertNotNull(testDone.get(1));
		assertTrue(inMem.store(testDone));

	}

	@Test
	public void testStore2() {
		List<Done> testDone = new ArrayList<Done>();
		testDone.add(null);
		assertNull(testDone.get(0));
	}

}
