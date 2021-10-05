package frc.team670.robot.commands.auton2021;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.team670.paths.right.*;
import frc.team670.paths.Path;
import frc.team670.paths.left.Left2BS;
import frc.team670.paths.center.Center2BS;
import frc.team670.paths.right.Right2BS;
import frc.team670.mustanglib.commands.MustangCommand;
import frc.team670.robot.commands.indexer.EmptyRevolver;
import frc.team670.robot.commands.indexer.SendAllBalls;
import frc.team670.robot.commands.routines.IntakeBallToIndexer;
import frc.team670.robot.commands.shooter.Shoot;
import frc.team670.robot.commands.shooter.StartShooter;
import frc.team670.robot.commands.shooter.StartShooterByDistance;
import frc.team670.robot.commands.turret.RotateToAngle;
import frc.team670.robot.commands.turret.RotateTurret;
import frc.team670.robot.commands.vision.GetVisionData;
import frc.team670.robot.constants.RobotConstants;
import frc.team670.robot.subsystems.Conveyor;
import frc.team670.robot.subsystems.DriveBase;
import frc.team670.robot.subsystems.Indexer;
import frc.team670.robot.subsystems.Intake;
import frc.team670.mustanglib.subsystems.MustangSubsystemBase;
import frc.team670.mustanglib.subsystems.MustangSubsystemBase.HealthState;
import frc.team670.robot.subsystems.Shooter;
import frc.team670.robot.subsystems.Turret;
import frc.team670.robot.subsystems.Vision;
import frc.team670.robot.commands.auton.AutoSelector.StartPosition;

/**
 * Autonomous routine starting with shooting from the initiation line (facing
 * towards your driver station), ending at (and hopefully intaking and indexing)
 * 2 Power Cells under the generator near your trench.
 * 
 * @author justin, rishabh
 */
public class GoStraightCommand2021 extends SequentialCommandGroup implements MustangCommand {

        private Path trajectory;
        private Map<MustangSubsystemBase, HealthState> healthReqs;
        private DriveBase driveBase;
        //private Vision coprocessor;

        /**
         * Initializes this command from the given parameters
         * 
         * @param startPosition the position of the robot at the beginning of the game
         * @param driveBase the drive base
         */
        public GoBackwardsCommand(DriveBase driveBase) {
                this.driveBase = driveBase;
                double turretAng = 0;
                
                trajectory = new GoStraightPath2021(driveBase);
                turretAng = RobotConstants.leftTurretAng;
                
                healthReqs = new HashMap<MustangSubsystemBase, HealthState>();
                healthReqs.put(driveBase, HealthState.GREEN);
                //healthReqs.put(shooter, HealthState.GREEN);
                /*healthReqs.put(intake, HealthState.GREEN);
                healthReqs.put(conveyor, HealthState.GREEN);
                healthReqs.put(indexer, HealthState.GREEN);
                healthReqs.put(turret, HealthState.GREEN);*/

                // Sets current position to the starting point of the path which is where we should be at init
                driveBase.resetOdometry(trajectory.getStartingPose());

                addCommands(
                        getTrajectoryFollowerCommand(trajectory, driveBase)
                );
        }

        @Override 
        public void end(boolean interrupted){
        //     if (!interrupted){
        //         addCommands(
        //             new GetVisionData(coprocessor),
        //             // should check here that vision didn't return an error code before updating
        //             new UpdatePoseFromVision(driveBase, coprocessor)
        //         );
        //     }
        }

        @Override
        public Map<MustangSubsystemBase, HealthState> getHealthRequirements() {
                // maybe also check that NavX is there
                return healthReqs;
        }
}