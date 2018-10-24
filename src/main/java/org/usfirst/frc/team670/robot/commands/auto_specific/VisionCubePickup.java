package org.usfirst.frc.team670.robot.commands.auto_specific;

import java.util.HashMap;

import org.usfirst.frc.team670.robot.Robot;
import org.usfirst.frc.team670.robot.commands.LoggingCommand;
import org.usfirst.frc.team670.robot.commands.drive.IR_Drive;
import org.usfirst.frc.team670.robot.commands.drive.Pivot;
import org.usfirst.frc.team670.robot.commands.intake.OpenIntake;
import org.usfirst.frc.team670.robot.commands.intake.SpinIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class VisionCubePickup extends LoggingCommand {

	private CommandGroup com;
	
    public VisionCubePickup() {
        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    		com = new CommandGroup();
    		logInitialize(new HashMap<String, Object>() {{
		}});
    		com.addSequential(new OpenIntake(true));
    		com.addSequential(new Delay(0.25));
    		double angle = Robot.sensors.getAngleToCube();
    		com.addParallel(new SpinIntake(-0.5, 10));
    		if(Math.abs(angle) < 30) {
    			if(Math.abs(angle) > 2) {
    				com.addSequential(new Pivot(angle));
    			}
    			com.addSequential(new IR_Drive());
    			com.addSequential(new OpenIntake(false));
    			com.addSequential(new SpinIntake(0, 0));
    		}
    		com.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	logFinished(new HashMap<String, Object>() {{
		}});
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
