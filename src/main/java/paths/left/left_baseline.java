package paths.left;

import org.usfirst.frc.team670.robot.commands.drive.Drive;
import org.usfirst.frc.team670.robot.commands.elevator.DelayedRaise;
import org.usfirst.frc.team670.robot.commands.elevator.Encoders_Elevator;
import org.usfirst.frc.team670.robot.constants.Field;
import org.usfirst.frc.team670.robot.constants.enums.ElevatorState;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class left_baseline extends CommandGroup {

    public left_baseline() {
    	addParallel(new DelayedRaise(ElevatorState.SWITCH));
    	addSequential(new Drive(Field.DS_TO_BASELINE + Field.TOLERANCE));    	
    }
}
