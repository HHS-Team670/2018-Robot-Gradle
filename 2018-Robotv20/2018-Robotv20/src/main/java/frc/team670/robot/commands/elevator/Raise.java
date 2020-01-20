package frc.team670.robot.commands.elevator;

import java.util.HashMap;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.team670.robot.Robot;

import frc.team670.robot.RobotContainer;


/**
 *
 */
public class Raise extends WaitCommand {

	private double speed;
	
	/*
	 * @param isDeploy true if it is the deploy, false if it is to pick up
	 */
    public Raise(double speed) {
        super(3);
        this.speed = speed;
    }

    // Called repeatedly when this Command is scheduled to run
    public void execute() {
    	RobotContainer.elevator.moveElevator(speed);
    }

    // Called once after isFinished returns true
    public void end() {
        RobotContainer.elevator.moveElevator(0);
    }

}
