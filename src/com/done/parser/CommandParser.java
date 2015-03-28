package com.done.parser;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.done.command.Command;
import com.done.command.Command.CommandType;
import com.done.command.CommandAdd;
import com.done.command.CommandClear;
import com.done.command.CommandDelete;
import com.done.command.CommandInvalid;
import com.done.command.CommandLoad;
import com.done.command.CommandSearch;
import com.done.model.Done;
import com.done.model.DoneDeadlineTask;
import com.done.model.DoneFloatingTask;
import com.done.model.DoneTimedTask;

public class CommandParser {

	public CommandParser() {

	}

	public Command parseInputToMakeCommand(String userInput) {

		String commandWord = getFirstWord(userInput);
		String commandContent = removeFirstWord(userInput);
		return makeCommand(commandWord, commandContent);

	}

	public CommandType getCommandType(String commandWord) {

		String command = getFirstWord(commandWord);

		if (command.equalsIgnoreCase("add")) {
			return CommandType.ADD;
		} else if (command.equalsIgnoreCase("delete")) {
			return CommandType.DELETE;
		} else if (command.equalsIgnoreCase("display")) {
			return CommandType.DISPLAY;
		} else if (command.equalsIgnoreCase("clear")) {
			return CommandType.CLEAR;
		} else if (command.equalsIgnoreCase("edit")) {
			return CommandType.EDIT;
		} else if (command.equalsIgnoreCase("load")) {
			return CommandType.LOAD;
		} else if (command.equalsIgnoreCase("search")) {
			return CommandType.SEARCH;
		} else if (command.equalsIgnoreCase("undo")) {
			return CommandType.UNDO;
		} else if (command.equalsIgnoreCase("reorder")) {
			return CommandType.REORDER;
		} else if (command.equalsIgnoreCase("move")) {
			return CommandType.MOVE;
		} else if (command.equalsIgnoreCase("mark")) {
			return CommandType.MARK;
		} else if (command.equalsIgnoreCase("remind")) {
			return CommandType.REMIND;
		} else if (command.equalsIgnoreCase("recur")) {
			return CommandType.RECUR;
		} else if (command.equalsIgnoreCase("exit")) {
			return CommandType.EXIT;
		} else {
			return CommandType.INVALID;
		}
	}

	public ArrayList<String> getCommandContent(String userCommand) {
		String currentContent = removeFirstWord(userCommand);
		assert currentContent != null;
		ArrayList<String> commandContent = processContent(currentContent);
		return commandContent;
	}

	public String removeFirstWord(String userCommand) {
		return userCommand.replace(getFirstWord(userCommand), "").trim();
	}

	public String getFirstWord(String userCommand) {
		return userCommand.trim().split("\\s+")[0];
	}

	// At current Stage, only slice the content into parts separated by spaces
	// Might modify in later versions
	private ArrayList<String> processContent(String content) {
		// JERRY: Ruoming, the ArrayList is actually redundant, returning just
		// the
		// String[] array would be good enough.
		ArrayList<String> processedContent = new ArrayList<String>();
		String[] contentPieces = content.split("\\s+");
		for (String pieceOfContent : contentPieces) {
			processedContent.add(pieceOfContent);
		}
		return processedContent;
	}

	private Command makeCommand(String commandWord, String commandContent) {
		// Command command;
		if (commandWord.equalsIgnoreCase("add")) {
			/*
			 * after we know that this is a "add" command we should implement a
			 * definer to differentiate the type of tasks so you should use
			 * commandContent as the parameter of a new method
			 */
			Done tempTask = defineTask(commandContent);
			return new CommandAdd(tempTask);
		} else if (commandWord.equalsIgnoreCase("delete")) {
			if (isContentValid(commandWord, commandContent)) {
				return new CommandDelete(Integer.parseInt(commandContent));
			} else {
				return new CommandInvalid();
			}
		} else if (commandWord.equalsIgnoreCase("load")) {
			return new CommandLoad(commandContent);
		} else if (commandWord.equalsIgnoreCase("clear")) {
			return new CommandClear();
		} else if (commandWord.equalsIgnoreCase("search")) {
			return new CommandSearch(commandContent);
		} else {
			return new CommandInvalid();
		}
	}

