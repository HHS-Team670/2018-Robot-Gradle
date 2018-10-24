package org.usfirst.frc.team670.robot.commands.joystick_control;

import java.util.HashMap;

import org.usfirst.frc.team670.robot.Robot;
import org.usfirst.frc.team670.robot.commands.LoggingCommand;
import org.usfirst.frc.team670.robot.constants.RoboConstants;
import org.usfirst.frc.team670.robot.constants.enums.OperatorState;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Joystick_Elevator extends LoggingCommand {

	private double speed;
	private boolean isGoingUp;
	
	public Joystick_Elevator() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.elevator);
		speed = 0;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		// Robot.elevator.resetEncoder();
		// Robot.elevator.toggleSoftLimits(true);
		logInitialize(new HashMap<String, Object>() {{
		}});
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		speed = Robot.oi.getOperatorStick().getY();
		
		//Speed is negative meaning it goes up
		isGoingUp = speed <= 0;
		speed = Robot.elevator.calculateSpeed((int) Robot.elevator.getCurrentPosition(),Robot.oi.getOperatorStick().getY(), isGoingUp);
		if (Robot.oi.getOS().equals(OperatorState.ELEVATOR))
		{
			Robot.elevator.moveElevator(speed);
		}
		else
			Robot.elevator.moveElevator(0);
		
		logExecute(new HashMap<String, Object>() {{
			put("OperatorStickPos", Robot.oi.getOperatorStick().getY());
			put("Speed", speed);
			put("IsGoingUp", isGoingUp);
			if(Robot.elevator.getCurrentPosition() > RoboConstants.BOTTOM_ELEVATOR_TICKS) {
				put("TicksPastSoftLimit", Robot.elevator.getCurrentPosition() + RoboConstants.BOTTOM_ELEVATOR_TICKS);
			}
			else if(Robot.elevator.getCurrentPosition() < RoboConstants.TOP_ELEVATOR_TICKS) {
				put("TicksPastSoftLimit", Robot.elevator.getCurrentPosition() + RoboConstants.TOP_ELEVATOR_TICKS);
			}
		}});
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.elevator.moveElevator(0);
		logFinished(new HashMap<String, Object>() {{
			if(Robot.elevator.getCurrentPosition() > RoboConstants.BOTTOM_ELEVATOR_TICKS) {
				put("TicksPastSoftLimit", Robot.elevator.getCurrentPosition() + RoboConstants.BOTTOM_ELEVATOR_TICKS);
			}
			else if(Robot.elevator.getCurrentPosition() < RoboConstants.TOP_ELEVATOR_TICKS) {
				put("TicksPastSoftLimit", Robot.elevator.getCurrentPosition() + RoboConstants.TOP_ELEVATOR_TICKS);
			}
		}});	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}