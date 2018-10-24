/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.usfirst.frc.team670.robot;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import paths.center.center_baseline;
import paths.center.center_left_switch_side;
import paths.center.center_left_switch_straight;
import paths.center.center_right_switch_side;
import paths.center.center_right_switch_straight;
import paths.left.left_baseline;
import paths.left.left_scale_opposite;
import paths.left.left_scale_side;
import paths.left.left_switch_side;
import paths.right.right_baseline;
import paths.right.right_scale_opposite;
import paths.right.right_scale_side;
import paths.right.right_switch_side;
import paths.right.right_switch_straight;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team670.robot.commands.LoggingCommand;
import org.usfirst.frc.team670.robot.commands.auto_specific.Delay;
import org.usfirst.frc.team670.robot.commands.elevator.ZeroElevatorEncoders;
import org.usfirst.frc.team670.robot.commands.intake.Deploy;
import org.usfirst.frc.team670.robot.constants.RobotMap;
import org.usfirst.frc.team670.robot.subsystems.Aggregator;
import org.usfirst.frc.team670.robot.subsystems.Climber;
import org.usfirst.frc.team670.robot.subsystems.DriveBase;
import org.usfirst.frc.team670.robot.subsystems.Elevator;
import org.usfirst.frc.team670.robot.subsystems.Intake;

import com.kauailabs.navx.frc.AHRS;

/**
 * @author vsharma
 */

public class Robot extends TimedRobot {
	public static final double width = 33.25, length = 38; // LENGTH AND WIDTH WITH BUMPERS
	public static final Elevator elevator = new Elevator();
	public static final DriveBase driveBase = new DriveBase();
	public static final Intake intake = new Intake();
	public static final Climber climber = new Climber();
	
	public static Thread m_visionThread;
	public static Aggregator sensors;
	public static OI oi;
	private static AHRS navXMicro;
	private static DigitalInput dio = new DigitalInput(1);
	private static DigitalInput dio1 = new DigitalInput(2);
	
	public static File log;	
	private PrintWriter writer;
	public static Queue<String> queuedMessages = new ConcurrentLinkedQueue<String>();

	CommandGroup combined;
	private SendableChooser<Double> autonomousDelay;
	private SendableChooser<String> subMenuRR, subMenuLL, subMenuLR, subMenuRL;
	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		oi = new OI();
		sensors = new Aggregator();
		queuedMessages = new ConcurrentLinkedQueue();

