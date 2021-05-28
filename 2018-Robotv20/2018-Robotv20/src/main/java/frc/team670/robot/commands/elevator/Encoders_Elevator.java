package frc.team670.robot.commands.elevator;

import java.util.HashMap;

import frc.team670.robot.Robot;
import frc.team670.robot.RobotContainer;
import frc.team670.robot.constants.Constants;
import frc.team670.robot.constants.enums.ElevatorState;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * 
 * Uses a PID control loop plus the navX getDisplacement to move a given
 * distance in feet
 * 
 * @author vsharma8363
 *
 */
public class Encoders_Elevator extends CommandBase {

	private double targetPulseHeight, speed;
	private boolean isGoingUp;
	
	public Encoders_Elevator(ElevatorState state) {
		addRequirements(RobotContainer.elevator);
		if (state == ElevatorState.EXCHANGE)
			targetPulseHeight = Constants.ELEVATOR_PULSE_FOR_EXCHANGE;
		else if (state.equals(ElevatorState.SWITCH))
			//Caleb was here

			targetPulseHeight = Constants.ELEVATOR_PULSE_FOR_SWITCH;
		else if (state.equals(ElevatorState.HIGHSCALE))
			targetPulseHeight = Constants.ELEVATOR_PULSE_FOR_HIGHSCALE;
		else if (state.equals(ElevatorState.EXCHANGE))
			targetPulseHeight = Constants.ELEVATOR_PULSE_FOR_EXCHANGE;
		else if (state.equals(ElevatorState.CLEARANCE))
			targetPulseHeight = Constants.ELEVATOR_PULSE_FOR_MIDSCALE;
		else
			targetPulseHeight = Constants.ELEVATOR_PULSE_FOR_EXCHANGE;
	}

	// Called just before this Command runs the first time
	public void initialize() {
		isGoingUp = (targetPulseHeight <= RobotContainer.elevator.getCurrentPosition());
		// logInitialize(new HashMap<String, Object>() {{
		// 	put("Speed", speed);
		// 	put("TargetPulseHeight", targetPulseHeight);
		// 	put("IsGoingUp", isGoingUp);
		// }});
	}

	// Called repeatedly when this Command is scheduled to run
	public void execute() {
		if(isGoingUp)
		{
			speed = RobotContainer.elevator.calculateSpeed((int) RobotContainer.elevator.getCurrentPosition(), -1.0, isGoingUp);
		}
		else
		{
			speed = RobotContainer.elevator.calculateSpeed((int) RobotContainer.elevator.getCurrentPosition(), 1.0, isGoingUp);
		}		
		RobotContainer.elevator.moveElevator(speed);
		
		// logExecute(new HashMap<String, Object>() {{
		// 	put("Speed", speed);
		// 	put("Velocity", RobotContainer.elevator.getCurrentVelocity());
		// 	put("IsGoingUp", isGoingUp);
		// 	if(RobotContainer.elevator.getCurrentPosition() > Constants.BOTTOM_ELEVATOR_TICKS) {
		// 		put("TicksPastSoftLimit", RobotContainer.elevator.getCurrentPosition() + Constants.BOTTOM_ELEVATOR_TICKS);
		// 	}
		// 	else if(RobotContainer.elevator.getCurrentPosition() < Constants.TOP_ELEVATOR_TICKS) {
		// 		put("TicksPastSoftLimit", RobotContainer.elevator.getCurrentPosition() + Constants.TOP_ELEVATOR_TICKS);
		// 	}
		// }});
	}

	// Make this return true when this Command no longer needs to run execute()
	public boolean isFinished() {
		boolean isFinished = (isGoingUp && RobotContainer.elevator.getCurrentPosition() <= targetPulseHeight)
				|| (!isGoingUp && RobotContainer.elevator.getCurrentPosition() >= targetPulseHeight);
		return isFinished;
	}

	// Called once after isFinished returns true
	protected void end() {
		//Ben too
		RobotContainer.elevator.moveElevator(0);
		
		// logFinished(new HashMap<String, Object>() {{
		// 	put("Speed", speed);
		// 	put("IsGoingUp", isGoingUp);
		// 	if(RobotContainer.elevator.getCurrentPosition() > Constants.BOTTOM_ELEVATOR_TICKS) {
		// 		put("TicksPastSoftLimit", RobotContainer.elevator.getCurrentPosition() + Constants.BOTTOM_ELEVATOR_TICKS);
		// 	}
		// 	else if(RobotContainer.elevator.getCurrentPosition() < Constants.TOP_ELEVATOR_TICKS) {
		// 		put("TicksPastSoftLimit", RobotContainer.elevator.getCurrentPosition() + Constants.TOP_ELEVATOR_TICKS);
		// 	}
		// }});
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}

}