	private Done defineTask(String commandContent) {
		Done task;
		boolean isTimed = false;
		boolean isDeadline = false;
		int dateIndex = 0;
		int timeIndex = 0;

		// now u breakdown commandContent
		// search for the type and set corresponding boolean
		String[] split = commandContent.split("\\s+");
		for (int i = 0; i < split.length; i++) {
			if (split[i].equals("..s") || split[i].equals("..e")) {
				// timedtask
				isTimed = true;
				timeIndex = i + 1;
				break;
			} else if (split[i].equals("..d")) {
				// deadline task
				isDeadline = true;
				timeIndex = i + 2;
				dateIndex = i + 1;
				break;
			}
		}

		if (isTimed) {
			// if content contains start time
			// we generate (new) timeTask
			// return timedTask
			task = addTimed(split, timeIndex);
			return task;

		} else if (isDeadline) {
			// if content has deadline (time) at
			// we generate new deadlinedTask
			// return deadlinedTask
			task = addDeadline(split, timeIndex, dateIndex);
			return task;
		}

		if (!commandContent.equals(null) && !commandContent.equals("")) {
			// if content isn't null or ""
			// we generate new floatingTask
			// we return floatingTask
			task = new DoneFloatingTask(commandContent);
			return task;
		}
		// else return null or throw exception
		return null;

	}

	private boolean isContentValid(String commandWord, String commandContent) {
		if (commandWord.equalsIgnoreCase("add")) {
			return true;
		} else if (commandWord.equalsIgnoreCase("delete")
				|| commandWord.equalsIgnoreCase("mark")) {
			return isPositiveNonZeroInt(commandContent);
		} else {
			return false;
		}
	}

	private boolean isPositiveNonZeroInt(String content) {
		try {
			int i = Integer.parseInt(content);
			return (i > 0 ? true : false);
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	private Done addTimed(String[] content, int timeIndex) {
		Done task = null;
		DateTimeFormatter dtf = DateTimeFormat.forPattern("HH:mm");
		long startTimeValue = 0;
		long endTimeValue = 0;
		LocalDate localStartDate = LocalDate.now();
		LocalDate localEndDate = LocalDate.now();

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < timeIndex - 1; i++) {
			if (i == timeIndex - 2) {
				sb.append(content[i]);
			} else {
				sb.append(content[i] + " ");
			}
		}
		String taskTitle = sb.toString();
		String startTime = "";
		String endTime = "";
		if (content[timeIndex - 1].equalsIgnoreCase("..s")) {
			startTime = content[timeIndex];
			try {
				if (content[timeIndex + 1].equalsIgnoreCase("..e")) {
					endTime = content[timeIndex + 2];
				}
			} catch (NullPointerException e) {
				System.out.println("Missing time information");
			}			
			LocalTime localStartTime = dtf.parseLocalTime(startTime);
			LocalTime localEndTime = dtf.parseLocalTime(endTime);

			startTimeValue = localStartDate.toDate().getTime()
					+ localStartTime.getMillisOfDay();
			endTimeValue = localEndDate.toDate().getTime()
					+ localEndTime.getMillisOfDay();
			
		}else if(content[timeIndex - 1].equalsIgnoreCase("..e")){
			endTime = content[timeIndex];
			
			DateTime localStartDateTime = DateTime.now();
			LocalTime localEndTime = dtf.parseLocalTime(endTime);
			
			startTimeValue = localStartDateTime.getMillis();
			endTimeValue = localEndDate.toDate().getTime()
					+ localEndTime.getMillisOfDay();
		}

		task = new DoneTimedTask(taskTitle, startTimeValue, endTimeValue);
		return task;
	}

	private Done addDeadline(String[] content, int timeIndex, int dateIndex) {
		Done task = null;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < dateIndex - 1; i++) {
			if (i == dateIndex - 2) {
				sb.append(content[i]);
			} else {
				sb.append(content[i] + " ");
			}

		}
		String taskTitle = sb.toString();
		DateTimeFormatter dtf = DateTimeFormat.forPattern("ddMMyyyy HH:mm");
		DateTime date = null;
		date = dtf.parseDateTime(content[dateIndex] + " " + content[timeIndex]);
		task = new DoneDeadlineTask(taskTitle, date.getMillis());
		return task;

	}

}
