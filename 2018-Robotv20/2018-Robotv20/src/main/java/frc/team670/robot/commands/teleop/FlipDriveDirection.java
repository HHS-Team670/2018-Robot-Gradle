/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team670.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.InstantCommand;

import frc.team670.robot.commands.teleop.XboxRocketLeagueDrive;
import frc.team670.robot.utils.Logger;
import frc.team670.robot.Robot;
import frc.team670.robot.RobotContainer;

/**
 * Flips the direction of the drive: forward or reversed.
 */
public class FlipDriveDirection extends InstantCommand {

  public FlipDriveDirection() {
    super();
  }

  // Called once when the command executes
  @Override
  public void initialize() {
    boolean isReversed = XboxRocketLeagueDrive.isDriveReversed();
    XboxRocketLeagueDrive.setDriveReversed(!isReversed);
    RobotContainer.oi.rumbleDriverController(0.7, 0.2);
    Logger.consoleLog("Flipped Drive: %s", (!isReversed));
  }

}
