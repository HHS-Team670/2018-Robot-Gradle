package org.usfirst.frc.team670.robot.commands;

import java.util.HashMap;

import org.usfirst.frc.team670.robot.Robot;



/**
 *@author vsharma
 */
public class CancelCommand extends LoggingCommand {

    public CancelCommand() {
        requires(Robot.driveBase);
        requires(Robot.intake);
        requires(Robot.elevator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	logInitialize(new HashMap<String, Object>() {{
		}});
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveBase.drive(0, 0);
    	Robot.intake.driveIntake(0);
    	Robot.elevator.moveElevator(0);
    	logExecute(new HashMap<String, Object>() {{
		}});
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveBase.drive(0, 0);
    	Robot.intake.driveIntake(0);
    	Robot.elevator.moveElevator(0);
    	logFinished(new HashMap<String, Object>() {{
		}});
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
