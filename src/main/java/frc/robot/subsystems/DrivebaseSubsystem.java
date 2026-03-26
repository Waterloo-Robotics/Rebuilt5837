// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import com.pathplanner.lib.config.PIDConstants;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

@Logged
public class DrivebaseSubsystem extends SubsystemBase {

    SwerveBaseSubsystem swerve_modules;

    public DrivebaseSubsystem(CommandXboxController driver_controller) {
        swerve_modules = new SwerveBaseSubsystem(driver_controller);

        // Load the RobotConfig from the GUI settings
        RobotConfig config;
        try {
            config = RobotConfig.fromGUISettings();
        } catch (Exception e) {
            e.printStackTrace();
            config = null;
        }

        // Configure AutoBuilder
        AutoBuilder.configure(
            swerve_modules::getPose,
            swerve_modules::resetPose,
            swerve_modules::getCurrentSpeeds,
            (speeds, feedforwards) -> swerve_modules.robotDriveRelative(speeds),
            new PPHolonomicDriveController(
                new PIDConstants(5.0, 0.0, 0.0), // Translation PID
                new PIDConstants(5.0, 0.0, 0.0)  // Rotation PID
            ),
            config,
            () -> {
                var alliance = DriverStation.getAlliance();
                if (alliance.isPresent()) {
                    return alliance.get() == DriverStation.Alliance.Red;
                }
                return false;
            },
            this
        );
    }

    public Command driverControlledCommand() {
        return Commands.run(swerve_modules::drive_xbox, this);
    }

    @Override
    public void periodic() {
        swerve_modules.update();
        SmartDashboard.putNumber("Robot Rotation", swerve_modules.gyro.getRotation2d().getDegrees());
    }

    @Override
    public void simulationPeriodic() {}
}