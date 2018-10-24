package org.usfirst.frc.team670.robot.commands.intake;

import java.util.HashMap;

import org.usfirst.frc.team670.robot.Robot;
import org.usfirst.frc.team670.robot.commands.LoggingCommand;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class OpenIntake extends LoggingCommand {

	private boolean isDeploy;
	
	/*
	 * @param isDeploy true if it is to open, false if it is to close
	 */
    public OpenIntake(boolean isDeploy) {
    	this.isDeploy = isDeploy;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	logInitialize(new HashMap<String, Object>() {{
    		put("IsDeploy", isDeploy);
		}});
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.intake.deployGrabber(isDeploy);
    		logExecute(new HashMap<String, Object>() {{
    		put("IsDeploy", isDeploy);
		}});
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	logFinished(new HashMap<String, Object>() {{
    		put("IsDeploy", isDeploy);
		}});
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
