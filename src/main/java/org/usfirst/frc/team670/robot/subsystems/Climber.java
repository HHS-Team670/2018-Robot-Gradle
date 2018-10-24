package org.usfirst.frc.team670.robot.subsystems;

import org.usfirst.frc.team670.robot.commands.joystick_control.Joystick_Climber;
import org.usfirst.frc.team670.robot.constants.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Climber extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private TalonSRX climbMotor;
	private double currentLimit = 140;
	
	public Climber()
	{
		climbMotor = new TalonSRX(RobotMap.climberMotor);
	}
	
	/**
	 * 
	 * @param speed ADD A CURRENT LIMIT TO THE CODE SO THAT YOU DO NOT DESTORY THE CLIMBER???. naw.
	 */
	public void climb(double speed)
	{
		//System.out.print(" | Voltage:" + climbMotor.getMotorOutputVoltage());
//		if(climbMotor.getOutputCurrent() <= currentLimit)
			climbMotor.set(ControlMode.PercentOutput, speed);
//		else
//			climbMotor.set(ControlMode.PercentOutput, 0);
	}
	
	public TalonSRX getTalon()
	{
		return climbMotor;
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new Joystick_Climber());
    }
}