		try {
			navXMicro = new AHRS(RobotMap.navXPort);
		} catch (RuntimeException ex) {
			DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);
			navXMicro = null;
		}
		
		CameraServer.getInstance().startAutomaticCapture();			

		subMenuRR = new SendableChooser<String>();
		subMenuLL = new SendableChooser<String>();
		subMenuRL = new SendableChooser<String>();
		subMenuLR = new SendableChooser<String>();
		autonomousDelay = new SendableChooser<Double>();

		autonomousDelay.addDefault("0 Second", 0.0);
		autonomousDelay.addObject("1 Second", 1.0);
		autonomousDelay.addObject("2 Second", 2.0);
		autonomousDelay.addObject("3 Second", 3.0);
		autonomousDelay.addObject("4 Second", 4.0);
		autonomousDelay.addObject("5 Second", 5.0);

		subMenuLL.addDefault("LL (KEY ONLY)", "left_baseline");
		subMenuLL.addObject("----LEFT----", "left_baseline");
		subMenuLL.addObject("left_baseline", "left_baseline");
		subMenuLL.addObject("left_switch_side", "left_switch_side");
		subMenuLL.addObject("left_scale_side", "left_scale_side");
		subMenuLL.addObject("left_scale_opposite", "left_scale_opposite");
		subMenuLL.addObject("----CENTER----", "center_baseline");
		subMenuLL.addObject("center_baseline", "center_baseline");
		subMenuLL.addObject("center_left_switch_straight", "center_left_switch_straight");
		subMenuLL.addObject("center_right_switch_straight", "center_right_switch_straight");
		subMenuLL.addObject("----RIGHT----", "right_baseline");
		subMenuLL.addObject("right_baseline", "right_baseline");
		subMenuLL.addObject("right_switch_side", "right_switch_side");
		subMenuLL.addObject("right_scale_side", "right_scale_side");
		subMenuLL.addObject("right_scale_opposite", "right_scale_opposite");

		subMenuRR.addDefault("RR (KEY ONLY)", "left_baseline");
		subMenuRR.addObject("----LEFT----", "left_baseline");
		subMenuRR.addObject("left_baseline", "left_baseline");
		subMenuRR.addObject("left_switch_side", "left_switch_side");
		subMenuRR.addObject("left_scale_side", "left_scale_side");
		subMenuRR.addObject("left_scale_opposite", "left_scale_opposite");
		subMenuRR.addObject("----CENTER----", "center_baseline");
		subMenuRR.addObject("center_baseline", "center_baseline");
		subMenuRR.addObject("center_left_switch_straight", "center_left_switch_straight");
		subMenuRR.addObject("center_right_switch_straight", "center_right_switch_straight");
		subMenuRR.addObject("----RIGHT----", "right_baseline");
		subMenuRR.addObject("right_baseline", "right_baseline");
		subMenuRR.addObject("right_switch_side", "right_switch_side");
		subMenuRR.addObject("right_scale_side", "right_scale_side");
		subMenuRR.addObject("right_scale_opposite", "right_scale_opposite");

		subMenuLR.addDefault("LR (KEY ONLY)", "left_baseline");
		subMenuLR.addObject("----LEFT----", "left_baseline");
		subMenuLR.addObject("left_baseline", "left_baseline");
		subMenuLR.addObject("left_switch_side", "left_switch_side");
		subMenuLR.addObject("left_scale_side", "left_scale_side");
		subMenuLR.addObject("left_scale_opposite", "left_scale_opposite");
		subMenuLR.addObject("----CENTER----", "center_baseline");
		subMenuLR.addObject("center_baseline", "center_baseline");
		subMenuLR.addObject("center_left_switch_straight", "center_left_switch_straight");
		subMenuLR.addObject("center_right_switch_straight", "center_right_switch_straight");
		subMenuLR.addObject("----RIGHT----", "right_baseline");
		subMenuLR.addObject("right_baseline", "right_baseline");
		subMenuLR.addObject("right_switch_side", "right_switch_side");
		subMenuLR.addObject("right_scale_side", "right_scale_side");
		subMenuLR.addObject("right_scale_opposite", "right_scale_opposite");

		
		subMenuRL.addDefault("RL (KEY ONLY)", "left_baseline");
		subMenuRL.addObject("----LEFT----", "left_baseline");
		subMenuRL.addObject("left_baseline", "left_baseline");
		subMenuRL.addObject("left_switch_side", "left_switch_side");
		subMenuRL.addObject("left_scale_side", "left_scale_side");
		subMenuRL.addObject("left_scale_opposite", "left_scale_opposite");
		subMenuRL.addObject("----CENTER----", "center_baseline");
		subMenuRL.addObject("center_baseline", "center_baseline");
		subMenuRL.addObject("center_left_switch_straight", "center_left_switch_straight");
		subMenuRL.addObject("center_right_switch_straight", "center_right_switch_straight");
		subMenuRL.addObject("----RIGHT----", "right_baseline");
		subMenuRL.addObject("right_baseline", "right_baseline");
		subMenuRL.addObject("right_switch_side", "right_switch_side");
		subMenuRL.addObject("right_scale_side", "right_scale_side");
		subMenuRL.addObject("right_scale_opposite", "right_scale_opposite");
		
		SmartDashboard.putData("Auton Delay", autonomousDelay);
		SmartDashboard.putData("LL", subMenuLL);
		SmartDashboard.putData("RR", subMenuRR);
		SmartDashboard.putData("LR", subMenuLR);
		SmartDashboard.putData("RL", subMenuRL);
	}
	
	public Command parseCommand(String str) {		
		switch(str.toLowerCase()){
		case "left_baseline":
			return new left_baseline();	
		case "left_scale_opposite":
			return new left_scale_opposite();
		case "left_scale_side":
			return new left_scale_side();
		case "left_switch_side":
			return new left_switch_side();
		case "center_baseline":
			return new center_baseline();
		case "center_left_switch_side":
			return new center_left_switch_side();
		case "center_left_switch_straight":
			return new center_left_switch_straight();
		case "center_right_switch_straight":
			return new center_right_switch_straight();
		case "center_right_switch_side":
			return new center_right_switch_side();
		case "right_baseline":
			return new right_baseline();
		case "right_scale_opposite":
			return new right_scale_opposite();
		case "right_scale_side":
			return new right_scale_side();
		case "right_switch_side":
			return new right_switch_side();
		case "right_switch_straight":
			return new right_switch_straight();
		default: 
			return new left_baseline();		
		}
	}


	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
	 */
	@Override
	public void disabledInit() {

		String msg;
		try {
			msg = queuedMessages.poll();
			if (msg != null) {
				writer.write(msg);
			}
			writer.flush();
		}
		catch(RuntimeException e){
//			e.printStackTrace();
		}
	}

	@Override
	public void disabledPeriodic() {
	}
	
	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable chooser
	 * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString code to get the
	 * auto name from the text box below the Gyro
	 *
	 * <p>
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons to
	 * the switch structure below with additional strings & commands.
	 */

	public Boolean isLeft(String str)
	{
		switch(str.toLowerCase()){
		case "left_baseline":
			return null;	
		case "left_scale_opposite":
			return false;
		case "left_scale_side":
			return true;
		case "left_switch_side":
			return true;
		case "center_baseline":
			return null;
		case "center_left_switch_side":
			return null;
		case "center_left_switch_straight":
			return null;
		case "center_right_switch_straight":
			return null;
		case "center_right_swtich_side":
			return null;
		case "right_baseline":
			return null;
		case "right_scale_opposite":
			return true;
		case "right_scale_side":
			return false;
		case "right_switch_side":
			return false;
		case "right_switch_straight":
			return false;
		default: 
			return null;		
		}
	}


	@Override
	public void autonomousInit() {
		String data = DriverStation.getInstance().getGameSpecificMessage(); 

		//If the string data is invalid, then keep checking for valid data-----------------------------------
		if(data == null)
			data = "";

		int retries = 100;

		while(data.length() < 3 && retries > 0)
		{
			DriverStation.reportError("Game data is not valid", false);
			try{
				Thread.sleep(5);
				data = DriverStation.getInstance().getGameSpecificMessage();
				if(data == null)
					data = "";
			}
			catch(Exception e){}
			retries--;
		}

		if(data.equals("") || data == null)
			data = "";
		else
			data = data.substring(0, 2); 

		//Find the primary command for autonomous-----------------------------------

		String cmd = "";

		if(data.equalsIgnoreCase("RR")) 
			cmd = subMenuRR.getSelected();
		else if(data.equalsIgnoreCase("LL"))
			cmd = subMenuLL.getSelected();
		else if(data.equalsIgnoreCase("LR"))
			cmd = subMenuLR.getSelected();
		else if(data.equalsIgnoreCase("RL"))
			cmd = subMenuRL.getSelected();

		Command primaryCommand = parseCommand(cmd);

		//Build the command sequence------------------------------

		combined = new CommandGroup(); 	

		//Deploy the intake
		combined.addParallel(new Deploy(true));

		//Add whatever time delay the driver selected
		if(autonomousDelay.getSelected() > 0.01)
			combined.addSequential(new Delay(autonomousDelay.getSelected())); 

		combined.addParallel(new org.usfirst.frc.team670.robot.commands.intake.SpinIntake(-0.15, 10));
		
		//Add the primary command sequence taken from the smartdashboard
		if(primaryCommand != null)
			combined.addSequential(primaryCommand);
		
		queuedMessages.add("{Game Data: " + data + "}\n");
		queuedMessages.add("{Command STR: " + cmd + "}\n");
		queuedMessages.add("{Command Ran: " + primaryCommand.getName() + "}\n");

		try {
			String fileName = "Log_" + DriverStation.getInstance().getEventName() +"_" + DriverStation.getInstance().getMatchNumber() + "_" + (int)(1000*Math.random()) + ".txt";
			log = new File("/home/lvuser/" + fileName);
		}
		catch(RuntimeException e) {
			log = null;
			e.printStackTrace();
		}
		
		try {
			writer = new PrintWriter(new BufferedWriter(new FileWriter(log)), false);
		} catch (IOException e) {
			writer = null;
			e.printStackTrace();
		}
		catch(RuntimeException e) {
			writer = null;
			e.printStackTrace();
		}
		
		if(writer != null && log != null) {
			new Thread(new Runnable(){

				@Override
				public void run() {
					Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
					while(!Thread.interrupted()) {
						String msg;
						try {
							do {
								msg = queuedMessages.poll();
								if (msg != null) {
									writer.write(msg);
								}
							} while (msg != null);
							writer.flush();
							Thread.sleep(1000);
						}
						catch(RuntimeException e){
							e.printStackTrace();
						}
						catch(InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		}
		
		LoggingCommand.setLoggingInterval(8);
		
		//Start running the command sequence----------------------------
		if (combined != null)
			combined.start();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		if (combined != null) {
			combined.cancel();
		}
		LoggingCommand.setLoggingInterval(100);


	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
//		SmartDashboard.putBoolean("IR Sensors", getIntakeLimit());
//		SmartDashboard.putBoolean("IR Sensor0", dio.get());
//		SmartDashboard.putBoolean("IR Sensor1",dio1.get());
		SmartDashboard.putBoolean("Soft Limits", elevator.getSoftLimits());
//		SmartDashboard.putBoolean("NavX Connected", isNavXConnected());
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}

	public static void resetNavX() {
		if(navXMicro != null)
			navXMicro.reset();
	}

	public static double getYaw()
	{
		if(navXMicro != null)
			return navXMicro.getYaw();
		else
			return -1;
	}
	
	public static double getTilt()
	{
		if(navXMicro != null)
			return navXMicro.getAngle();
		else
			return 0;
	}
	
	public static boolean isNavXConnected() {
		if(navXMicro != null)
			return navXMicro.isConnected();
		return false;
	}

	public static boolean getIntakeLimit() {
		return (!dio.get() || !dio1.get());	
	}
}