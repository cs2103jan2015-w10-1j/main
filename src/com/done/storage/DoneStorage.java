package com.done.storage;

import java.util.List;

import com.done.model.Done;

public interface DoneStorage {
	
	public List<Done> load();
	public void store(List<Done> task);

}
