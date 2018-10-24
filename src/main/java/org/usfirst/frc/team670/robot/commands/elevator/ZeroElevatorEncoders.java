package org.usfirst.frc.team670.robot.commands.elevator;

import java.util.HashMap;

import org.usfirst.frc.team670.robot.Robot;
import org.usfirst.frc.team670.robot.commands.LoggingCommand;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Allows us to move the elevator position to the bottom of the elevator and
 * reset encoder ticks to zero
 */
public class ZeroElevatorEncoders extends LoggingCommand {

	public ZeroElevatorEncoders() {
		requires(Robot.elevator);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.elevator.toggleSoftLimits(false);
		logInitialize(new HashMap<String, Object>() {{
		}});
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.elevator.moveElevator(0.15);
		logInitialize(new HashMap<String, Object>() {{
		}});
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return Robot.elevator.getCurrentVelocity() < 0.05;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.elevator.toggleSoftLimits(true);
		Robot.elevator.resetEncoder();
		Robot.elevator.moveElevator(0);
		logFinished(new HashMap<String, Object>() {{
		}});
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.elevator.moveElevator(0);
	}
}