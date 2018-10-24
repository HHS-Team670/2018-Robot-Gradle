package org.usfirst.frc.team670.robot.commands.drive;

import org.usfirst.frc.team670.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Encoders_Calibration extends Command {

	private double startEncoders;
	
    public Encoders_Calibration() {
        requires(Robot.driveBase);
        requires(Robot.intake);
        requires(Robot.climber);
        requires(Robot.elevator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveBase.drive(0, 0);
    	Robot.climber.climb(0);
    	Robot.intake.driveIntake(0);
    	Robot.elevator.moveElevator(0);
    	startEncoders = Robot.driveBase.getLeft().getSensorCollection().getPulseWidthPosition();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putString("EncodersDifference", (Robot.driveBase.getLeft().getSensorCollection().getPulseWidthPosition() - startEncoders) + "");
    	SmartDashboard.putString("NavX", (Robot.getYaw()) + "");
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
