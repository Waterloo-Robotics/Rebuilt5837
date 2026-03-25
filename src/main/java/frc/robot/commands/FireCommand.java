// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.FlywheelSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.HotDogSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class FireCommand extends Command {
  @SuppressWarnings("PMD.UnusedPrivateField")
  private final FlywheelSubsystem F_subsystem;
  private final IntakeSubsystem I_subsystem;
  private final HotDogSubsystem HD_subsystem;



  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public FireCommand(FlywheelSubsystem Fsubsystem, IntakeSubsystem Isubsystem, HotDogSubsystem HDsubsystem ){
    F_subsystem = Fsubsystem; 
    I_subsystem = Isubsystem;
    HD_subsystem = HDsubsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Fsubsystem);
    addRequirements(Isubsystem);
    addRequirements(HDsubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //F_subsystem.Flywheel_on();
    I_subsystem.intake_on();
    HD_subsystem.SpinFoward();
    I_subsystem.rotate_bounce();

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
