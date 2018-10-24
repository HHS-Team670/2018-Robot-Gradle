package paths.right;

import org.usfirst.frc.team670.robot.Robot;
import org.usfirst.frc.team670.robot.commands.auto_specific.ShootCube;
import org.usfirst.frc.team670.robot.commands.auto_specific.VisionCubePickup;
import org.usfirst.frc.team670.robot.commands.drive.Drive;
import org.usfirst.frc.team670.robot.commands.drive.Pivot;
import org.usfirst.frc.team670.robot.commands.elevator.DelayedRaise;
import org.usfirst.frc.team670.robot.commands.elevator.Encoders_Elevator;
import org.usfirst.frc.team670.robot.constants.Field;
import org.usfirst.frc.team670.robot.constants.RoboConstants;
import org.usfirst.frc.team670.robot.constants.enums.ElevatorState;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class right_scale_side extends CommandGroup {

	public right_scale_side() {
	addParallel(new DelayedRaise(ElevatorState.CLEARANCE));
    	addSequential((new Drive(Field.DS_TO_SCALE - 28.26 - Robot.length)));
    	addParallel(new Encoders_Elevator(ElevatorState.HIGHSCALE));
    	addSequential(new Pivot(-45));
    	addSequential(new Drive(28.26/Math.sin(Math.toRadians(45)) + RoboConstants.FRONT_TO_ELEVATOR + Field.TOLERANCE/2));
    	addSequential(new ShootCube(0.7));
    	addSequential(new Drive(-(RoboConstants.FRONT_TO_ELEVATOR + 28.26/(Math.sin(Math.toRadians(45))))));
    	addParallel(new Encoders_Elevator(ElevatorState.EXCHANGE));
    	addSequential(new Pivot(-90));
    	addSequential(new VisionCubePickup());
	}
}
