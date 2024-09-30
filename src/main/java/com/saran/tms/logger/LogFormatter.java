package com.saran.tms.logger;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.io.StringWriter;
import java.io.PrintWriter;

public class LogFormatter extends Formatter {

    @Override
    public String format(LogRecord record) {
        StringBuilder logBuilder = new StringBuilder();
        
        logBuilder.append(String.format("%1$tF %1$tT [%2$s] \n%3$s: %4$s%n",
                record.getMillis(),
                record.getLevel(),
                record.getLoggerName(),
                record.getMessage()));

        if (record.getThrown() != null) {
        	logBuilder.append("Exception: ");
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            record.getThrown().printStackTrace(pw);
            logBuilder.append(sw.toString());
        }

        return logBuilder.toString();
    }
}
