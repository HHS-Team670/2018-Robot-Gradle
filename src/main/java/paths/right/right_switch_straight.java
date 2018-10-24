package paths.right;

import org.usfirst.frc.team670.robot.Robot;
import org.usfirst.frc.team670.robot.commands.auto_specific.DropCube;
import org.usfirst.frc.team670.robot.commands.drive.Drive;
import org.usfirst.frc.team670.robot.commands.drive.Time_Drive;
import org.usfirst.frc.team670.robot.commands.elevator.DelayedRaise;
import org.usfirst.frc.team670.robot.constants.Field;
import org.usfirst.frc.team670.robot.constants.enums.ElevatorState;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *@pre Line up Robot with the switch
 */
public class right_switch_straight extends CommandGroup {

	/**
	 * 
	 * @pre Line up Robot with the switch
	 */
	public right_switch_straight() {
		addParallel(new DelayedRaise(ElevatorState.SWITCH));
		addSequential(new Drive(Field.DS_TO_SWITCH - Robot.length - 12));
    	addSequential(new Time_Drive(1.5, 0.3));
		addSequential(new DropCube());
    	addSequential(new Drive(-Robot.length/2));
	}
}
