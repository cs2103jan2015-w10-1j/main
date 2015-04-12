package com.done.storage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class StorageLogger {

	private static final String DIR_LOG = "log//";
	private static FileHandler fileHandler;
	private static Logger storageLogger = Logger.getLogger("Storage");

	//@author A0111830X
	public static void setUpLogger() {
		SimpleFormatter sf = new SimpleFormatter();

		try {
			String out = new SimpleDateFormat(
					"'StorageLog-'dd-MM-yyyy HHmm'.log'").format(new Date());
			File file = new File(DIR_LOG);
			file.mkdir();
			fileHandler = new FileHandler(DIR_LOG + out);
			storageLogger.addHandler(fileHandler);
			fileHandler.setFormatter(sf);
		} catch (SecurityException | IOException e) {
			storageLogger.log(Level.WARNING, "Unable to read file!", e);
		}
	}

	//@author generated
	public static Logger getStorageLogger() {
		return storageLogger;
	}
	
	public static void setStorageLogger(Logger storageLogger) {
		StorageLogger.storageLogger = storageLogger;
	}

}
