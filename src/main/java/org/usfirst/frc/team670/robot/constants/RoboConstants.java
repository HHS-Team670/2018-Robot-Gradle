package org.usfirst.frc.team670.robot.constants;

public class RoboConstants {

	// Gear ratio from motor to shaft = 10.71
	// Ratio encoder to shaft = 1.0

	//15 feet and 4.46 seconds
	//TU = Oscillation PEriod (4.46)
	//KU = (0.275)
	//P = 0.6*KU
	//D = 3*P*TU/40
	//I = 1.2*Ku/Tu
	
	// All PID Variables
	public static final int kSlotIdx = 0;
	public static final int kPIDLoopIdx = 0;
	public static final int kTimeoutMs = 10;
	public static final double KU = 0.275; //Original P value
	public static final double TU = 4.46; //Oscillation in seconds
	public static double PROPORTION = 0.2*KU;
	public static double INTEGRAL = 0;//0.05*KU/TU;
	public static double DERIVATIVE = 5*KU*TU/40;
	public static double f = 0.02;//0.03

	// Ticks for one rotation of a drivebase wheel
	public static final double DRIVEBASE_TICKS_PER_ROTATION = 4096.0;

	// Pivot radius of the robot in degrees
	public static final double PIVOT_RADIUS = 17.51;

	// Wheel velocity = inches/second at power of timeAutoSpeed;
	public static final double WHEEL_VELOCITY = 0; // THESE NEED TO BE SET
	public static final double TIME_AUTO_SPEED = 0;

	// Seconds to run intake during auto
	public static final double INTAKE_RUN_TIME = 5;

	// Distance from the front of the robot to the elevator
	public static final double FRONT_TO_ELEVATOR = 12.5;

	// Diameter of wheels in drivebase in inches
	public static final double DRIVEBASE_WHEEL_DIAMETER = 6;
	public static final double DRIVEBASE_GEAR_RATIO = 1.0;
	// Diameter of wheel in elevator in inches
	public static final double ELEVATOR_WHEEL_DIAMETER = 6; //Not sure if this is correct

	/*
	*	- command to move elevator all the way down slowly and reset encoders
	*	- soft limits
	*	- Heights:
	*	    - Exchange = low as possible
	*	    - Switch = 35
	*	    - Mid Scale = 71
	*	    - High Scale = 83
	*	    - Also do second stage
	*	    - Full height
	 */
	//Raising the elevator as we move can cause the robot
	// Elevator Heights
	public static final double ELEVATOR_PULSE_FOR_EXCHANGE = -500; // SET THESE
	public static final double ELEVATOR_PULSE_FOR_SWITCH = -3638;
	public static final double ELEVATOR_PULSE_FOR_HIGHSCALE = -9600;
	public static final double ELEVATOR_PULSE_FOR_MIDSCALE = -6000;
	public static final double ELEVATOR_PULSE_FOR_SECONDSTAGE = -5430; //In ticks
	public static final double ELEVATOR_AUTON_SPEED = 0.05; // Probably make this higher after testing

	public static final double TOP_ELEVATOR_TICKS = -10450; // SET THIS
	public static final double CEILING_ELEVATOR_TICKS = -9000;
	public static final double BOTTOM_ELEVATOR_TICKS = -100; // SET THIS
	
	public static final double ELEVATOR_TOLERANCE = 700;
	public static final double ELEVATOR_MIN_SPEED = 0.3;

	public static final double MAX_INTAKE_VOLTAGE = 10; //You're gonna want to set this, amde it nonzero to avoid repeatedly printing warning

	// Multiply by distance in inches to get needed ticks
	public static final double elevatorInchesToTicks = 8 / 3 * DRIVEBASE_TICKS_PER_ROTATION / Math.PI
			* DRIVEBASE_WHEEL_DIAMETER;
	public static final double elevatorTicksToInches = Math.PI * DRIVEBASE_WHEEL_DIAMETER
			/ (8 / 3 * DRIVEBASE_TICKS_PER_ROTATION);

	public static double elevatorTicksToGo(double inches) {
		return inches * elevatorInchesToTicks;
	}

	public static double elevatorInchesToGo(double ticks) {
		return ticks * elevatorTicksToInches;
	}

}