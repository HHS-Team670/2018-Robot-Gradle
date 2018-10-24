package paths.left;

import org.usfirst.frc.team670.robot.Robot;
import org.usfirst.frc.team670.robot.commands.auto_specific.DropCube;
import org.usfirst.frc.team670.robot.commands.drive.Drive;
import org.usfirst.frc.team670.robot.commands.drive.Pivot;
import org.usfirst.frc.team670.robot.commands.drive.Time_Drive;
import org.usfirst.frc.team670.robot.commands.elevator.Encoders_Elevator;
import org.usfirst.frc.team670.robot.commands.intake.Deploy;
import org.usfirst.frc.team670.robot.constants.Field;
import org.usfirst.frc.team670.robot.constants.RoboConstants;
import org.usfirst.frc.team670.robot.constants.enums.ElevatorState;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class left_scale_opposite extends CommandGroup {
	
	public left_scale_opposite() {
		addSequential(new Drive(Field.DS_TO_SWITCH + Field.SWITCH_WIDTH + Field.TOLERANCE + Field.CUBE_WIDTH));
		addSequential(new Pivot(90));
		addSequential(new Drive(Field.PLATFORM_WIDTH + (Field.SIDE_TO_SWITCH - Field.SIDE_TRIANGLE_WIDTH - Robot.width + Field.CUBE_WIDTH + Robot.length/2)));
		addParallel(new Encoders_Elevator(ElevatorState.HIGHSCALE));
		addSequential(new Pivot(-90));
		addSequential(new Drive(Field.DS_TO_SCALE - (Field.DS_TO_SWITCH + Field.SWITCH_WIDTH + Field.TOLERANCE + Field.CUBE_WIDTH) + Field.TOLERANCE/2 + RoboConstants.FRONT_TO_ELEVATOR));
		addSequential(new DropCube());
		addParallel(new Encoders_Elevator(ElevatorState.SWITCH));
		addSequential(new Drive(-(Field.DS_TO_SCALE - (Field.DS_TO_SWITCH + Field.SWITCH_WIDTH + Field.CUBE_WIDTH) + Field.TOLERANCE/2 + RoboConstants.FRONT_TO_ELEVATOR)));
		addSequential(new Pivot(180));
	}
}
