package paths.right;

import org.usfirst.frc.team670.robot.commands.drive.Drive;
import org.usfirst.frc.team670.robot.commands.elevator.DelayedRaise;
import org.usfirst.frc.team670.robot.constants.Field;
import org.usfirst.frc.team670.robot.constants.enums.ElevatorState;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * 
 * Moves forward to baseline, and pauses for 4 seconds - then drives back to
 * starting
 * 
 * 
 */
public class right_baseline extends CommandGroup {

	public right_baseline() {
    	addParallel(new DelayedRaise(ElevatorState.SWITCH));
		addSequential(new Drive(Field.DS_TO_BASELINE + Field.TOLERANCE));
	}
}
