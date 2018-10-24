package org.usfirst.frc.team670.robot.commands.joystick_control;


import java.util.HashMap;

import org.usfirst.frc.team670.robot.Robot;
import org.usfirst.frc.team670.robot.commands.LoggingCommand;
import org.usfirst.frc.team670.robot.constants.enums.OperatorState;


/**
 *
 */
public class Joystick_Climber extends LoggingCommand {

	public Joystick_Climber() {
		requires(Robot.climber);
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.climber.climb(0);
		logInitialize(new HashMap<String, Object>() {{
		}});
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if(Robot.oi.getOS().equals(OperatorState.CLIMBER))
			Robot.climber.climb(-Math.abs(Robot.oi.getOperatorStick().getY())); // removed math.abs
		else
			Robot.climber.climb(0);
		
		logExecute(new HashMap<String, Object>() {{
			put("OperatorStickPos", Robot.oi.getOperatorStick().getY());
		}});
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
//		if(current >= currentLimit)
//		{
//			stopped = true;
//			working = false;
//			return true;
//		}
//		else
			return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.climber.climb(0);
		logFinished(new HashMap<String, Object>() {{
		}});
	}
	
//	public static boolean isWorking()
//	{
//		return working;
//	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}