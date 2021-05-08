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
import frc.team670.robot.commands.elevator.Raise;
import frc.team670.robot.commands.intake.Deploy;
import frc.team670.robot.commands.intake.Grab;
import frc.team670.robot.commands.teleop.FlipDriveDirection;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

  // Controllers/Joysticks
  private MustangController driverController;

  // Buttons
  private JoystickButton toggleReverseDrive;

  private OperatorState os = OperatorState.NONE;

  private Joystick arcadeStick = new Joystick(RobotMap.arcadeStick);

  // Operator Controls
  
  private JoystickButton retract = new JoystickButton(arcadeStick, 1);
  private JoystickButton deploy = new JoystickButton(arcadeStick, 10);
  
  private Button grab = new JoystickButton(arcadeStick, 2);
  private Button yeet = new JoystickButton(arcadeStick, 9);

  	public OI() {

    driverController = new MustangController(RobotMap.DRIVER_CONTROLLER_PORT);
    //operatorController = new MustangController(RobotMap.OPERATOR_CONTROLLER_PORT);
    toggleReverseDrive = new JoystickButton(driverController, XboxButtons.LEFT_BUMPER);
    // toggleFlexMode = new JoystickButton(driverController, XboxButtons.A);
    toggleReverseDrive.whenPressed(new FlipDriveDirection());

    retract = new JoystickButton(driverController, XboxButtons.A);
    retract.whenPressed(new Deploy(false));

    deploy = new JoystickButton(driverController, XboxButtons.B);
    deploy.whenPressed(new Deploy(true));

    grab = new JoystickButton(driverController, XboxButtons.X);
    grab.whenPressed(new Grab(false));

    yeet = new JoystickButton(driverController, XboxButtons.Y);
    yeet.whenPressed(new Grab(true));
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
		return arcadeStick;
	}

	public OperatorState getOS() {
		return os;
	}

	public void setOperatorCommand(OperatorState os) {
		this.os = os;
	}

}