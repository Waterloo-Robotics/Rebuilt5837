// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.DrivebaseSubsystem;
import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import com.pathplanner.lib.auto.NamedCommands;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
@Logged
public class RobotContainer {
  /* Joysticks */
  private final CommandXboxController m_driverController = new CommandXboxController(
      OperatorConstants.kDriverControllerPort);

  private final CommandJoystick farmSim1 = new CommandJoystick(2);
  private final CommandJoystick farmSim2 = new CommandJoystick(3);

  /* Robot Subsystems */
  public final DrivebaseSubsystem m_drivebaseSubsystem = new DrivebaseSubsystem(m_driverController);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    configureBindings();

    m_drivebaseSubsystem.setDefaultCommand(m_drivebaseSubsystem.driverControlledCommand());
  }

  /* Configure trigger->command mappings */
  private void configureBindings() {

  }
}
