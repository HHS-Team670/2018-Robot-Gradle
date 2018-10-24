package paths.left;

import org.usfirst.frc.team670.robot.Robot;
import org.usfirst.frc.team670.robot.commands.auto_specific.AutoCubePickupSwitch;
import org.usfirst.frc.team670.robot.commands.auto_specific.DropCube;
import org.usfirst.frc.team670.robot.commands.drive.Drive;
import org.usfirst.frc.team670.robot.commands.drive.Pivot;
import org.usfirst.frc.team670.robot.commands.elevator.Encoders_Elevator;
import org.usfirst.frc.team670.robot.commands.intake.Deploy;
import org.usfirst.frc.team670.robot.constants.Field;
import org.usfirst.frc.team670.robot.constants.RoboConstants;
import org.usfirst.frc.team670.robot.constants.enums.ElevatorState;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class left_switch_side extends CommandGroup {

	public left_switch_side() {
		addSequential(new Drive(Field.DS_TO_SWITCH - Robot.length/2 + Field.SWITCH_WIDTH/2));
		addParallel(new Encoders_Elevator(ElevatorState.SWITCH));
		addSequential(new Pivot(90));
		addSequential(new Drive(Field.SIDE_TO_SWITCH - Robot.width - Field.SIDE_TRIANGLE_WIDTH));
    	addSequential(new DropCube());
    	addSequential(new AutoCubePickupSwitch(false));
	}
}
