package com.done.command;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Level;

import com.done.command.Command.CommandType;
import com.done.model.Done;
import com.done.storage.InMemStorage;

public class CommandRecur extends Command {
	
	private Done task;
	private String frequency;
	private String period;
	
	//@author A0115635J
	//for recur
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	private void recurWeekly(Done task, String numberOfWeeksToStop) {
		
		final int WEEK = 604800;
		int weeks = Integer.parseInt(numberOfWeeksToStop);
		final int STOPTIME = weeks*WEEK;
		
		final Runnable recur = new Runnable() {
			public void run() {
				InMemStorage inMemStorage = InMemStorage.getInstance();
				inMemStorage.store(task);
			}
		};
		final ScheduledFuture<?> recurHandle = scheduler.scheduleAtFixedRate(recur, 0, WEEK, SECONDS);
		scheduler.schedule(new Runnable() {
			public void run() { recurHandle.cancel(true); }
			}, STOPTIME, SECONDS);
	}
	
	private void recurDaily(Done task, String numberOfDaysToStop) {
		
		final int DAY = 86400;
		int days = Integer.parseInt(numberOfDaysToStop);
		final int STOPTIME = days*DAY;
		
		final Runnable recur = new Runnable() {
			public void run() {
				InMemStorage inMemStorage = InMemStorage.getInstance();
				inMemStorage.store(task);
			}
		};
		final ScheduledFuture<?> recurHandle = scheduler.scheduleAtFixedRate(recur, 0, DAY, SECONDS);
		scheduler.schedule(new Runnable() {
			public void run() { recurHandle.cancel(true); }
			}, STOPTIME, SECONDS);
	}
	
	private void recurMonthly(Done task, String numberOfMonthsToStop) {
		
		final int MONTH = 604800*30;
		int months = Integer.parseInt(numberOfMonthsToStop);
		final int STOPTIME = months*MONTH;
		
		final Runnable recur = new Runnable() {
			public void run() {
				InMemStorage inMemStorage = InMemStorage.getInstance();
				inMemStorage.store(task);
			}
		};
		final ScheduledFuture<?> recurHandle = scheduler.scheduleAtFixedRate(recur, 0, MONTH, SECONDS);
		scheduler.schedule(new Runnable() {
			public void run() { recurHandle.cancel(true); }
			}, STOPTIME, SECONDS);
	}
	
	

	//@author A0115777W

	public CommandRecur(int recurIndex, String period){
		super(CommandType.RECUR, true);
		this.task = InMemStorage.getInstance().getTask(recurIndex);
		this.period = period;
		commandLogger.log(Level.INFO, "Recur Command Created");
	}

	public CommandRecur(Done task,String period){
		super(CommandType.RECUR,true);
		this.task = task;
		this.period = period;
		commandLogger.log(Level.INFO, "Recur Command Created");
	}
	
	//@author A0115635J
	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub
		if (this.task != null) {
			if(frequency == "Daily"){
				recurDaily(task, period);
			} else if(frequency == "Weekly"){
				recurWeekly(task, period);
			} else if(frequency == "Monthly"){
				recurMonthly(task, period);
			} else { System.out.println("Invalid Frequency!");
			}
		} else {
			throw new Exception("TaskNullException");
		}

	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		CommandDelete command = new CommandDelete(task);
		command.execute();
	}

	@Override
	public String getCommandContent() {
		// TODO Auto-generated method stub
		return null;
	}

}
