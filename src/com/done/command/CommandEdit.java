package com.done.command;

import java.util.List;

import com.done.model.Done;
import com.done.storage.InMemStorage;

public class CommandEdit extends Command {
	
	private String targetTitle;
	private Done task;
	private Done subbedTask;
	
	public CommandEdit(String title, Done task){
		super(CommandType.EDIT,true);
		this.targetTitle = title;
		this.task = task;
	}

	@Override
	//This method edits First tasks with the same Title
	public void execute() throws Exception {
		InMemStorage memory = InMemStorage.getInstance();
		List<Done> tasks = memory.getTasks();
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).getTitle().contains(targetTitle)) {
				subbedTask = tasks.get(i);
				tasks.set(i, task);
				break;
			}
		}
	}

	@Override
	public void undo() {
		InMemStorage memory = InMemStorage.getInstance();
		List<Done> tasks = memory.getTasks();
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).getTitle().contains(targetTitle)) {
				tasks.set(i, subbedTask);
				break;
			}
		}
	}

}
