package com.done.storage;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;

import com.done.model.Done;
import com.done.model.DoneDeadlineTask;
import com.done.model.DoneFloatingTask;
import com.done.model.DoneTimedTask;

public class InMemStorageTest {

	InMemStorage inMem = InMemStorage.getInstance();
	DateTime dateTime = new DateTime();

	@Test
	public void testStore() {		
		Done doneFT = new DoneFloatingTask("test1");
		Done doneTT = new DoneTimedTask("test2", dateTime.now().getMillis(), dateTime.now().getMillis()+88888);
		Done doneDT = new DoneDeadlineTask("test3", dateTime.now().getMillis()+128000);
		// This is the equivalence partition for the Done ArrayList
		// whether is it [NULL] or [NOT NULL]
		assertNotNull(doneFT);
		assertNotNull(doneTT);
		assertTrue(inMem.store(doneFT));
		assertTrue(inMem.store(doneTT));
		assertTrue(inMem.store(doneDT));

	}

	@Test
	public void testStore2() {
		List<Done> testDone = new ArrayList<Done>();
		testDone.add(null);
		assertNull(testDone.get(0));
	}

}
