package frc.team670.robot.commands.intake;

import java.util.HashMap;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team670.robot.Robot;

import frc.team670.robot.RobotContainer;


/**
 *
 */
public class Grab extends CommandBase {

	private boolean isDeploy;
	
	/*
	 * @param isDeploy true if it is the deploy, false if it is to pick up
	 */
    public Grab(boolean isDeploy) {
    	this.isDeploy = isDeploy;
    }

    // Called just before this Command runs the first time
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    public void execute() {
    	RobotContainer.intake.deployGrabber(isDeploy);
    }

    // Make this return true when this Command no longer needs to run execute()
    public boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
