package org.usfirst.frc.team670.robot.commands.auto_specific;

import org.usfirst.frc.team670.robot.commands.intake.OpenIntake;
import org.usfirst.frc.team670.robot.commands.intake.SpinIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DropCube extends CommandGroup {

    public DropCube() {
    		setTimeout(0.25);

//    		addParallel(new OpenIntake(false));
    		addParallel(new SpinIntake(0.30, 10));
    		addSequential(new Delay(0.2));
    		addSequential(new OpenIntake(true));
    }
    
    public boolean isFinished()
    {
    	return isTimedOut();
    }
}