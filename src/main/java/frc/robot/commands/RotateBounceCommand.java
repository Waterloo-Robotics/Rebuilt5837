// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.RotateSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class RotateBounceCommand extends Command {
   @SuppressWarnings("PMD.UnusedPrivateField")
  private final IntakeSubsystem I_subsystem;
  private final RotateSubsystem R_subsystem;


  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public RotateBounceCommand(IntakeSubsystem isubsystem,RotateSubsystem rsubsystem) {
    I_subsystem = isubsystem;
    R_subsystem = rsubsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(isubsystem);
    addRequirements(rsubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    R_subsystem.rotate_bounce_postion();
    I_subsystem.intake_bounce();

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return R_subsystem.target_position != R_subsystem.Rotate_Home;
  }
}
