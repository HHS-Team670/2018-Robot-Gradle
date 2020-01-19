package frc.team670.robot.commands.joystick_control;

import java.util.HashMap;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team670.robot.Robot;
import frc.team670.robot.RobotContainer;
import frc.team670.robot.constants.enums.OperatorState;


/**
 *
 */
public class Joystick_Intake extends CommandBase {

	public Joystick_Intake() {
		addRequirements(RobotContainer.intake);
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	public void initialize() {
		RobotContainer.intake.driveIntake(0);
		
	}

	// Called repeatedly when this Command is scheduled to run
	public void execute() {
		if(RobotContainer.oi.getOS().equals(OperatorState.INTAKE))
        RobotContainer.intake.driveIntake(-RobotContainer.oi.getOperatorStick().getY());
		else
        RobotContainer.intake.driveIntake(0);
		
	}

	// Make this return true when this Command no longer needs to run execute()
	public boolean isFinished() {

			return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		RobotContainer.intake.driveIntake(0);
		
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