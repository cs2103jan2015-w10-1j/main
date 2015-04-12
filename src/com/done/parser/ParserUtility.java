package com.done.parser;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.done.command.Command;
import com.done.command.CommandAdd;
import com.done.command.CommandClear;
import com.done.command.CommandClearDone;
import com.done.command.CommandDelete;
import com.done.command.CommandDone;
import com.done.command.CommandEdit;
import com.done.command.CommandExit;
import com.done.command.CommandInvalid;
import com.done.command.CommandLoad;
import com.done.command.CommandMove;
import com.done.command.CommandRecur;
import com.done.command.CommandRemind;
import com.done.command.CommandSearch;
import com.done.command.CommandShowAll;
import com.done.command.CommandUndo;
import com.done.model.Done;
import com.done.model.DoneDeadlineTask;
import com.done.model.DoneFloatingTask;
import com.done.model.DoneTimedTask;

public class ParserUtility {
	
	private static Logger parserLogger = Logger.getLogger("CommandParser");
	
	//The methods to make respective Commands. 
	//May return a CommandInvalid if the content is not correct
	//@author A0115777W
	protected static Command makeAdd(String content){
		try{
			Done tempTask = defineTask(content);
			return new CommandAdd(tempTask);
		}catch (Exception e){
			return new CommandInvalid();
		}
	}
	
	protected static Command makeDelete(String content){
		if (isPositiveInt(content)) {
			try{
				return new CommandDelete(Integer.parseInt(content));
			}catch(Exception e){
				return new CommandInvalid();
			}
		} else {
			return new CommandInvalid();
		}
	}

	protected static Command makeEdit(String content){
		String indexString = getFirstWord(content);
		if (isPositiveInt(indexString)) {
			try{
				int index = Integer.parseInt(indexString);
				Done changedTask = defineTask(removeFirstWord(content));
				parserLogger.log(Level.INFO, "make edit Command");
				return new CommandEdit(index, changedTask);
			}catch (Exception e){
				parserLogger.log(Level.INFO, "make invalid Command instead");
				return new CommandInvalid();
			}
		} else {
			parserLogger.log(Level.INFO, "make invalid Command instead");
			return new CommandInvalid();
		}
	}
	
	protected static Command makeClear(String content){
		parserLogger.log(Level.INFO, "make clear Command");
		if(content.equals("")){
			return new CommandClear();
		}else{
			return new CommandInvalid();
		}
	}
	
	protected static Command makeMove(String content){
		parserLogger.log(Level.INFO, "make move Command");
		ArrayList<String> indexes = sliceContent(content);
		if ((isPositiveInt(indexes.get(0)))
				&& (isPositiveInt(indexes.get(1)))) {
			try{
				System.out.println(indexes.get(0));
				System.out.println(indexes.get(1));
				int origin = Integer.parseInt(indexes.get(0));
				int destination = Integer.parseInt(indexes.get(1));
				return new CommandMove(origin, destination);
			}catch(Exception e){
				return new CommandInvalid();
			}
		} else {
			parserLogger.log(Level.INFO, "make invalid Command instead");
			return new CommandInvalid();
		}
	}
	
	protected static Command makeSearch(String content){
		parserLogger.log(Level.INFO, "make search Command");
		return new CommandSearch(content);
	}
	
	protected static Command makeShowAll(){
		parserLogger.log(Level.INFO, "make showall Command");
		return new CommandShowAll();
	}
	
	protected static Command makeDone(String content){
		parserLogger.log(Level.INFO, "make done Command");
		if (isPositiveInt(content)) {
			try{
				return new CommandDone(Integer.parseInt(content));
			}catch (Exception e){
				return new CommandInvalid();
			}
		} else {
			return new CommandInvalid();
		}
	}
	
	protected static Command makeClearDone(){
		parserLogger.log(Level.INFO, "make cleardone Command");
		return new CommandClearDone();
	}
	
	protected static Command makeRecur(String content){
		parserLogger.log(Level.INFO, "make recur Command");
		ArrayList<String> contents = sliceContent(content);
		String index = contents.get(0);
		String period = contents.get(1);
		if (isPositiveInt(index) && isValidPeriod(period)) {
			try{
				return new CommandRecur(Integer.parseInt(index), period);
			}catch (Exception e){
				return new CommandInvalid();
			}
		} else {
			return new CommandInvalid();
		}
	}
	
	protected static Command makeRemind(String content){
		parserLogger.log(Level.INFO, "make remind Command");
		try {
			ArrayList<String> contents = sliceContent(content);
			String index = contents.get(0);
			String date = contents.get(1);
			String time = contents.get(2);
			return new CommandRemind(Integer.parseInt(index), date, time);
		} catch (Exception e) {
			parserLogger.log(Level.INFO, "make invalid Command instead");
			return new CommandInvalid();
		}
	}
	
	protected static Command makeLoad(String content){
		parserLogger.log(Level.INFO, "make load Command");
		return new CommandLoad(content);
	}
	
	protected static Command makeUndo(){
		parserLogger.log(Level.INFO, "make undo Command");
		return new CommandUndo();
	}
	
	protected static Command makeExit(){
		parserLogger.log(Level.INFO, "make exit Command");
		return new CommandExit(true);
	}
	
	protected static Command makeInvalid(){
		parserLogger.log(Level.INFO, "make invalid Command");
		return new CommandInvalid();
	}
	//End of Command Maker Methods
	
