// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.IntakeOnCommand;
import frc.robot.commands.ResetGyroCommand;
import frc.robot.commands.RotateBounceCommand;
import frc.robot.commands.RotateDownCommand;
import frc.robot.commands.RotateHalfwayCommand;
import frc.robot.commands.RotateHomeCommand;
import frc.robot.commands.RotateStopCommand;
import frc.robot.commands.RotateTravelCommand;
import frc.robot.commands.ALLOFFCommand;
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
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.auto.AutoBuilder;
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
  private final SendableChooser<Command> autoChooser;

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

    NamedCommands.registerCommand("IntakeOn", new IntakeOnCommand(m_intakeSubsystem));
    NamedCommands.registerCommand("IntakeOff", new IntakeOffCommand(m_intakeSubsystem));
    NamedCommands.registerCommand("FlywheelOFf", new FlywheelOffCommand(m_FlywheelSubsystem, m_HotDogSubsystem));
    NamedCommands.registerCommand("FlywheelOn", new FlywheelOnCommand(m_FlywheelSubsystem));
    NamedCommands.registerCommand("FlywheelZ1", new FlywheelZ1Command(m_FlywheelSubsystem));
    NamedCommands.registerCommand("FlywheelZ2", new FlywheelZ2Command(m_FlywheelSubsystem));
    NamedCommands.registerCommand("FlywheelZ3", new FlywheelZ3Command(m_FlywheelSubsystem));
    NamedCommands.registerCommand("FlywheelZ4", new FlywheelZ4Command(m_FlywheelSubsystem));
    NamedCommands.registerCommand("FlywheelZ5", new FlywheelZ5Command(m_FlywheelSubsystem));
    NamedCommands.registerCommand("HotDogOn", new HotDogOnCommand(m_HotDogSubsystem,m_FlywheelSubsystem));
    NamedCommands.registerCommand("RotateDown", new RotateDownCommand(m_intakeSubsystem));
    NamedCommands.registerCommand("RotateHalfway", new RotateHalfwayCommand(m_intakeSubsystem));
    NamedCommands.registerCommand("RotateHome", new RotateHomeCommand(m_intakeSubsystem));
    NamedCommands.registerCommand("RotateStop", new RotateStopCommand(m_intakeSubsystem));
    NamedCommands.registerCommand("RotateTravel", new RotateTravelCommand(m_intakeSubsystem));
    NamedCommands.registerCommand("ALLOFF", new ALLOFFCommand(m_FlywheelSubsystem, m_intakeSubsystem, m_HotDogSubsystem));
    NamedCommands.registerCommand("Fire", new FireCommand(m_FlywheelSubsystem, m_intakeSubsystem, m_HotDogSubsystem));



    


   configureBindings();
    m_drivebaseSubsystem.setDefaultCommand(m_drivebaseSubsystem.driverControlledCommand());

     // Build an auto chooser. This will use Commands.none() as the default option.
    autoChooser = AutoBuilder.buildAutoChooser();
    SmartDashboard.putData("Auto Chooser", autoChooser);
  }

  /* Configure trigger->command mappings */
  private void configureBindings() {

    /*Driver 1 controlls */
    m_driverController.leftTrigger().onTrue(new FlywheelOnCommand(m_FlywheelSubsystem));
    m_driverController.rightTrigger().whileTrue(Commands.repeatingSequence(new RotateTravelCommand(m_intakeSubsystem).andThen(
      Commands.waitSeconds(1).andThen(
        new RotateBounceCommand(m_intakeSubsystem).andThen(
          Commands.waitSeconds(1))))));

    m_driverController.rightTrigger().whileTrue(new FireCommand(m_FlywheelSubsystem, m_intakeSubsystem, m_HotDogSubsystem));
    m_driverController.rightTrigger().onFalse(new FlywheelOffCommand(m_FlywheelSubsystem, m_HotDogSubsystem));
    m_driverController.rightTrigger().onFalse(new IntakeOffCommand(m_intakeSubsystem));
    m_driverController.rightBumper().onTrue(new FlywheelOffCommand(m_FlywheelSubsystem,m_HotDogSubsystem));
    m_driverController.rightStick().whileTrue(new IntakeOnCommand(m_intakeSubsystem));
    m_driverController.rightStick().onFalse(new IntakeOffCommand(m_intakeSubsystem));
    m_driverController.button(8).onTrue(new ResetGyroCommand(m_drivebaseSubsystem));
    
    m_driverController.leftStick().onTrue(new ALLOFFCommand(m_FlywheelSubsystem, m_intakeSubsystem, m_HotDogSubsystem));
    m_driverController.leftBumper().onTrue(new IntakeOffCommand(m_intakeSubsystem));
    m_driverController.a().onTrue(new RotateTravelCommand(m_intakeSubsystem));


    // m_driverController.b().onTrue(new IntakeOffCommand(m_intakeSubsystem));
    // m_driverController.a().onTrue(new RotateHalfwayCommand(m_intakeSubsystem));
    // m_driverController.x().onTrue(new RotateHomeCommand(m_intakeSubsystem));
    // m_driverController.y().onTrue(new RotateDownCommand(m_intakeSubsystem));

    /*Driver 2 Controlls */
    farmSim1.button(11).onTrue(new FlywheelZ1Command(m_FlywheelSubsystem));
    farmSim1.button(13).onTrue(new FlywheelZ2Command(m_FlywheelSubsystem));
    farmSim1.button(15).onTrue(new FlywheelZ3Command(m_FlywheelSubsystem));
    farmSim1.button(12).onTrue(new FlywheelZ4Command(m_FlywheelSubsystem));
    farmSim1.button(14).onTrue(new FlywheelZ5Command(m_FlywheelSubsystem));
    farmSim1.button(16).onTrue(new FlywheelOnCommand(m_FlywheelSubsystem));
    farmSim2.button(5).onTrue(new ALLOFFCommand(m_FlywheelSubsystem, m_intakeSubsystem, m_HotDogSubsystem));
    farmSim1.button(16).onTrue(new FlywheelOffCommand(m_FlywheelSubsystem,m_HotDogSubsystem));
    farmSim2.button(1).onTrue(new IntakeOnCommand(m_intakeSubsystem));
    farmSim2.button(2).onTrue(new IntakeOffCommand(m_intakeSubsystem));
    farmSim2.button(3).onTrue(new HotDogOnCommand(m_HotDogSubsystem,m_FlywheelSubsystem));
    farmSim2.button(3).onTrue(new RotateDownCommand(m_intakeSubsystem));
    farmSim2.button(4).onTrue(new RotateHalfwayCommand(m_intakeSubsystem));
    farmSim2.button(6).onTrue(new RotateHomeCommand(m_intakeSubsystem));













  }
  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
}
}
  