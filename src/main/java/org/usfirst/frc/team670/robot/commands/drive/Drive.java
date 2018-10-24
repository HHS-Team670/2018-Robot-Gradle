package org.usfirst.frc.team670.robot.commands.drive;

import edu.wpi.first.wpilibj.command.CommandGroup;


/**
 * Takes in a distance in inches and picks which pivot command to use
 * 
 * @author shaylan
 */
public class Drive extends CommandGroup{
	/**
	 * @param distance Distance in inches
	 */
	public Drive(double distance) {
		addSequential(new Encoders_Drive(distance));
	}
}