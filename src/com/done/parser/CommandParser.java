package com.done.parser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import com.done.command.CommandDone;
import com.done.command.CommandInvalid;
import com.done.command.CommandLoad;
import com.done.command.CommandMove;
import com.done.command.CommandSearch;
import com.done.command.CommandUndo;
import com.done.model.Done;
import com.done.model.DoneDeadlineTask;
import com.done.model.DoneFloatingTask;
import com.done.model.DoneTimedTask;
import com.done.storage.InMemStorage;

public class CommandParser {

	private static CommandParser instance = null;
	private static Logger parserLogger = Logger.getLogger("CommandParser");

	private CommandParser() {

	}

	public static CommandParser getInstance() {
		if (instance == null) {
			instance = new CommandParser();
		}
		return instance;
	}

	public Command parseInputToMakeCommand(String userInput) {

		parserLogger.log(Level.INFO, "Input passed to make Command");
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
		} else if (command.equalsIgnoreCase("move")) {
			return CommandType.MOVE;
		} else if (command.equalsIgnoreCase("done")) {
			return CommandType.DONE;
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
		ArrayList<String> commandContent = sliceContent(currentContent);
		return commandContent;
	}

	public String removeFirstWord(String userCommand) {
		String returnStr = getFirstWord(userCommand).trim();
		returnStr = userCommand.substring(returnStr.length());
		return returnStr.trim();
	}

	public String getFirstWord(String userCommand) {
		return userCommand.trim().split("\\s+")[0];
	}

	private Command makeCommand(String commandWord, String commandContent) {
		if (commandWord.equalsIgnoreCase("add")) {
			/*
			 * after we know that this is a "add" command we should implement a
			 * definer to differentiate the type of tasks so you should use
			 * commandContent as the parameter of a new method
			 */
			parserLogger.log(Level.INFO, "make add Command");
			Done tempTask = defineTask(commandContent);
			return new CommandAdd(tempTask);
		} else if (commandWord.equalsIgnoreCase("delete")) {
			parserLogger.log(Level.INFO, "make delete Command");
			if (isContentValid(commandWord, commandContent)) {
				return new CommandDelete(Integer.parseInt(commandContent));
			} else {
				return new CommandInvalid();
			}
		} else if (commandWord.equalsIgnoreCase("load")) {
			parserLogger.log(Level.INFO, "make load Command");
			return new CommandLoad(commandContent);
		} else if (commandWord.equalsIgnoreCase("clear")) {
			parserLogger.log(Level.INFO, "make clear Command");
			return new CommandClear();
		} else if (commandWord.equalsIgnoreCase("move")) {
			parserLogger.log(Level.INFO, "make move Command");
			ArrayList<String> indexes = sliceContent(commandContent);
			if ((isContentValid(commandWord, indexes.get(0)))
					&& (isContentValid(commandWord, indexes.get(1)))) {
				int origin = Integer.parseInt(indexes.get(0));
				int destination = Integer.parseInt(indexes.get(1));
				return new CommandMove(origin,destination);
			} else {
				return new CommandInvalid();
			}
		} else if (commandWord.equalsIgnoreCase("search")) {
			parserLogger.log(Level.INFO, "make search Command");
			return new CommandSearch(commandContent);
		} else if (commandWord.equalsIgnoreCase("undo")) {
			parserLogger.log(Level.INFO, "make undo Command");
			return new CommandUndo();
		} else if (commandWord.equalsIgnoreCase("done")) {
			parserLogger.log(Level.INFO, "make done Command");
			if (isContentValid(commandWord, commandContent)) {
				return new CommandDone(Integer.parseInt(commandContent));
			} else {
				return new CommandInvalid();
			}
		} else {
			parserLogger.log(Level.INFO, "make invalid Command");
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
			parserLogger.log(Level.INFO, "make Timed Task");
			task = addTimed(split, timeIndex);
			return task;

		} else if (isDeadline) {
			// if content has deadline (time) at
			// we generate new deadlinedTask
			// return deadlinedTask
			parserLogger.log(Level.INFO, "make Deadline Task");
			task = addDeadline(split, timeIndex, dateIndex);
			return task;
		}

		if (!commandContent.equals(null) && !commandContent.equals("")) {
			// if content isn't null or ""
			// we generate new floatingTask
			// we return floatingTask
			parserLogger.log(Level.INFO, "make Floating Task");
			task = new DoneFloatingTask(commandContent);
			return task;
		}
		// else return null or throw exception
		return null;

	}

	// At current Stage, only slice the content into parts separated by spaces
	// Might modify in later versions
	private ArrayList<String> sliceContent(String content) {
		ArrayList<String> slicedContent = new ArrayList<String>();
		String[] contentPieces = content.split("\\s+");
		for (String pieceOfContent : contentPieces) {
			slicedContent.add(pieceOfContent);
		}
		return slicedContent;
	}

	private boolean isContentValid(String commandWord, String commandContent) {
		if (commandWord.equalsIgnoreCase("add")) {
			parserLogger.log(Level.INFO, "Command Content is Valid");
			return true;
		} else if (commandWord.equalsIgnoreCase("delete")
				|| commandWord.equalsIgnoreCase("done")
				|| commandWord.equalsIgnoreCase("move")) {
			return isPositiveNonZeroInt(commandContent);
		} else {
			parserLogger.log(Level.INFO, "Command Content is invalid");
			return false;
		}
	}

	private boolean isPositiveNonZeroInt(String content) {
		try {
			int i = Integer.parseInt(content);
			return (i > 0 ? true : false);
		} catch (NumberFormatException nfe) {
			parserLogger.log(Level.INFO, "Command Content is invalid");
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

		StringBuilder taskTitleBuilder = new StringBuilder();
		for (int i = 0; i < timeIndex - 1; i++) {
			if (i == timeIndex - 2) {
				taskTitleBuilder.append(content[i]);
			} else {
				taskTitleBuilder.append(content[i] + " ");
			}
		}
		String taskTitle = taskTitleBuilder.toString();
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

		} else if (content[timeIndex - 1].equalsIgnoreCase("..e")) {
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
