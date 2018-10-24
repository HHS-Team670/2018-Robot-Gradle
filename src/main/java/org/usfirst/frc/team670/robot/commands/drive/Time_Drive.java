package org.usfirst.frc.team670.robot.commands.drive;

import java.util.HashMap;

import org.usfirst.frc.team670.robot.Robot;
import org.usfirst.frc.team670.robot.commands.LoggingCommand;

/**
 *
 */
public class Time_Drive extends LoggingCommand {
	  private double speed = 0, seconds = 0;
	    
	    public Time_Drive(double seconds, double speed) {
	        this.speed = speed;
	        this.seconds = seconds;
	        requires(Robot.driveBase);
	    }

	    // Called just before this Command runs the first time
	    protected void initialize() {
	        setTimeout(seconds);
			logInitialize(new HashMap<String, Object>() {{
			    put("Speed", speed);
			    put("Seconds", seconds);
			}});
	    }

	    // Called repeatedly when this Command is scheduled to run
	    protected void execute() {
	    	//Drive seven feet to baseline
	    	Robot.driveBase.drive(speed, speed);
	    	logExecute(new HashMap<String, Object>() {{
			    put("Speed", speed);
			    put("Seconds", seconds);
			}});
	    }

	    // Make this return true when this Command no longer needs to run execute()
	    protected boolean isFinished() {
	        return isTimedOut();
	    }

	    // Called once after isFinished returns true
	    protected void end() {
	    	Robot.driveBase.drive(0, 0);
	    	logFinished(new HashMap<String, Object>() {{
			    put("Speed", speed);
			    put("Seconds", seconds);
			}});
	    }

	    // Called when another command which requires one or more of the same
	    // subsystems is scheduled to run
	    protected void interrupted() {
	    	Robot.driveBase.drive(0, 0);
	    }
	}