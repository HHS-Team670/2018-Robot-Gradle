package org.usfirst.frc.team670.robot.commands.joystick_control;

import java.util.HashMap;

import org.usfirst.frc.team670.robot.Robot;
import org.usfirst.frc.team670.robot.commands.LoggingCommand;
import org.usfirst.frc.team670.robot.constants.RoboConstants;

public class Joystick_Drive extends LoggingCommand {

	private double opL, opR, d = 0.05;	
	private double left, right;

	public Joystick_Drive() {
		requires(Robot.driveBase);
	}
	
	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.driveBase.initJoystickDrive();
		left = 0;
		right = 0;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		left = Robot.oi.getLeftStick().getY();
		right = Robot.oi.getRightStick().getY();
		
		//Elevator limiting code---------------------------------------------------------
		/*if(currentPosition < RoboConstants.ELEVATOR_PULSE_FOR_SECONDSTAGE - 500)
		{
			left *= (.0001  * (currentPosition - RoboConstants.ELEVATOR_PULSE_FOR_SECONDSTAGE) + 1);
			right *= (.0001 * (currentPosition - RoboConstants.ELEVATOR_PULSE_FOR_SECONDSTAGE) + 1);
		}*/
		//Elevator limiting code end-----------------------------------------------------
		
		
		double currentPosition = Robot.elevator.getCurrentPosition();
		
		if(currentPosition < RoboConstants.ELEVATOR_PULSE_FOR_SECONDSTAGE - 500)
		{
			left*=0.5;
			right*=0.5;
		}
		
		Robot.driveBase.drive(left, right);

		logExecute(new HashMap<String, Object>() {{
			put("LeftStickPos", Robot.oi.getLeftStick().getY());
			put("RightStickPos", Robot.oi.getRightStick().getY());
			put("LeftMotorInput", left);
			put("RightMotorInput", right);
		}});
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.driveBase.drive(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}