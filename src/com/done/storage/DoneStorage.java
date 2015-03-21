package com.done.storage;

import java.util.List;

import com.done.model.Done;

public interface DoneStorage {
	
	public List<Done> load();
	public boolean store(List<Done> task);

}
