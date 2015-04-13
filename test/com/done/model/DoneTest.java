//@author A0111830X
package com.done.model;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import com.done.model.Done.TaskType;

public class DoneTest {
	

	
	@Test
	public void testFloatingTask() {
		// assert that if is a new instance of DoneFloatingTask, then the
		// type is Floating Task
		Done done = new DoneFloatingTask("title1", "tag1");
		assertThat(done.getType(), is(TaskType.FLOATING));
	}

	@Test
	public void testTimedTask() {
		// assert that if is a new instance of DoneTimedTask, then the
		// type is Timed Task
		Done done = new DoneTimedTask("title1", 6711253, 6711253);
		assertThat(done.getType(), is(TaskType.TIMED));
	}

	@Test
	public void testDeadlineTask() {
		// assert that if is a new instance of DoneDeadlineTask, then the
		// type is Deadline Task
		Done done = new DoneDeadlineTask("title1", 676721);
		assertThat(done.getType(), is(TaskType.DEADLINE));
	}

	@Test
	public void testDoneString() {
		Done done = new DoneFloatingTask("test");
		assertThat(done.getTitle(), not("\\s"));
		assertThat(done.getTitle().length(), not(0));
	}

}
