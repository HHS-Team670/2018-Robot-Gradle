package paths.center;

import org.usfirst.frc.team670.robot.commands.auto_specific.DropCube;
import org.usfirst.frc.team670.robot.commands.auto_specific.PickupCube;
import org.usfirst.frc.team670.robot.commands.drive.Encoders_Drive;
import org.usfirst.frc.team670.robot.commands.drive.IR_Drive;
import org.usfirst.frc.team670.robot.commands.drive.NavX_Pivot;
import org.usfirst.frc.team670.robot.commands.elevator.Encoders_Elevator;
import org.usfirst.frc.team670.robot.commands.intake.SpinIntake;
import org.usfirst.frc.team670.robot.constants.Field;
import org.usfirst.frc.team670.robot.constants.enums.ElevatorState;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class center_right_switch_straight extends CommandGroup {

    public center_right_switch_straight() {
		addSequential(new center_baseline());
		addSequential(new DropCube());
		
		//Alternate to plowing cube pile
		addSequential(new Encoders_Drive(-(Field.CUBEPILE_LENGTH + 12)));
		addParallel(new Encoders_Elevator(ElevatorState.EXCHANGE));
		addSequential(new NavX_Pivot(-35));
		addSequential(new IR_Drive());
		addSequential(new PickupCube());
		addSequential(new Encoders_Drive(-30));
		addParallel(new Encoders_Elevator(ElevatorState.SWITCH));
		addSequential(new NavX_Pivot(45));
		addSequential(new Encoders_Drive(Field.CUBEPILE_LENGTH + 18));
		addSequential(new SpinIntake(0.8, 2));
		
		//Pick up cube
//		addSequential(new Time_Drive(0.15, -0.3));
//		addParallel(new Encoders_Elevator(ElevatorState.EXCHANGE));
//		addSequential(new NavX_Pivot(-90));
//		addSequential(new IR_Drive());
//		addSequential(new PickupCube());
		//Place Cube
//		addParallel(new Encoders_Elevator(ElevatorState.SWITCH));
//		addSequential(new Encoders_Drive(-Field.SWITCH_LENGTH/2));
//		addSequential(new NavX_Pivot(90));
//		addSequential(new Time_Drive(0.15, 0.7));
//		addSequential(new ShootCube(0.5));
    }
}
