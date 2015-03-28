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

	InMemStorage inMem = InMemStorage.getInstance();

	@Test
	public void testStore() {
		Done doneFT = new DoneFloatingTask("test1");
		Done doneTT = new DoneTimedTask("test2", 676723, 98891999);
		// This is the equivalence partition for the Done ArrayList
		// whether is it [NULL] or [NOT NULL]
		assertNotNull(doneFT);
		assertNotNull(doneTT);
		assertTrue(inMem.store(doneFT));
		assertTrue(inMem.store(doneTT));

	}

	@Test
	public void testStore2() {
		List<Done> testDone = new ArrayList<Done>();
		testDone.add(null);
		assertNull(testDone.get(0));
	}

}
