package stubs;

import java.util.ArrayList;

import com.done.command.Command;
import com.done.command.CommandAdd;
import com.done.command.CommandClear;
import com.done.command.CommandDelete;
import com.done.command.CommandInvalid;
import com.done.command.CommandSearch;
import com.done.model.Done;


public class ParserUtils {

	public static String removeFirstWord(String userCommand) {
		return userCommand.replace(getFirstWord(userCommand), "").trim();
	}

	public static String getFirstWord(String userCommand) {
		return userCommand.trim().split("\\s+")[0];
	}

	// At current Stage, only slice the content into parts separated by spaces
	// Might modify in later versions
	public static ArrayList<String> processContent(String content) {
		// JERRY: Ruoming, the ArrayList is actually redundant, returning just the
		// String[] array would be good enough.
		ArrayList<String> processedContent = new ArrayList<String>();
		String[] contentPieces = content.split("\\s+");
		for (String pieceOfContent : contentPieces) {
			processedContent.add(pieceOfContent);
		}
		return processedContent;
	}

	
	public static Command makeCommand(String commandWord, String commandContent) {
		// Command command;
		if (commandWord.equalsIgnoreCase("add")) {
			/* after we know that this is a "add" command
			 * we should implement a definer to differentiate the type of tasks
			 * so you should use commandContent as the parameter of a new method
			 * 
			 */
			Done tempTask = defineTask(commandContent); 
			
			
			/*String[] split = commandContent.split("\\s+");
			for(int i = 0; i < split.length; i++){
				if(split[i].equals("s")){
					// timedtask
				}else if(split[i].equals("e")){
					// deadline task
				}
			}*/
			
			return new CommandAdd(tempTask);
		} else if (commandWord.equalsIgnoreCase("delete")) {
			if(isContentValid(commandWord, commandContent)){
				return new CommandDelete(Integer.parseInt(commandContent));
			}else{
				return new CommandInvalid();
			}
		} else if (commandWord.equalsIgnoreCase("clear")) {
			return new CommandClear();
		} else if (commandWord.equalsIgnoreCase("search")) {
			return new CommandSearch(commandContent);
		} else {
			return new CommandInvalid();
		}
	}

	private static Done defineTask(String commandContent) {
		// now u breakdown commandContent
		
		// if content contains start time
		// we generate (new) timeTask
		//return timedTask
		
		// if content has deadline (time)
		// we generate new deadlinedTask
		//return deadlinedTask
		
		// if content isn't null or ""
		// we generate new floatingTask
		// we return floatingTask
		
		// else return null or throw exception
		
		
		return null;
		
	}

	public static boolean isContentValid(String commandWord,
			String commandContent) {
		if (commandWord.equalsIgnoreCase("add")) {
			return true;
		} else if (commandWord.equalsIgnoreCase("delete")
				|| commandWord.equalsIgnoreCase("mark")) {
			return isPositiveNonZeroInt(commandContent);
		} else {
			return false;
		}
	}

	public static boolean isPositiveNonZeroInt(String content) {
		try {
			int i = Integer.parseInt(content);
			return (i > 0 ? true : false);
		} catch (NumberFormatException nfe) {
			return false;
		}
	}
}
