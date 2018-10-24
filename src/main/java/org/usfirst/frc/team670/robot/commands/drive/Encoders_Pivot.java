package org.usfirst.frc.team670.robot.commands.drive;

import java.util.HashMap;

import org.usfirst.frc.team670.robot.Robot;
import org.usfirst.frc.team670.robot.commands.LoggingCommand;
import org.usfirst.frc.team670.robot.constants.RoboConstants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * 
 * Uses a PID control loop plus the navX getDisplacement to move a given
 * distance in feet
 * 
 * @author vsharma8363
 *
 */
public class Encoders_Pivot extends LoggingCommand {

	private double ticksToTravel, minVelocity = 500, finishedVelocity = 100;
	private int numTimesMotorOutput;
	private boolean reachedMinSpeed;
	private final double TICKS_PER_DEGREE = 4600 / 90;

	public Encoders_Pivot(double angle) {
		ticksToTravel = TICKS_PER_DEGREE * angle;
		numTimesMotorOutput = 0;
		reachedMinSpeed = false;
		/*
		 * double circum = Ma th.PI*RoboConstants.PIVOT_RADIUS; double inches =
		 * circum*(angle/360.0); this.ticksToTravel =
		 * ((inches)/(Math.PI*RoboConstants.DRIVEBASE_WHEEL_DIAMETER)) *
		 * RoboConstants.DRIVEBASE_TICKS_PER_ROTATION;
		 */
		requires(Robot.driveBase);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		setTimeout(2);
		numTimesMotorOutput = 0;
		Robot.driveBase.initPIDPivoting(Robot.driveBase.getLeft());
		Robot.driveBase.initPIDPivoting(Robot.driveBase.getRight());
		logInitialize(new HashMap<String, Object>() {{
		    put("TicksToTravel", ticksToTravel);
		    put("DegreesToTravel", ticksToTravel / TICKS_PER_DEGREE);
		    put("LeftEncoderTicks", Robot.driveBase.getLeft().getSensorCollection().getQuadraturePosition());
		    put("RightEncoderTicks", Robot.driveBase.getRight().getSensorCollection().getQuadraturePosition());
		}});
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.driveBase.getLeft().set(ControlMode.Position, -ticksToTravel);
		Robot.driveBase.getRight().set(ControlMode.Position, ticksToTravel);
		if (!reachedMinSpeed)
			reachedMinSpeed = Math
					.abs(Robot.driveBase.getLeft().getSensorCollection().getQuadratureVelocity()) > minVelocity;
		/* 50 rotations in either direction */
		logExecute(new HashMap<String, Object>() {{
		    put("TicksToTravel", ticksToTravel);
		    put("ReachedMinSpeed", reachedMinSpeed);
		    put("LeftEncoderTicks", Robot.driveBase.getLeft().getSensorCollection().getQuadraturePosition());
		    put("RightEncoderTicks", Robot.driveBase.getRight().getSensorCollection().getQuadraturePosition());
		    put("LeftEncoderVelocity", Robot.driveBase.getLeft().getSensorCollection().getQuadratureVelocity());
		    put("RightEncoderVelocity", Robot.driveBase.getRight().getSensorCollection().getQuadratureVelocity());
		}});
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (Math.abs(Robot.driveBase.getRight().getSensorCollection().getQuadratureVelocity()) <= finishedVelocity
				&& Math.abs(
						Robot.driveBase.getRight().getSensorCollection().getQuadratureVelocity()) <= finishedVelocity
				&& reachedMinSpeed)
			numTimesMotorOutput++;
		return (numTimesMotorOutput >= 2);
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.driveBase.drive(0, 0);
		logExecute(new HashMap<String, Object>() {{
		    put("DegreesToTravel", ticksToTravel);
		    put("ReachedMinSpeed", reachedMinSpeed);
		    put("LeftEncoderTicks", Robot.driveBase.getLeft().getSensorCollection().getQuadraturePosition());
		    put("RightEncoderTicks", Robot.driveBase.getRight().getSensorCollection().getQuadraturePosition());
		    put("LeftEncoderTicks", Robot.driveBase.getLeft().getSensorCollection().getQuadratureVelocity());
		    put("RightEncoderTicks", Robot.driveBase.getRight().getSensorCollection().getQuadratureVelocity());
		}});
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}