package frc.robot.subsystems;

import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.OpenLoopRampsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

@Logged

public class SwerveModule {
    /* Motors */
    public TalonFX drive_talon;
    public TalonFX steer_talon;

    /* Motor Configurations */
    public TalonFXConfiguration drive_config;
    public TalonFXConfiguration steer_config;

    public MotorOutputConfigs drive_output_config;
    public MotorOutputConfigs steer_output_config;

    public OpenLoopRampsConfigs drive_open_loop_config;
    public OpenLoopRampsConfigs steer_open_loop_config;

    /* Encoders */
    public CANcoder steer_cancoder;

    /* PID Controllers */
    public SimpleMotorFeedforward drive_feedforward_controller;
    public PIDController drive_controller;
    public PIDController angle_controller;

    public SwerveModuleState last_state;

    public double kMeterPerMotorRotation;

    /* Configuration */
    public boolean closedLoop;

    public SwerveModule(int steer_id, int drive_id, int angle_id, boolean reverse_drive) {
        /* Drive Motor */
        drive_talon = new TalonFX(drive_id);
        drive_output_config = new MotorOutputConfigs()
            .withNeutralMode(NeutralModeValue.Brake);
        
        if (reverse_drive) {
            drive_output_config = drive_output_config.withInverted(InvertedValue.CounterClockwise_Positive);
        } else {
            drive_output_config = drive_output_config.withInverted(InvertedValue.Clockwise_Positive);
        }

        /*
         * By limiting the ramp rate to 0.5 seconds the peak current goes down from 120A
         * to 80A
         */
        drive_open_loop_config = new OpenLoopRampsConfigs()
            .withDutyCycleOpenLoopRampPeriod(0.25)
        ;
        

        double kWheelCircumference = Constants.Drive.kwheelDiameter * Math.PI;
        kMeterPerMotorRotation = kWheelCircumference / Constants.Drive.kDriveRatio;

        drive_config = new TalonFXConfiguration()
            .withMotorOutput(drive_output_config)
            .withOpenLoopRamps(drive_open_loop_config)
        ;

        drive_talon.getConfigurator().apply(drive_config, 0.050);

        /* Steering Motor */
        steer_talon = new TalonFX(steer_id);
        steer_config = new TalonFXConfiguration()
            .withMotorOutput(
                new MotorOutputConfigs()
                    .withInverted(InvertedValue.CounterClockwise_Positive)
        );
        steer_talon.getConfigurator().apply(steer_config, 0.050);

        /* Steering Encoder */
        steer_cancoder = new CANcoder(angle_id);

        /* Feedforward and PID Controllers */
        drive_feedforward_controller = new SimpleMotorFeedforward(Constants.Drive.kMotorkS, Constants.Drive.kMotorkV);
        drive_controller = new PIDController(Constants.Drive.kDriveP, Constants.Drive.kDriveI, Constants.Drive.kDriveD);

        angle_controller = new PIDController(Constants.Drive.kSteerP, Constants.Drive.kSteerI, Constants.Drive.kSteerD);
        angle_controller.setTolerance(Constants.Drive.kSteerTolerance);
        angle_controller.enableContinuousInput(-180, 180);

        last_state = new SwerveModuleState(0, new Rotation2d(0));

        closedLoop = true;
    }

    public void set_module_state(SwerveModuleState state) {
        /*
         * Optimize the state - this handles reversing direction and minimizes the
         * change in heading
         */
        state.optimize(last_state.angle);
        /* Calculate Feedforward Voltage */
        /* (m/s) / (m/rev) * (60s/min) = rev/min */
        double requestedMotorSpeed = (state.speedMetersPerSecond / kMeterPerMotorRotation) * 60;
        double feedfowardVoltage = MathUtil.clamp(drive_feedforward_controller.calculate(requestedMotorSpeed),
                -Constants.Drive.kFeedForwardMaxVoltage, Constants.Drive.kFeedForwardMaxVoltage);
                
        SmartDashboard.putNumber("Meter per Motor rev", kMeterPerMotorRotation);
        SmartDashboard.putNumber("Requested Wheel Speed mps", state.speedMetersPerSecond);
        SmartDashboard.putNumber("Requested Wheel Speed rpm", requestedMotorSpeed);

        SmartDashboard.putNumber("Feed Forward Voltage", feedfowardVoltage);

        /* Calculate PID Voltage */
        double closedLoopVoltage;

        if (closedLoop) {
            closedLoopVoltage = MathUtil.clamp(
                    drive_controller.calculate(drive_talon.getVelocity().getValueAsDouble() * kMeterPerMotorRotation, requestedMotorSpeed),
                    -Constants.Drive.kClosedLoopMaxVoltage, Constants.Drive.kClosedLoopMaxVoltage);
        } else {
            closedLoopVoltage = 0;
        }

        /* Calculate Steer PID */
        double steerVoltage = MathUtil.clamp(angle_controller.calculate(get_raw_angle(), state.angle.getDegrees()),
                -Constants.Drive.kMaxSteerVoltage, Constants.Drive.kMaxSteerVoltage);

        /* Set the new powers to the SPARK Max controllers */
        drive_talon.setVoltage(feedfowardVoltage + closedLoopVoltage);
        steer_talon.setVoltage(steerVoltage);

        /* Update last angle for use next time */
        last_state = state;
    }

    public double get_raw_angle() {
        return steer_cancoder.getAbsolutePosition().getValueAsDouble() * 360 - 180;
    }

    public SwerveModulePosition get_module_position() {
        return new SwerveModulePosition(drive_talon.getPosition().getValueAsDouble() * kMeterPerMotorRotation, Rotation2d.fromDegrees(this.get_raw_angle()));
    }

    public SwerveModuleState get_module_speed() {
        return new SwerveModuleState(drive_talon.getVelocity().getValueAsDouble() * kMeterPerMotorRotation, Rotation2d.fromDegrees(this.get_raw_angle()));
    }

}
