package com.done.storage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.done.model.Done;
import com.done.model.DoneDeadlineTask;
import com.done.model.DoneFloatingTask;
import com.done.model.DoneTimedTask;

public class InMemStorageTest {

	InMemStorage inMem = InMemStorage.getInstance();
	DateTime dateTime = new DateTime();
	
	//@author A0111830X
	
	@Before
	public void setUp(){
		// delete all tasks from memory and JSON first before performing tests
		inMem.delete(null, true);
	}

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
	
	@Test
	public void testClear(){
		// add 3 items
		inMem.store(new DoneFloatingTask("test 1"));
		inMem.store(new DoneTimedTask("test2", dateTime.now().getMillis(), dateTime.now().getMillis()+88888));
		inMem.store(new DoneDeadlineTask("test3", dateTime.now().getMillis()+128000));
		assertTrue(inMem.getTasks().size() == 3);
		
		// boolean true in delete method states that it is clear
		inMem.delete(null, true);
		assertTrue(inMem.getTasks().size() == 0);
		
	}
	
	@Test
	public void testSetCompleted(){
		Done doneFT = new DoneFloatingTask("test1");
		
		// when first added, it is not a completed task
		inMem.store(doneFT);
		assertFalse(inMem.getTask(doneFT.getId()).isCompleted());
		
		// get and set to completed
		// should return true
		inMem.getTask(doneFT.getId()).setCompleted(true);
		assertTrue(inMem.getTask(doneFT.getId()).isCompleted());
	}

}
