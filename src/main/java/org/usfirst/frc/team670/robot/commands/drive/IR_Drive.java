package org.usfirst.frc.team670.robot.commands.drive;

import java.util.HashMap;

import org.usfirst.frc.team670.robot.Robot;
import org.usfirst.frc.team670.robot.commands.LoggingCommand;

import com.ctre.phoenix.motorcontrol.SensorCollection;

/**
 *
 */
public class IR_Drive extends LoggingCommand {

	private final double speed;
	private SensorCollection leftEncoder, rightEncoder;

	public IR_Drive() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		speed = -0.47;
		setTimeout(2.8);
		requires(Robot.driveBase);
		requires(Robot.intake);
		leftEncoder = Robot.driveBase.getLeft().getSensorCollection();
		rightEncoder = Robot.driveBase.getLeft().getSensorCollection();
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.intake.deployGrabber(true);
		logInitialize(new HashMap<String, Object>() {{
			 put("LeftEncoderVelocity", leftEncoder.getQuadratureVelocity());
			    put("RightEncoderVelocity", rightEncoder.getQuadratureVelocity());
			    put("LeftEncoderTicks", leftEncoder.getQuadraturePosition());
			    put("RightEncoderTicks", rightEncoder.getQuadraturePosition());
		}});
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.driveBase.drive(speed, speed);
		Robot.intake.driveIntake(-0.9);
		logExecute(new HashMap<String, Object>() {{
			put("Speed", speed);
		    put("LeftEncoderVelocity", leftEncoder.getQuadratureVelocity());
		    put("RightEncoderVelocity", rightEncoder.getQuadratureVelocity());
		    put("LeftEncoderTicks", leftEncoder.getQuadraturePosition());
		    put("RightEncoderTicks", rightEncoder.getQuadraturePosition());
		}});
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return Robot.sensors.isCubeInIntake() || isTimedOut();
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.driveBase.drive(0, 0);
		logFinished(new HashMap<String, Object>() {{
		    put("LeftEncoderVelocity", leftEncoder.getQuadratureVelocity());
		    put("RightEncoderVelocity", rightEncoder.getQuadratureVelocity());
		    put("LeftEncoderTicks", leftEncoder.getQuadraturePosition());
		    put("RightEncoderTicks", rightEncoder.getQuadraturePosition());
		}});
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
