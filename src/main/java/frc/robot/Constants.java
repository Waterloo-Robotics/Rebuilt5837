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
    public static final double kDriveRatio = 7.03;
    /* Intake PID */
    public static final double kIntakeP = 0.048;
    public static final double kIntakeI = 0.0;
    public static final double kIntakeD = 0.0;
    public static final double kIntakeTolerance = 1;
    public static final double kMaxIntakeVoltage = 8;
    /* Rotate PID */
    public static final double kRotateP = 0.0;
    public static final double kRotateI = 0.0;
    public static final double kRotateD = 0.0;
    /* Rotate Feedforward */
    public static final int kMotorkS = 0;
      // kV for in Volts * RPM
    public static final double kMotorkV = 0.002;
    public static final double kFeedForwardMaxVoltage = 12;
    public static final double kClosedLoopMaxVoltage = 0;
  }

public static class HotDog {
    public static final double kDriveRatio = 7.03;

    /* HotDog PID */
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
    public static final double kDriveRatio = 7.03;

    /* Climber PID */
    public static final double kRotateP = 0.0;
    public static final double kRotateI = 0.0;
    public static final double kRotateD = 0.0;
    /* Climber Feedforward */
    public static final int kMotorkS = 0;
    // kV for in Volts * RPM
    public static final double kMotorkV = 0.002;
    public static final double kFeedForwardMaxVoltage = 12;
    public static final double kClosedLoopMaxVoltage = 0;
  }

  public static class Flywheel {
    public static final double kDriveRatio = 7.03;

    /* Flywheel 1 PID */
    public static final double kFlywheelMotor1P = 0.0;
    public static final double kFlywheelMotor1I = 0.0;
    public static final double kFlywheelMotor1D = 0.0;
    /* Flywheel 2 PID */
    public static final double kFlywheelMotor2P = 0.0;
    public static final double kFlywheelMotor2I = 0.0;
    public static final double kFlywheelMotor2D = 0.0;
    // kV for in Volts * RPM
    public static final double kMotorkV = 0.002;
    public static final double kFeedForwardMaxVoltage = 12;
    public static final double kClosedLoopMaxVoltage = 0;
  }
}
