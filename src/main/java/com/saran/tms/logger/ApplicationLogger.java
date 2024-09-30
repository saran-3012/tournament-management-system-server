package com.saran.tms.logger;

import java.util.logging.Level;
import java.time.LocalDate;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class ApplicationLogger {
	   private static final Logger logger = Logger.getLogger(ApplicationLogger.class.getName());
	   private static FileHandler fileHandler;
	   private static String date;
	   private static String logsFolder;
	   
	   private synchronized static void checkLoggerSetup() {
		   String currentDate = LocalDate.now().toString();
		   if(date != null && date.equals(currentDate)) {
			   return;
		   }
		   
		   date = currentDate;
		   
		   if(fileHandler != null) {
			   logger.removeHandler(fileHandler);
			   fileHandler.close();
		   }
		   
		   try {
			   
			   fileHandler = new FileHandler(logsFolder + "/application.tms." + date + ".log", true);	
			   fileHandler.setFormatter(new LogFormatter());
			   logger.addHandler(fileHandler);
		   }
		   catch(Exception e) {
			   logger.log(Level.SEVERE, "File Handler instance failed", e);
		   }
		   
	   }
	   
	   public static void log(Level level, String message, Throwable exception) {
		   checkLoggerSetup();
		   logger.log(level, message, exception);
	   }
	   
	   public static void log(Level level, String message) {
		   checkLoggerSetup();
		   logger.log(level, message);
	   }
	   
	   public static void initializeLogger(String logsFolderPath) {
		   logsFolder = logsFolderPath;
		   checkLoggerSetup();
		   logger.log(Level.INFO, "Logger Initialized!");
	   }
	   
	   public static void closeLogger() {
		   if(fileHandler != null) {
			   logger.removeHandler(fileHandler);
			   fileHandler.close();
	           fileHandler = null;
		   }
	   }
}
