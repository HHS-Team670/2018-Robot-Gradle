package org.usfirst.frc.team670.robot.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.usfirst.frc.team670.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public abstract class LoggingCommand extends Command {
	private int executeCount = 0;
	private static int executeLoggingInterval = 8;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	protected void logInitialize(Map<String, Object> data) {
		log("initialize", data);
	}

	protected void logExecute(Map<String, Object> data) {
		if (executeCount % executeLoggingInterval == 0) {
			data.put("executionCount", executeCount);
			log("execute", data);
		}
		executeCount++;
	}

	protected void logFinished(Map<String, Object> data) {
		log("finished", data);
	}

	private void log(String stage, Map<String, Object> data) {
		//This would print to command line
		// System.out.print(sdf.format(new Date()) + " command=" +
		// this.getClass().getName() + " stage=" + stage + " {");
		// for (Map.Entry<String, Object> entry : data.entrySet()) {
		// System.out.print(entry.getKey() + "=" + entry.getValue().toString() + " ");
		// }
		//		System.out.println("}");

//		if(Robot.writer != null) {
//			Robot.writer.print(sdf.format(new Date()) + " command=" + this.getClass().getName() + " stage=" + stage + " {");
//			for (Map.Entry<String, Object> entry : data.entrySet()) {
//				Robot.writer.print(entry.getKey() + "=" + entry.getValue().toString() + " ");
//			}
//			Robot.writer.println('}');
//		}
		
		if(Robot.log != null) { //If this is too slow, can try using a StringBuffer instead of a String
			String str = sdf.format(new Date()) + " command=" + this.getClass().getName() + " stage=" + stage + " {";
			for (Map.Entry<String, Object> entry : data.entrySet()) {
				str += entry.getKey() + "=" + entry.getValue().toString() + " ";
			}
			str += "}\n";
			Robot.queuedMessages.add(str);
		}

	}

	protected void interrupted() {
		logFinished(new HashMap<String, Object>() {
			{
				put("Interrupted", "interrupted");
			}
		});
	}
	
	public static void setLoggingInterval(int interval) {
		executeLoggingInterval = interval;
	}

}
