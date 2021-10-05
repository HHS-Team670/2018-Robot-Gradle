/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team670.robot.paths2021;

import java.util.List;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import frc.team670.robot.subsystems.DriveBase;

/**
 * Robot go backward 2m. Start facing the goal
 * 
 * @author 
 */
public class GoStraightPath2021 extends Path2021 {

    public GoStraightPath2021(DriveBase driveBase) {
        super(
            List.of(
                new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0)),
                new Pose2d(0.0, -2, Rotation2d.fromDegrees(0))
            ), 
        driveBase);
    }
}