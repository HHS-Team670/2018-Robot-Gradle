package org.usfirst.frc.team670.robot.commands.state_change;

import java.util.HashMap;

import org.usfirst.frc.team670.robot.Robot;
import org.usfirst.frc.team670.robot.commands.LoggingCommand;
import org.usfirst.frc.team670.robot.constants.enums.DriverState;


/**
 * Flips controls for the driver when called
 * 
 * @author vsharma
 */
public class Set_DriverControl extends LoggingCommand {
	
	private DriverState ds;
	
    public Set_DriverControl(DriverState ds) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.ds = ds;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	logInitialize(new HashMap<String, Object>() {{
    		put("NewDriverState", ds.toString());
		}});
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.oi.setDriverState(ds);
    	logExecute(new HashMap<String, Object>() {{
    		put("NewDriverState", ds.toString());
		}});
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	logFinished(new HashMap<String, Object>() {{
    		put("NewDriverState", ds.toString());
		}});    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}