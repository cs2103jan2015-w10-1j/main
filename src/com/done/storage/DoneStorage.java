package com.done.storage;

import com.done.Done;

public interface DoneStorage {
	
	public void load();
	public void store(Done task);

}
