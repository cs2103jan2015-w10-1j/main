package com.done.model;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import com.done.model.Done.TaskType;

public class DoneTest {

	@Test
	public void testFloatingTask() {
		Done done = new DoneFloatingTask("title1", "tag1");
		assertThat(done.getType(), is(TaskType.FLOATING));
	}

	@Test
	public void testTimedTask() {
		Done done = new DoneTimedTask("title1", 6711253, 6711253);
		assertThat(done.getType(), is(TaskType.TIMED));
	}

	@Test
	public void testDeadlineTask() {
		Done done = new DoneDeadlineTask("title1", 676721);
		assertThat(done.getType(), is(TaskType.DEADLINE));
	}

	@Test
	public void testDoneString() {
		Done done = new DoneFloatingTask("test");
		assertThat(done.getTitle(), not("\\s"));
		assertThat(done.getTitle().length(), not(0));
	}

	@Test
	public void testDoneLong() {
		DoneDeadlineTask done = new DoneDeadlineTask("test", 7823718);
		// Equivalence Partition of:
		// long data type [MIN_VALUE] and [MAX_VALUE]
		// not less than [MIN_VALUE] and not more than [MAX_VALUE]
		long endTime = done.getEndTime();
		assertTrue(endTime >= Long.MIN_VALUE && endTime <= Long.MAX_VALUE);
	}

}
