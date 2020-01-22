/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team670.robot.subsystems;

import frc.team670.robot.Robot;
import frc.team670.robot.commands.teleop.XboxRocketLeagueDrive;
import frc.team670.robot.constants.Constants;
import frc.team670.robot.constants.RobotMap;

import java.util.ArrayList;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveBase extends SubsystemBase {
    
    private WPI_TalonSRX left1, right1, left2, right2;
    private SpeedControllerGroup m_left, m_right;
    private DifferentialDrive driveTrain;


	// PID VALUES
	/**
	 * Creates the drivetrain, assuming that there are four talons.
	 * 
	 * @param fl
	 *            Front-left Talon ID
	 * @param fr
	 *            Front-right Talon ID
	 * @param bl
	 *            Back-left Talon ID
	 * @param br
	 *            Back-right Talon ID
	 */
	public DriveBase() {
		left1 = new WPI_TalonSRX(RobotMap.leftMotor1);
		left2 = new WPI_TalonSRX(RobotMap.leftMotor2);
		right1 = new WPI_TalonSRX(RobotMap.rightMotor1);
		right2 = new WPI_TalonSRX(RobotMap.rightMotor2);

		left1.setInverted(false);
		left2.setInverted(false);
		right1.setInverted(false);
		right2.setInverted(false);

		left1.setSensorPhase(true);
		left2.setSensorPhase(true);
		right1.setSensorPhase(false);
        right2.setSensorPhase(false);
        
        left1.setNeutralMode(NeutralMode.Brake);
		left2.setNeutralMode(NeutralMode.Brake);
		right1.setNeutralMode(NeutralMode.Brake);
		right2.setNeutralMode(NeutralMode.Brake);

		// Set follower control on back talons.
		left2.set(ControlMode.Follower, RobotMap.leftMotor1);
        right2.set(ControlMode.Follower, RobotMap.rightMotor1);

        m_left = new SpeedControllerGroup(left1, left2);
        m_right = new SpeedControllerGroup(right1, right2);

        driveTrain = new DifferentialDrive(m_left, m_right);
	}

	/**
	 * Gets the Talon based on the ID.
	 * 
	 * @param id
	 *            The device ID of the Talon.
	 * @return The Talon bound to the ID port, or {@code null} if no drivetrain
	 *         Talon was found.
	 * 
	 * @see CAN RobotMap.CAN
	 */
	public TalonSRX getTalon(int id) {
		switch (id) {
		case RobotMap.leftMotor1:
			return left1;
		case RobotMap.rightMotor1:
			return right1;
		case RobotMap.leftMotor2:
			return left2;
		case RobotMap.rightMotor2:
			return right2;
		default: // Not a drivetrain Talon!
			return null;
		}
	}

	public void initJoystickDrive() {
		left1.setNeutralMode(NeutralMode.Coast);
		left2.setNeutralMode(NeutralMode.Coast);
		right1.setNeutralMode(NeutralMode.Coast);
		right2.setNeutralMode(NeutralMode.Coast);
	}

	    /**
    * 
    * Drives the Robot using a curvature drive configuration (wheel)
    * 
    * @param xSpeed      The forward throttle speed [-1, 1]
    * @param zRotation   The amount of rotation to turn [-1, 1] with positive being
    *                    right
    * @param isQuickTurn If true enables turning in place and running one side
    *                    backwards to turn faster
    */
    public void curvatureDrive(double xSpeed, double zRotation, boolean isQuickTurn) {
        driveTrain.curvatureDrive(xSpeed, zRotation, isQuickTurn);
    }
    
    /**
     * @param left Speed for left motors [-1.0, 1.0]
     * @param right Speed for right motors [-1.0, 1.0]
     */
    public void tankDrive(double left, double right) {
        tankDrive(left, right, false);
    }

    /**
     * 
     * Drives the Robot using a tank drive configuration (two joysticks, or auton)
     * 
     * @param leftSpeed     Speed for left side of drive base [-1, 1]
     * @param rightSpeed    Speed for right side of drive base [-1, 1]
     * @param squaredInputs If true, decreases sensitivity at lower inputs
     */
    public void tankDrive(double leftSpeed, double rightSpeed, boolean squaredInputs) {
        driveTrain.tankDrive(leftSpeed, rightSpeed, squaredInputs);
    }

	public TalonSRX getLeft() {
		return left1;
	}

	public TalonSRX getRight() {
		return right1;
	}

	public void resetEncoders() {
		left1.getSensorCollection().setQuadraturePosition(0, 0);
		right1.getSensorCollection().setQuadraturePosition(0, 0);
	}

	/**
	 * Stops the motors by zeroing the left and right Talons.
	 */
	public void stop() {
		left1.set(ControlMode.Velocity, 0);
		right1.set(ControlMode.Velocity, 0);
	}

	public void initDefaultCommand() {
		CommandScheduler.getInstance().setDefaultCommand(this, new XboxRocketLeagueDrive());
	}

	public double getLeftEncPositionInFeet() {
		double ticks = left1.getSensorCollection().getQuadraturePosition();
		// Convert encoder ticks to feet
		return ((Math.PI * Constants.DRIVEBASE_WHEEL_DIAMETER)
				/ (Constants.DRIVEBASE_TICKS_PER_ROTATION * Constants.DRIVEBASE_GEAR_RATIO) * ticks) / 12;
	}

	public double getRightEncPositionInFeet() {
		double ticks = right1.getSensorCollection().getQuadraturePosition();
		// Convert encoder ticks to feet
		return ((Math.PI * Constants.DRIVEBASE_WHEEL_DIAMETER)
				/ (Constants.DRIVEBASE_TICKS_PER_ROTATION * Constants.DRIVEBASE_GEAR_RATIO) * ticks) / 12;
	}

	/**
	 * <pre>
	 * s
	 * public double feetToEncoderTicks(double feet)
	 * </pre>
	 * 
	 * Returns a value in ticks based on a certain value in feet using the Magnetic
	 * Encoder.
	 * 
	 * @param feet
	 *            The value in feet
	 * @return The value in ticks
	 */
	public double feetToEncoderTicks(double feet) {
		return (Constants.DRIVEBASE_TICKS_PER_ROTATION * Constants.DRIVEBASE_GEAR_RATIO)
				/ (Math.PI * Constants.DRIVEBASE_WHEEL_DIAMETER) * feet;
	}

	public void initPID(TalonSRX talon) {
		left1.setNeutralMode(NeutralMode.Brake);
		left2.setNeutralMode(NeutralMode.Brake);
		right1.setNeutralMode(NeutralMode.Brake);
		right2.setNeutralMode(NeutralMode.Brake);

		int absolutePosition = talon.getSelectedSensorPosition(Constants.kTimeoutMs)
				& 0xFFF; /*
							 * mask out the bottom12 bits, we don't care about the wrap arounds
							 */
		/* use the low level API to set the quad encoder signal */
		talon.setSelectedSensorPosition(absolutePosition, Constants.kPIDLoopIdx, Constants.kTimeoutMs);

		/* choose the sensor and sensor direction */
		talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, Constants.kPIDLoopIdx,
				Constants.kTimeoutMs);
		talon.setSensorPhase(true);

		/* set the peak and nominal outputs, 12V means full */
		talon.configNominalOutputForward(0, Constants.kTimeoutMs);
		talon.configNominalOutputReverse(0, Constants.kTimeoutMs);
		talon.configPeakOutputForward(1, Constants.kTimeoutMs);
		talon.configPeakOutputReverse(-1, Constants.kTimeoutMs);
		talon.configClosedloopRamp(1, 0);
		/*
		 * set the allowable closed-loop error, Closed-Loop output will be neutral
		 * within this range. See Table in Section 17.2.1 for native units per rotation.
		 */
		talon.configAllowableClosedloopError(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs); /* always servo */
		/* set closed loop gains in slot0 */
		talon.config_kF(Constants.kPIDLoopIdx, Constants.f, Constants.kTimeoutMs);
		talon.config_kP(Constants.kPIDLoopIdx, Constants.PROPORTION, Constants.kTimeoutMs);
		talon.config_kI(Constants.kPIDLoopIdx, Constants.INTEGRAL, Constants.kTimeoutMs);
		talon.config_kD(Constants.kPIDLoopIdx, Constants.DERIVATIVE, Constants.kTimeoutMs);
	}
	
	public void initPIDPivoting(TalonSRX talon) {
		left1.setNeutralMode(NeutralMode.Brake);
		left2.setNeutralMode(NeutralMode.Brake);
		right1.setNeutralMode(NeutralMode.Brake);
		right2.setNeutralMode(NeutralMode.Brake);

		int absolutePosition = talon.getSelectedSensorPosition(Constants.kTimeoutMs)
				& 0xFFF; /*
							 * mask out the bottom12 bits, we don't care about the wrap arounds
							 */
		/* use the low level API to set the quad encoder signal */
		talon.setSelectedSensorPosition(absolutePosition, Constants.kPIDLoopIdx, Constants.kTimeoutMs);

		/* choose the sensor and sensor direction */
		talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, Constants.kPIDLoopIdx,
				Constants.kTimeoutMs);
		talon.setSensorPhase(true);

		/* set the peak and nominal outputs, 12V means full */
		talon.configNominalOutputForward(0, Constants.kTimeoutMs);
		talon.configNominalOutputReverse(0, Constants.kTimeoutMs);
		talon.configPeakOutputForward(1, Constants.kTimeoutMs);
		talon.configPeakOutputReverse(-1, Constants.kTimeoutMs);
		//talon.configClosedloopRamp(1, 0);
		/*
		 * set the allowable closed-loop error, Closed-Loop output will be neutral
		 * within this range. See Table in Section 17.2.1 for native units per rotation.
		 */
		talon.configAllowableClosedloopError(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs); /* always servo */
		/* set closed loop gains in slot0 */
		talon.config_kF(Constants.kPIDLoopIdx, 0.0, Constants.kTimeoutMs);
		talon.config_kP(Constants.kPIDLoopIdx, 0.125, Constants.kTimeoutMs);
		talon.config_kI(Constants.kPIDLoopIdx, 0, Constants.kTimeoutMs);
		talon.config_kD(Constants.kPIDLoopIdx, 0.15, Constants.kTimeoutMs);
	}

}