/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team670.robot;

import org.usfirst.frc.team670.robot.commands.CancelCommand;
import org.usfirst.frc.team670.robot.commands.auto_specific.DropCube;
import org.usfirst.frc.team670.robot.commands.auto_specific.PickupCube;
import org.usfirst.frc.team670.robot.commands.auto_specific.VisionCubePickup;
import org.usfirst.frc.team670.robot.commands.drive.Drive;
import org.usfirst.frc.team670.robot.commands.drive.NavX_Pivot;
import org.usfirst.frc.team670.robot.commands.drive.Pivot;
import org.usfirst.frc.team670.robot.commands.elevator.Encoders_Elevator;
import org.usfirst.frc.team670.robot.commands.elevator.Hold_Elevator;
import org.usfirst.frc.team670.robot.commands.elevator.DisableSoftLimits;
import org.usfirst.frc.team670.robot.commands.elevator.ZeroElevatorEncoders;
import org.usfirst.frc.team670.robot.commands.intake.Deploy;
import org.usfirst.frc.team670.robot.commands.intake.OpenIntake;
import org.usfirst.frc.team670.robot.commands.intake.SpinIntake;
import org.usfirst.frc.team670.robot.commands.state_change.Set_DriverControl;
import org.usfirst.frc.team670.robot.commands.state_change.Set_OperatorControl;
import org.usfirst.frc.team670.robot.commands.state_change.enableHardGrab;
import org.usfirst.frc.team670.robot.constants.Field;
import org.usfirst.frc.team670.robot.constants.RobotMap;
import org.usfirst.frc.team670.robot.constants.enums.DriverState;
import org.usfirst.frc.team670.robot.constants.enums.ElevatorState;
import org.usfirst.frc.team670.robot.constants.enums.OperatorState;
import org.usfirst.frc.team670.robot.commands.drive.Encoders_Calibration;
import org.usfirst.frc.team670.robot.commands.drive.Encoders_Pivot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import paths.right.right_baseline;
import paths.right.right_scale_side;
import paths.right.right_switch_side;
import paths.right.right_switch_straight;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 * 
 * @author vsharma
 */
public class OI {

	private OperatorState os = OperatorState.NONE;
	private DriverState ds = DriverState.TANK;
		
	private Joystick leftDriveStick = new Joystick(RobotMap.leftDriveStick);
	private Joystick rightDriveStick = new Joystick(RobotMap.rightDriveStick);
	private Joystick operatorStick = new Joystick(RobotMap.operatorStick);
	private Joystick arcadeStick = new Joystick(RobotMap.arcadeStick);

	// Operator Controls
	private Button toggleElevator = new JoystickButton(operatorStick, 3);
	private Button toggleIntake = new JoystickButton(operatorStick, 1);
	private Button toggleClimber = new JoystickButton(operatorStick, 5);
	
	private Button retract = new JoystickButton(arcadeStick, 1);
	private Button deploy = new JoystickButton(arcadeStick, 10);
	
	private Button grab = new JoystickButton(arcadeStick, 2);
	private Button release = new JoystickButton(arcadeStick, 9);
	
	private Button elevatorExchange = new JoystickButton(arcadeStick, 3);
	private Button elevatorSwitch = new JoystickButton(arcadeStick, 8);
	
	private Button CancelCommand = new JoystickButton(arcadeStick, 5);
	private Button elevatorScale = new JoystickButton(arcadeStick, 4);
	
	private Button toggleElevatorLimits = new JoystickButton(arcadeStick, 6);
	
	private Button vision_Pivot = new JoystickButton(arcadeStick, 7);
	
//	private Button left45 = new JoystickButton(rightDriveStick, 4);
//	private Button left90 = new JoystickButton(rightDriveStick, 2);
//	private Button left135 = new JoystickButton(rightDriveStick, 7);
//	private Button right45 = new JoystickButton(rightDriveStick, 5);
//	private Button right90 = new JoystickButton(rightDriveStick, 3);
//	private Button right135 = new JoystickButton(rightDriveStick, 10);

	
	public OI() {
		// Operator buttons
		toggleClimber.whenPressed(new Set_OperatorControl(OperatorState.CLIMBER));
		toggleClimber.whenReleased(new Set_OperatorControl(OperatorState.NONE));
		toggleElevator.whenPressed(new Set_OperatorControl(OperatorState.ELEVATOR));
		toggleElevator.whenReleased(new Set_OperatorControl(OperatorState.NONE));
		toggleIntake.whenPressed(new Set_OperatorControl(OperatorState.INTAKE));
		toggleIntake.whenReleased(new Set_OperatorControl(OperatorState.NONE));
		
		grab.whenPressed(new OpenIntake(false));
		release.whenPressed(new OpenIntake(true));
		
		deploy.whenPressed(new Deploy(false));
		retract.whenPressed(new Deploy(true));
		
		elevatorSwitch.whenPressed(new Encoders_Elevator(ElevatorState.SWITCH));
		elevatorScale.whenPressed(new Encoders_Elevator(ElevatorState.HIGHSCALE));
		elevatorExchange.whenPressed(new Encoders_Elevator(ElevatorState.EXCHANGE));
		
		CancelCommand.whenPressed(new CancelCommand());
				
		toggleElevatorLimits.whenPressed(new DisableSoftLimits());
		
		vision_Pivot.whenPressed(new VisionCubePickup());
		
		//COMMENT THESE OUT FOR MATCH, MEANT FOR TESTING PIVOTING
//		left45.whenPressed(new Pivot(-45));
//		left90.whenPressed(new NavX_Pivot(-90));
//		left135.whenPressed(new NavX_Pivot(-135));
//		right45.whenPressed(new NavX_Pivot(45));
//		right90.whenPressed(new NavX_Pivot(90));
//		right135.whenPressed(new NavX_Pivot(135));
	}

	public Joystick getLeftStick() {
		return leftDriveStick;
	}

	public Joystick getRightStick() {
		return rightDriveStick;
	}

	public Joystick getOperatorStick() {
		return operatorStick;
	}

	public OperatorState getOS() {
		return os;
	}

	public void setOperatorCommand(OperatorState os) {
		this.os = os;
	}

	public DriverState getDS() {
		return ds;
	}

	public void setDriverState(DriverState ds) {
		this.ds = ds;
	}
}
