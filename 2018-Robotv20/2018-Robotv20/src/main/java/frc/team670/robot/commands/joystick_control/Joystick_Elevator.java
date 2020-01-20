package frc.team670.robot.commands.joystick_control;

import java.util.HashMap;

import frc.team670.robot.Robot;
import frc.team670.robot.RobotContainer;
import frc.team670.robot.constants.Constants;
import frc.team670.robot.constants.enums.OperatorState;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 *
 */
public class Joystick_Elevator extends CommandBase{

	private double speed;
	private boolean isGoingUp;
	
	public Joystick_Elevator() {
		// Use requires() here to declare subsystem dependencies
		addRequirements(RobotContainer.elevator);
		speed = 0;
	}

	// Called repeatedly when this Command is scheduled to run
	public void execute() {
		speed = RobotContainer.oi.getDriverController().getRightStickY();
		
		//Speed is negative meaning it goes up
		isGoingUp = speed <= 0;
		speed = RobotContainer.elevator.calculateSpeed((int) RobotContainer.elevator.getCurrentPosition(),RobotContainer.oi.getDriverController().getRightStickY(), isGoingUp);
		RobotContainer.elevator.moveElevator(speed);
	}

	// Make this return true when this Command no longer needs to run execute()
	public boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	public void end() {
		RobotContainer.elevator.moveElevator(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}