	//Methods to make Tasks(Floating, Timed and Deadline tasks).
	//@author A0111830X
	protected static Done defineTask(String commandContent) {
		Done task;
		boolean isTimed = false;
		boolean isDeadline = false;
		int dateIndex = 0;
		int timeIndex = 0;

		String[] split = commandContent.split("\\s+");
		for (int i = 0; i < split.length; i++) {
			if (split[i].equals("..s") || split[i].equals("..e")) {
				// timed task
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
			// if content contains end time and/or start time
			// we generate (new) timedTask
			// and return timedTask
			parserLogger.log(Level.INFO, "make Timed Task");
			task = addTimed(split, timeIndex);
			return task;

		} else if (isDeadline) {
			// if content has date and time
			// we generate new deadlineTask
			// and return deadlineTask
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

	//@author A0111830X
	protected static Done addTimed(String[] content, int timeIndex) {
		String startTime = "";
		String endTime = "";
		long startTimeValue = 0;
		long endTimeValue = 0;
		
		Done task = null;
		DateTimeFormatter dtf = DateTimeFormat.forPattern("HH:mm");
		
		LocalDate localStartDate = LocalDate.now();
		LocalDate localEndDate = LocalDate.now();
		
		// obtain title of task from input
		StringBuilder taskTitleBuilder = new StringBuilder();
		for (int i = 0; i < timeIndex - 1; i++) {
			if (i == timeIndex - 2) {
				taskTitleBuilder.append(content[i]);
			} else {
				taskTitleBuilder.append(content[i] + " ");
			}
		}
		String taskTitle = taskTitleBuilder.toString();
		
		// check for parameters ..s and ..e
		if (content[timeIndex - 1].equalsIgnoreCase("..s")) {
			startTime = content[timeIndex];
			try {
				if (content[timeIndex + 1].equalsIgnoreCase("..e")) {
					endTime = content[timeIndex + 2];
				}
			} catch (NullPointerException e) {
				System.out.println("Missing time information");
			}
			
			// obtain time from input
			LocalTime localStartTime = dtf.parseLocalTime(startTime);
			LocalTime localEndTime = dtf.parseLocalTime(endTime);

			startTimeValue = localStartDate.toDate().getTime()
					+ localStartTime.getMillisOfDay();
			endTimeValue = localEndDate.toDate().getTime()
					+ localEndTime.getMillisOfDay();

		} else if (content[timeIndex - 1].equalsIgnoreCase("..e")) {
			endTime = content[timeIndex];
			
			// obtain date and time from now() and input
			DateTime localStartDateTime = DateTime.now();
			LocalTime localEndTime = dtf.parseLocalTime(endTime);

			startTimeValue = localStartDateTime.getMillis();
			endTimeValue = localEndDate.toDate().getTime()
					+ localEndTime.getMillisOfDay();
		}

		task = new DoneTimedTask(taskTitle, startTimeValue, endTimeValue);
		return task;
	}

	//@author A0111830X
	protected static Done addDeadline(String[] content, int timeIndex, int dateIndex) {
		Done task = null;
		
		// obtain title of task
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < dateIndex - 1; i++) {
			if (i == dateIndex - 2) {
				sb.append(content[i]);
			} else {
				sb.append(content[i] + " ");
			}

		}
		String taskTitle = sb.toString();
		
		// obtain date time and format it to long milliseconds
		DateTimeFormatter dtf = DateTimeFormat.forPattern("ddMMyyyy HH:mm");
		DateTime date = dtf.parseDateTime(content[dateIndex] + " "
				+ content[timeIndex]);
		task = new DoneDeadlineTask(taskTitle, date.getMillis());
		return task;

	}
	//End of Task Maker Methods
	
	//Methods to process content Strings, like removing and getting first words
	//@author A0111830X
	protected static String removeFirstWord(String userCommand) {
		String returnStr = getFirstWord(userCommand).trim();
		returnStr = userCommand.substring(returnStr.length());
		return returnStr.trim();
	}

	protected static String getFirstWord(String userCommand) {
		return userCommand.trim().split("\\s+")[0];
	}

	//@author A0115777W
	protected static ArrayList<String> sliceContent(String content) {
		ArrayList<String> slicedContent = new ArrayList<String>();
		String[] contentPieces = content.split("\\s+");
		for (String pieceOfContent : contentPieces) {
			slicedContent.add(pieceOfContent);
		}
		return slicedContent;
	}
	//End of Content processer methods
	
	//Validation methods to detect if a String content piece is as required
	//@author A0115777W-unused
	protected static boolean isContentValid(String commandWord, String commandContent) {
		if (commandWord.equalsIgnoreCase("add")) {
			parserLogger.log(Level.INFO, "Command Content is Valid");
			return true;
		} else if (commandWord.equalsIgnoreCase("delete")
				|| commandWord.equalsIgnoreCase("done")
				|| commandWord.equalsIgnoreCase("move")) {
			return isPositiveInt(commandContent);
		} else {
			parserLogger.log(Level.INFO, "Command Content is invalid");
			return false;
		}
	}
	
	//@author A0115777W-unused
	protected static boolean isValidTime(String time){
		if(time.length()>5){
			return false;
		}
		int hour = Integer.parseInt(time.substring(0,2));
		int minute = Integer.parseInt(time.substring(3, 5));
        if(hour<0||hour>24){
			return false;
		}else if(minute<0||minute>60){
			return false;
		}
        return true;
	}

	//@author A0115777W
	protected static boolean isPositiveInt(String content) {
		try {
			int i = Integer.parseInt(content);
			return (i > 0 ? true : false);
		} catch (NumberFormatException nfe) {
			parserLogger.log(Level.INFO, "Command Content is invalid");
			return false;
		}
	}

	//@author A0115777W
	protected static boolean isValidPeriod(String content){
		if(content.equals("hourly")||content.equals("daily")||content.equals("weekly")||content.equals("monthly")||content.equals("yearly")){
			return true;
		}else{
			return false;
		}
	}
	//End of Validation methods
}
