package org.usfirst.frc.team670.robot.commands.elevator;

import org.usfirst.frc.team670.robot.constants.enums.ElevatorState;


/**
 *
 */
public class DelayedRaise extends Encoders_Elevator {

    public DelayedRaise(ElevatorState state) {
       super(state);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	super.initialize();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	super.execute();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return super.isFinished();
    }

    // Called once after isFinished returns true
    protected void end() {
    	super.end();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	super.interrupted();
    }
}
