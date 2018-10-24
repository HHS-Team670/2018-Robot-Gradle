package org.usfirst.frc.team670.robot.commands.state_change;

import java.util.HashMap;

import org.usfirst.frc.team670.robot.Robot;
import org.usfirst.frc.team670.robot.commands.LoggingCommand;
import org.usfirst.frc.team670.robot.constants.enums.OperatorState;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Sets operator control to a different value
 * 
 * @author vsharma
 */
public class Set_OperatorControl extends LoggingCommand {

	private OperatorState os;
	
    public Set_OperatorControl(OperatorState os) {
    	this.os = os;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	logInitialize(new HashMap<String, Object>() {{
    		put("NewOperatorState", os.toString());
		}});
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.oi.setOperatorCommand(os);
    	logExecute(new HashMap<String, Object>() {{
    		put("NewOperatorState", os.toString());
		}});
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	logFinished(new HashMap<String, Object>() {{
    		put("NewOperatorState", os.toString());
		}});
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}