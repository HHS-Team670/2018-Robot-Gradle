package frc.team670.robot.subsystems;

import frc.team670.robot.Robot;
import frc.team670.robot.RobotContainer;
import frc.team670.robot.commands.elevator.Joystick_Elevator;
import frc.team670.robot.constants.Constants;
import frc.team670.robot.constants.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Elevator extends SubsystemBase {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	private TalonSRX elevator;
	private SensorCollection encoder;
	private boolean toggle;

	public Elevator() {
		elevator = new TalonSRX(RobotMap.elevatorMotor);
		encoder = new SensorCollection(elevator);
		encoder.setQuadraturePosition(0, 0);
		elevator.setNeutralMode(NeutralMode.Brake);
		elevator.configOpenloopRamp(0.25, 0);
		elevator.configForwardSoftLimitEnable(false, 0);
		elevator.configReverseSoftLimitEnable(false, 0);
		toggle = true;
//		toggleSoftLimits(true);
	}

	public boolean getSoftLimits() {
		return toggle;
	}
	
	public void toggleSoftLimits(boolean b) {
		this.toggle = b;
	}

	public double getCurrentPosition() {
		return encoder.getQuadraturePosition();
	}

	public double getCurrentVelocity() {
		return encoder.getQuadratureVelocity();
	}

	public void resetEncoder() {
		encoder.setPulseWidthPosition(0, 0);
		encoder.setAnalogPosition(0, 0);
		encoder.setQuadraturePosition(0, 0);
	}

	public void moveElevator(double speed) {
		if (RobotContainer.intake.isIntakeDeployed()) {
			if (toggle) {
				// Limit is on
				if (getCurrentPosition() > Constants.BOTTOM_ELEVATOR_TICKS) {
					// Speed is negative, so going up
					if (speed > 0)
						elevator.set(ControlMode.PercentOutput, 0);
					else
						elevator.set(ControlMode.PercentOutput, speed);
				} else if (getCurrentPosition() < Constants.TOP_ELEVATOR_TICKS) {
					// Speed is positive, so going down
					if (speed < 0)
						elevator.set(ControlMode.PercentOutput, 0);
					else
						elevator.set(ControlMode.PercentOutput, speed);
				}

				else {
					elevator.set(ControlMode.PercentOutput, speed);
				}
			} else
				elevator.set(ControlMode.PercentOutput, speed);
		} else
			elevator.set(ControlMode.PercentOutput, 0);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
        CommandScheduler.getInstance().setDefaultCommand(this, new Joystick_Elevator());
	}

	/**
	 * 
	 * @param currentTicks
	 * @param maxSpeed
	 * @param goingUp
	 * @return
	 */
	public double calculateSpeed(double currentTicks, double maxSpeed, boolean goingUp) {
		
		if(!toggle)
			return maxSpeed;
		
		double RATIO_TICKS_COVERED_TO_VELOCITY_UP = 220.0/136.0;
		double RATIO_TICKS_COVERED_TO_VELOCITY_DOWN = 540.0/350.0;
		double ACCELERATION_TOLERANCE_UP = 0.75;
		double ACCELERATION_TOLERANCE_DOWN = 0.75;
		double MAX_VELOCITY_UP = 700;
		double MAX_VELOCITY_DOWN = 350;
		double COAST_DISTANCE_MAX_SPEED_UP = 800; //When the 0.1 braking speed changes, the value for coasting at max speed changes
		double COAST_DISTANCE_MAX_SPEED_DOWN = 800; //When the 0.1 braking speed changes, the value for coasting at max speed changes
		double currentVelocity = RobotContainer.elevator.getCurrentVelocity();
		
		double tolerance = 0;
		
		if(goingUp)
			tolerance = Math.abs(currentVelocity)*RATIO_TICKS_COVERED_TO_VELOCITY_UP*ACCELERATION_TOLERANCE_UP + (Math.abs(currentVelocity)/MAX_VELOCITY_UP) * COAST_DISTANCE_MAX_SPEED_UP;
		else
			tolerance = Math.abs(currentVelocity)*RATIO_TICKS_COVERED_TO_VELOCITY_DOWN*ACCELERATION_TOLERANCE_DOWN + (Math.abs(currentVelocity)/MAX_VELOCITY_DOWN) * COAST_DISTANCE_MAX_SPEED_DOWN;
		
		double minSpeed = goingUp ? -Constants.ELEVATOR_MIN_SPEED : Constants.ELEVATOR_MIN_SPEED;
		double speed = 0;
		
		double MIDDLE_TOLERANCE = Constants.ELEVATOR_TOLERANCE; //Tolerance constant for the second stage and bottom
		
		// if (maxSpeed < minSpeed)
		// return minSpeed;

		// MIN_ELEVATOR_TICKS is the top physical point on the elevator, so
		// it's
		// the most negative value
		if (currentTicks > Constants.BOTTOM_ELEVATOR_TICKS - tolerance && !goingUp) {
			//speed = ((currentTicks / (Constants.BOTTOM_ELEVATOR_TICKS - MIDDLE_TOLERANCE)) * maxSpeed) / 3;
			speed = 0.1;
		}

		else if (currentTicks > Constants.ELEVATOR_PULSE_FOR_SECONDSTAGE - MIDDLE_TOLERANCE
				&& currentTicks < Constants.ELEVATOR_PULSE_FOR_SECONDSTAGE + MIDDLE_TOLERANCE) {

			speed = ((Math.abs(Constants.ELEVATOR_PULSE_FOR_SECONDSTAGE - currentTicks) / MIDDLE_TOLERANCE) * maxSpeed);
			if (Math.abs(speed) < Math.abs(minSpeed))
				speed = (1.75*minSpeed);
		}

		// MAX_ELEVATOR_TICKS is the lowest physical point on the elevator, so
		// it's the most positive value
		else if (currentTicks < Constants.TOP_ELEVATOR_TICKS + tolerance && goingUp) 
		{
			//speed = ((Math.abs(Constants.TOP_ELEVATOR_TICKS - currentTicks) / tolerance) * maxSpeed) / 3;
			speed = -0.1;
		} 
		else
			speed = maxSpeed;

		if(Math.abs(speed) > 0.2 && ((goingUp && currentTicks <= Constants.TOP_ELEVATOR_TICKS + 200) || (!goingUp && currentTicks >= Constants.BOTTOM_ELEVATOR_TICKS -  400)))
		{
			speed = (goingUp)? -0.2 : 0.2;			
		}
		
//		//if (Math.abs(speed) < Math.abs(minSpeed))
//			//speed = minSpeed;
//		System.out.print(" " + speed);
//		System.out.print(", "+ goingUp);
//		System.out.print(", " + maxSpeed);
//		System.out.print(", " + currentTicks);
//		System.out.print(", " + currentVelocity);
//		System.out.println(", " + tolerance);
		System.out.println(speed);
		return speed;

	}
}
