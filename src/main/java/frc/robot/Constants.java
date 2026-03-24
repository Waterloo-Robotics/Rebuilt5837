// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static class Drive {
    public static final int kMotorkS = 0;
    /* kV for in Volts * RPM */
    public static final double kMotorkV = 0.002;
    public static final double kwheelDiameter = Units.inchesToMeters(4.0);
    public static final double kDriveRatio = 7.03;
    /* Drive PID */
    public static final double kDriveP = 0.0;
    public static final double kDriveI = 0.0;
    public static final double kDriveD = 0.0;
    public static final double kFeedForwardMaxVoltage = 12;
    public static final double kClosedLoopMaxVoltage = 0;
    /* Steer PID */
    public static final double kSteerP = 0.048;
    public static final double kSteerI = 0.0;
    public static final double kSteerD = 0.0;
    public static final double kSteerTolerance = 1;
    public static final double kMaxSteerVoltage = 8;
  }

  public static class Drivebase {
    public static final double kWheelOffset_m = Units.inchesToMeters(12);
    public static final double kMaxDriveSpeed_m_s = Units.feetToMeters(5);
    public static final double kMaxSpinSpeed_rev_s = 0.2; // Rps
  }

  public static class Intake {
    public static final double kIntakeRatio = 5.0;
    public static final double kRotateRatio = 50.0;

    /* Intake PID */
    public static final double kIntakeS = 0.1;
    public static final double kIntakeV = 0.12;
    public static final double kIntakeP = 0.005;
    public static final double kIntakeI = 0.0;
    public static final double kIntakeD = 0.0;
    public static final double kIntakeTolerance = 1;
    public static final double kMaxIntakeVoltage = 8;
    /* Rotate PID */
    public static final double kRotateS = 0.001;
    public static final double kRotateV = 0.0002;
    public static final double kRotateP = 0.01;//75;
    public static final double kRotateI = 0;//.001;
    public static final double kRotateD = 0;//.0001;
    /* Rotate Feedforward */
    public static final int kMotorkS = 0;
      // kV for in Volts * RPM
    public static final double kMotorkV = 0.002;
    public static final double kFeedForwardMaxVoltage = 12;
    public static final double kClosedLoopMaxVoltage = 0;
  }

public static class HotDog {
    public static final double kHotDogRatio = 5.0;

    /* HotDog PID */
    public static final double kHotDogS = 0.1;
    public static final double kHotDogV = 0.12;
    public static final double kHotDogP = 0.048;
    public static final double kHotDogI = 0.0;
    public static final double kHotDogD = 0.0;
    public static final double kHotDogTolerance = 1;
    public static final double kMaxHotDogVoltage = 8;
  
    // kV for in Volts * RPM
    public static final double kMotorkV = 0.002;
    public static final double kFeedForwardMaxVoltage = 12;
    public static final double kClosedLoopMaxVoltage = 0;
  }

public static class Climber {
    public static final double kClimberRatio = 0.0;

    /* Climber PID */
    public static final double kCLimberP = 0.0;
    public static final double kClimberI = 0.0;
    public static final double kClimberD = 0.0;
    /* Climber Feedforward */
    public static final int kMotorkS = 0;
    // kV for in Volts * RPM
    public static final double kMotorkV = 0.002;
    public static final double kFeedForwardMaxVoltage = 12;
    public static final double kClosedLoopMaxVoltage = 0;
  }

  public static class Flywheel {
    public static final double kFlywheelLeftRatio = -1.0;
    public static final double kFlywheelRightRatio = 1.0;


    /* FlywheelLeft PID */
    public static final double kFlywheelLeftS = 0.1;
    public static final double kFlywheelLeftV = 0.12;
    public static final double kFlywheelLeftP = 0.0;
    public static final double kFlywheelLeftI = 0.0;
    public static final double kFlywheelLeftD = 0.0;
    /* Flywheel Right PID */
    public static final double kFlywheelRightS = 0.1;
    public static final double kFlywheelRightV = 0.12;
    public static final double kFlywheelRightP = 0.0;
    public static final double kFlywheelRightI = 0.0;
    public static final double kFlywheelRightD = 0.0;
    // kV for in Volts * RPM
    public static final double kMotorkV = 0.002;
    public static final double kFeedForwardMaxVoltage = 12;
    public static final double kClosedLoopMaxVoltage = 0;
  }
}
