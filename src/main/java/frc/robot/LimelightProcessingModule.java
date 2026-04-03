package frc.robot;

import frc.robot.LimelightHelpers;
import frc.robot.LimelightHelpers.LimelightResults;
import frc.robot.LimelightHelpers.LimelightTarget_Fiducial;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.AngleUnit;
import edu.wpi.first.units.DistanceUnit;
import edu.wpi.first.units.measure.Distance;

import java.util.List;

public class LimelightProcessingModule {

    public LimelightProcessingModule()
    {
//        this.limelight.pipelineSwitch(0);
//        this.limelight.start();
    }
    /* ------ */
    /* limelightResult
    *  The goal of this function is to return the robot position
    *  relative to the april tag.
    * */
    public Pose3d limelightResult() {
        LimelightResults results = LimelightHelpers.getLatestResults("limelight-right");

        if (results != null && results.valid)
        {
            Pose3d robot_pose = LimelightHelpers.getBotPose3d_TargetSpace("limeight-right");
            return robot_pose;
        }
        return null;
    }
}
