// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.IntakeOnCommand;
import frc.robot.commands.RotateDownCommand;
import frc.robot.commands.RotateHalfwayCommand;
import frc.robot.commands.RotateHomeCommand;
import frc.robot.commands.RotateStopCommand;
import frc.robot.commands.ALLOFFCommand;
import frc.robot.commands.BounceCommand;
import frc.robot.commands.FireCommand;
import frc.robot.commands.FlywheelOffCommand;
import frc.robot.commands.FlywheelOnCommand;
import frc.robot.commands.FlywheelZ1Command;
import frc.robot.commands.FlywheelZ2Command;
import frc.robot.commands.FlywheelZ3Command;
import frc.robot.commands.FlywheelZ4Command;
import frc.robot.commands.FlywheelZ5Command;
import frc.robot.commands.HotDogOnCommand;
import frc.robot.commands.IntakeOffCommand;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.DrivebaseSubsystem;
import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.auto.NamedCommands;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.HotDogSubsystem;
import frc.robot.subsystems.FlywheelSubsystem;

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
  public final IntakeSubsystem m_intakeSubsystem = new IntakeSubsystem(15,16);
  public final HotDogSubsystem m_HotDogSubsystem = new HotDogSubsystem(17);
  public final FlywheelSubsystem m_FlywheelSubsystem = new FlywheelSubsystem(19,18);
  public final ClimberSubsystem m_ClimberSubsystem = new ClimberSubsystem(20);



  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

    NamedCommands.registerCommand("FlywheelOFf", new FlywheelOffCommand(m_FlywheelSubsystem, m_HotDogSubsystem));
    NamedCommands.registerCommand("FlywheelOn", new FlywheelOnCommand(m_FlywheelSubsystem));
    NamedCommands.registerCommand("HotDogOn", new HotDogOnCommand(m_HotDogSubsystem));
    NamedCommands.registerCommand("IntakeOff", new IntakeOffCommand(m_intakeSubsystem));
    NamedCommands.registerCommand("IntakeOn", new IntakeOnCommand(m_intakeSubsystem));
    NamedCommands.registerCommand("RotateDown", new RotateDownCommand(m_intakeSubsystem));
    NamedCommands.registerCommand("RotateHalfway", new RotateHalfwayCommand(m_intakeSubsystem));
    NamedCommands.registerCommand("RotateHome", new RotateHomeCommand(m_intakeSubsystem));
    NamedCommands.registerCommand("RotateStop", new RotateStopCommand(m_intakeSubsystem));









    configureBindings();

    m_drivebaseSubsystem.setDefaultCommand(m_drivebaseSubsystem.driverControlledCommand());
  }

  /* Configure trigger->command mappings */
  private void configureBindings() {
    //m_driverController.leftTrigger().onTrue(new FlywheelOnCommand(m_FlywheelSubsystem));
    m_driverController.rightBumper().onTrue(new BounceCommand(m_intakeSubsystem));
    m_driverController.leftBumper().onTrue(new RotateStopCommand(m_intakeSubsystem));


    // m_driverController.leftBumper().onTrue(new FlywheelOffCommand(m_FlywheelSubsystem, m_HotDogSubsystem));
    // m_driverController.rightBumper().onTrue(new IntakeOnCommand(m_intakeSubsystem));
    // m_driverController.b().onTrue(new IntakeOffCommand(m_intakeSubsystem));
    // m_driverController.a().onTrue(new RotateHalfwayCommand(m_intakeSubsystem));
    // m_driverController.x().onTrue(new RotateHomeCommand(m_intakeSubsystem));
    // m_driverController.y().onTrue(new RotateDownCommand(m_intakeSubsystem));
    // farmSim1.button(0).onTrue(getAutonomousCommand());

  }
  public Command getAutonomousCommand() {
    return new PathPlannerAuto("Example Auto");
}
}
  