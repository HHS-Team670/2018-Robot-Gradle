package frc.team670.robot.subsystems;

import frc.team670.robot.constants.RobotMap;

public class Vision extends VisionSubsystemBase{

    public Vision(){
        super(new double[] {70, 235, 255}, new double[] {95, 255, 255}, 
            new VisionShapePointList(
                new VisionShapePoint(-498.475, 431.0, 0, 1), 
                new VisionShapePoint(-250.825, 0.0, 0, 2), 
                new VisionShapePoint(250.825, 0.0, 0, 3), 
                new VisionShapePoint(498.475, 431, 0, 4)
                )
            );
    }
    
}
