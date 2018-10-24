package org.usfirst.frc.team670.robot.commands.auto_specific;

import org.usfirst.frc.team670.robot.Robot;
import org.usfirst.frc.team670.robot.commands.drive.Drive;
import org.usfirst.frc.team670.robot.commands.drive.IR_Drive;
import org.usfirst.frc.team670.robot.commands.drive.Pivot;
import org.usfirst.frc.team670.robot.commands.drive.Time_Drive;
import org.usfirst.frc.team670.robot.commands.elevator.Encoders_Elevator;
import org.usfirst.frc.team670.robot.constants.Field;
import org.usfirst.frc.team670.robot.constants.enums.ElevatorState;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoCubePickupSwitch extends CommandGroup {

    public AutoCubePickupSwitch(boolean isRight) {
    	
    	addParallel(new Encoders_Elevator(ElevatorState.EXCHANGE));
    	addSequential(new Drive(-(Field.SIDE_TO_SWITCH - Robot.width - Field.SIDE_TRIANGLE_WIDTH)));
    	
    	if(isRight)
    		addSequential(new Pivot(90));
    	else
    		addSequential(new Pivot(-90));

    	addSequential(new Drive(Field.SWITCH_WIDTH));
    	
    	if(isRight)
    		addSequential(new Pivot(-135));
    	else
    		addSequential(new Pivot(135));
    	
    	addSequential(new VisionCubePickup());
    	addSequential(new Encoders_Elevator(ElevatorState.SWITCH));
    	addSequential(new DropCube());
    }
}