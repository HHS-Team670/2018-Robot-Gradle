package org.usfirst.frc.team670.robot.subsystems;

import org.usfirst.frc.team670.robot.Robot;
import org.usfirst.frc.team670.robot.constants.RobotMap;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * 
 * Program to consolidate all sensor information - one class to retrieve information from
 * 
 * @author vsharma
 *
 */
public class Aggregator extends Thread{
	
	// Sensors
	//private AHRS navXMicro;
	private NetworkTable raspberryPi;
	private AnalogInput aio;
	
	//Booleans
	private boolean isNavXConnected, isCubeInIntake, encodersConnected, elevatorEncoders, collectVisionData;
	
	private boolean isCubeIn;
	private double powerCubeAngle = 0, cubeWidth = 0, frameWidth, tolerance = 10;
	
	public Aggregator(){
		raspberryPi = NetworkTable.getTable("raspberryPi");
		raspberryPi.setUpdateRate(0.03);
		collectVisionData = true;
	    aio = new AnalogInput(0);
		isCubeInIntake = false;
	    
		new Thread(() -> {
			while(true)
			{
				if(collectVisionData)
				{
					powerCubeAngle = raspberryPi.getNumber("angleToPowerCube", 0);
					cubeWidth = raspberryPi.getNumber("cubeWidth", 0);
					frameWidth = raspberryPi.getNumber("frameWidth", 0);
					
					//MAKE SURE THIS EXISTS ON THE RASPI @VIKRAM
					isCubeIn = raspberryPi.getBoolean("isCubeIn", false);
					
					if(Math.abs(cubeWidth - frameWidth) <= tolerance)
						isCubeInIntake = true;
					else
						isCubeInIntake = false;
					
					SmartDashboard.putNumber("RPI Angle:", powerCubeAngle);
				}
			}
		}).start();
	}
	
	public double getAngleToCube()
	{
		return powerCubeAngle;
	}
	
	public double getDistanceIntakeInches()
	{
		return aio.getValue()/19.6;
	}
	
	public void reset() {
		Robot.resetNavX();
	}

	public boolean isNavXConnected() {
		return Robot.isNavXConnected();
	}

	public static double getYaw() {
		return Robot.getYaw();
	}
	
	public void areEncodersWorking(boolean b) {
		encodersConnected = b;
	}

	public void areElevatorEncodersWorking(boolean b) {
		elevatorEncoders = b;
	}

	public boolean isCubeInIntake() {
		return Robot.getIntakeLimit();
	}
}

