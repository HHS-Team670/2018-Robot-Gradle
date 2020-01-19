package frc.team670.robot.constants;

import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.team670.robot.constants.RobotMap;
import frc.team670.robot.constants.enums.OperatorState;
import frc.team670.robot.utils.MustangController;
import frc.team670.robot.utils.MustangController.XboxButtons;
import frc.team670.robot.commands.teleop.FlipDriveDirection;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

  // Controllers/Joysticks
  private MustangController driverController;

  // Buttons
  private JoystickButton toggleReverseDrive, toggleFlexMode;

  private OperatorState os = OperatorState.NONE;

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

  	public OI() {

    driverController = new MustangController(RobotMap.DRIVER_CONTROLLER_PORT);
    //operatorController = new MustangController(RobotMap.OPERATOR_CONTROLLER_PORT);
    toggleReverseDrive = new JoystickButton(driverController, XboxButtons.LEFT_BUMPER);
    toggleFlexMode = new JoystickButton(driverController, XboxButtons.A);
    toggleReverseDrive.whenPressed(new FlipDriveDirection());
	}

  /**
   * Sets the rumble on the driver controller
   * 
   * @param power The desired power of the rumble [0, 1]
   * @param time The time to rumble for in seconds
   */
  public void rumbleDriverController(double power, double time) {
    rumbleController(driverController, power, time);
  }

  /**
   * Sets the rumble on the operator controller
   * 
   * @param power The desired power of the rumble [0, 1]
   * @param time The time to rumble for in seconds
   */
  public void rumbleOperatorController(double power, double time) {
    // rumbleController(operatorController, power, time);
  }

  private void rumbleController(MustangController controller, double power, double time) {
    controller.rumble(power, time);
  }

  public MustangController getDriverController() {
    return driverController;
  }

  // public MustangController getOperatorController() {
  //   return operatorController;
  // }

  public boolean isQuickTurnPressed() {
    return driverController.getRightBumper();
  }


	
//	private Button left45 = new JoystickButton(rightDriveStick, 4);
//	private Button left90 = new JoystickButton(rightDriveStick, 2);
//	private Button left135 = new JoystickButton(rightDriveStick, 7);
//	private Button right45 = new JoystickButton(rightDriveStick, 5);
//	private Button right90 = new JoystickButton(rightDriveStick, 3);
//	private Button right135 = new JoystickButton(rightDriveStick, 10);

	public Joystick getOperatorStick() {
		return operatorStick;
	}

	public OperatorState getOS() {
		return os;
	}

	public void setOperatorCommand(OperatorState os) {
		this.os = os;
	}

}