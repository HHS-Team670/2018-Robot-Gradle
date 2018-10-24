package paths.center;

import org.usfirst.frc.team670.robot.Robot;
import org.usfirst.frc.team670.robot.commands.auto_specific.DropCube;
import org.usfirst.frc.team670.robot.commands.drive.Drive;
import org.usfirst.frc.team670.robot.commands.drive.Pivot;
import org.usfirst.frc.team670.robot.commands.elevator.Encoders_Elevator;
import org.usfirst.frc.team670.robot.commands.intake.Deploy;
import org.usfirst.frc.team670.robot.commands.intake.SpinIntake;
import org.usfirst.frc.team670.robot.constants.Field;
import org.usfirst.frc.team670.robot.constants.RoboConstants;
import org.usfirst.frc.team670.robot.constants.enums.ElevatorState;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class center_left_switch_side extends CommandGroup {

    public center_left_switch_side() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	addParallel(new Deploy(true));
    	addSequential(new Drive(0.5*Robot.length));
    	addSequential(new Pivot(-90));
    	addSequential(new Drive(Field.SWITCH_LENGTH/2+Robot.length*2));
    	addSequential(new Pivot(90));
    	addSequential(new Drive(Field.DS_TO_SWITCH + Field.SWITCH_WIDTH/2 - 0.5*Robot.length));
    	addSequential(new Pivot(90));//Check if TOLERANCE is actually accounted for
		addSequential(new Encoders_Elevator(ElevatorState.SWITCH));
    	addSequential(new Drive(1.75*Robot.length + Field.TOLERANCE));
    	addSequential(new DropCube());
    	addSequential(new Drive(-Robot.length));
    	addSequential(new Encoders_Elevator(ElevatorState.EXCHANGE));
    }
}
