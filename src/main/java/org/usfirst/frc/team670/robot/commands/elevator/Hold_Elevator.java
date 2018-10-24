package org.usfirst.frc.team670.robot.commands.elevator;

import org.usfirst.frc.team670.robot.Robot;
import org.usfirst.frc.team670.robot.constants.RoboConstants;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Hold_Elevator extends Command {

	private boolean hold;
	
    public Hold_Elevator(boolean hold) {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.elevator);
    	this.hold = hold;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(hold)
    		Robot.elevator.moveElevator(-0.1);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.elevator.moveElevator(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
