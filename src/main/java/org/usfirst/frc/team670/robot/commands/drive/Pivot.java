package org.usfirst.frc.team670.robot.commands.drive;

import org.usfirst.frc.team670.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;


/**
 * Takes in a degree value and picks which pivot command to use
 * 
 * @author shaylan
 */
public class Pivot extends CommandGroup{
	
	/**
	 * @param degrees Angle in degrees
	 */
	public Pivot(double degrees) {
		if(Robot.isNavXConnected())
			addSequential(new NavX_Pivot(degrees));
		else
			addSequential(new Encoders_Pivot(degrees));
	}
}