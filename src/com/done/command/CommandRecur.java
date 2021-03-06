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
	
	private static final String MESSAGE_CREATION = "Recur Command Created"; 
	private static final String RECUR_COMMAND_CONTENT = "Set %1$s to recur %2$s for %3$s times";

	private Done task;
	private String frequency;
	private int numberToStop;
	
	//@author A0115777W

	public CommandRecur(int recurIndex, String period, int numberToStop) throws Exception{
		super(CommandType.RECUR, true);
		assert recurIndex > 0;
		if (recurIndex > InMemStorage.getInstance().getTasks().size()) {
			commandLogger.log(Level.INFO, "Too large Index");
			throw new Exception("Too large Destination Index Value");
		}
		this.task = InMemStorage.getInstance().getTask(recurIndex);
		this.frequency = period;
		this.numberToStop = numberToStop;
		commandLogger.log(Level.INFO, MESSAGE_CREATION);
	}

	public CommandRecur(Done task, String period, int numberToStop){
		super(CommandType.RECUR,true);
		this.task = task;
		this.frequency = period;
		this.numberToStop = numberToStop;
		commandLogger.log(Level.INFO, MESSAGE_CREATION);
	}
	
	//@author A0115635J
	//for recur
	
	private void recurWeekly(Done task, int numberOfWeeksToStop) {
		
		final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		final int WEEK = 604800;
		final int STOPTIME = numberOfWeeksToStop*WEEK;
		
		final Runnable recur = new Runnable() {
			public void run() {
				InMemStorage inMemStorage = InMemStorage.getInstance();
				inMemStorage.store(task);
			}
		};
		final ScheduledFuture<?> recurHandle = scheduler.scheduleAtFixedRate(recur, WEEK, WEEK, SECONDS);
		scheduler.schedule(new Runnable() {
			public void run() { recurHandle.cancel(true); }
			}, STOPTIME, SECONDS);
	}
	
	private void recurDaily(Done task, int numberOfDaysToStop) {
		
		final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		final int DAY = 86400;
		final int STOPTIME = numberOfDaysToStop*DAY;
		
		final Runnable recur = new Runnable() {
			public void run() {
				InMemStorage inMemStorage = InMemStorage.getInstance();
				inMemStorage.store(task);
			}
		};
		final ScheduledFuture<?> recurHandle = scheduler.scheduleAtFixedRate(recur, DAY, DAY, SECONDS);
		scheduler.schedule(new Runnable() {
			public void run() { recurHandle.cancel(true); }
			}, STOPTIME, SECONDS);
	}
	
	private void recurMonthly(Done task, int numberOfMonthsToStop) {
		
		final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		final int MONTH = 604800*30;
		final int STOPTIME = numberOfMonthsToStop*MONTH;
		
		final Runnable recur = new Runnable() {
			public void run() {
				InMemStorage inMemStorage = InMemStorage.getInstance();
				inMemStorage.store(task);
			}
		};
		final ScheduledFuture<?> recurHandle = scheduler.scheduleAtFixedRate(recur, MONTH, MONTH, SECONDS);
		scheduler.schedule(new Runnable() {
			public void run() { recurHandle.cancel(true); }
			}, STOPTIME, SECONDS);
	}
	
	private void recurTest(Done task, int numberOfMonthsToStop) {
		
		final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		final int MONTH = 604800*30;
		final int STOPTIME = numberOfMonthsToStop*MONTH;
		
		final Runnable recur = new Runnable() {
			public void run() {
				InMemStorage inMemStorage = InMemStorage.getInstance();
				inMemStorage.store(task);
			}
		};
		final ScheduledFuture<?> recurHandle = scheduler.scheduleAtFixedRate(recur, 0, 5, SECONDS);
		scheduler.schedule(new Runnable() {
			public void run() { recurHandle.cancel(true); }
			}, 9, SECONDS);
	}
	
	//@author A0115635J
	@Override
	public void execute() throws Exception {
		commandLogger.log(Level.INFO, "Recur Command Called");
		// TODO Auto-generated method stub
		if (this.task != null) {
			System.out.println(frequency);
			if(frequency.equalsIgnoreCase("daily")){
				commandLogger.log(Level.INFO, "Make daily recur");
				recurDaily(task, numberToStop);
				commandLogger.log(Level.INFO, "Recur Command Success");
			} else if(frequency.equalsIgnoreCase("weekly")){
				commandLogger.log(Level.INFO, "Make weekly recur");
				recurWeekly(task, numberToStop);
				commandLogger.log(Level.INFO, "Recur Command Success");
			} else if(frequency.equalsIgnoreCase("monthly")){
				commandLogger.log(Level.INFO, "Make monthly recur");
				recurMonthly(task, numberToStop);
				commandLogger.log(Level.INFO, "Recur Command Success");
			} else if(frequency.equalsIgnoreCase("test")){
				commandLogger.log(Level.INFO, "Make test recur");
				recurTest(task, numberToStop);
				commandLogger.log(Level.INFO, "Recur Command Success");
			}else { System.out.println("Invalid Frequency!");
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
	
	//@author A0088821X
	@Override
	public String getCommandContent() {
		// TODO Auto-generated method stub
		return String.format(RECUR_COMMAND_CONTENT, task.getTitle(), frequency.toLowerCase(), numberToStop);
	}

}
