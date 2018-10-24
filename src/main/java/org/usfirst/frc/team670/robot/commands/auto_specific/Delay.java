package org.usfirst.frc.team670.robot.commands.auto_specific;

import java.util.HashMap;

import org.usfirst.frc.team670.robot.Robot;
import org.usfirst.frc.team670.robot.commands.LoggingCommand;


/**
 *
 */
public class Delay extends LoggingCommand {
	private double seconds = 0;

	public Delay(double seconds) {
		this.seconds = seconds;
		// requires(Robot.driveBase);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		setTimeout(seconds);
		logInitialize(new HashMap<String, Object>() {{
			put("Seconds", seconds);
		}});
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		logExecute(new HashMap<String, Object>() {{
			put("Seconds", seconds);
		}});	    	
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		logFinished(new HashMap<String, Object>() {{
			put("Seconds", seconds);
		}});
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}