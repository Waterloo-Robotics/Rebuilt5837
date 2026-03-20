package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructPublisher;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.units.measure.Velocity;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import edu.wpi.first.epilogue.Logged;

import com.kauailabs.navx.frc.AHRS;
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
public class FlywheelSubsystem {
    
    public enum FlywheelStates {
        SHOOT,
        PANIC,
        NOTHING,
    }


    /* Motors */
    public TalonFX FlywheelMotor1_talon;
    public TalonFX FlywheelMotor2_talon;

    /* Motor Configurations */
    public TalonFXConfiguration FlywheelMotor1_config;
    public TalonFXConfiguration FlywheelMotor2_config;

    public MotorOutputConfigs FlywheelMotor1_output_config;
    public MotorOutputConfigs FlywheelMotor2_output_config;

    public OpenLoopRampsConfigs FlywheelMotor1_open_loop_config;
    public OpenLoopRampsConfigs FlywheelMotor2_open_loop_config;

    /* PID Controllers */
    public PIDController FlywheelMotor1_controller;
    public PIDController FlywheelMotor2_controller;

    public FlywheelStates current_state = FlywheelStates.NOTHING;
    
    private CommandXboxController input_controller;
    public double FlywheelMotor1_speed = 0;
    public double FlywheelMotor2_speed = 0;

    public FlywheelSubsystem(int FlywheelMotor1_id, int FlywheelMotor2_id, CommandXboxController xbox_controller) {
        
        this.FlywheelMotor1_talon = new TalonFX(FlywheelMotor1_id);
        this.FlywheelMotor1_output_config = new MotorOutputConfigs();
        this.FlywheelMotor1_open_loop_config = new OpenLoopRampsConfigs()
            .withDutyCycleOpenLoopRampPeriod(0.25)
        ;
        this.FlywheelMotor1_config = new TalonFXConfiguration()
            .withMotorOutput(FlywheelMotor1_output_config)
            .withOpenLoopRamps(FlywheelMotor1_open_loop_config)
        ;
        this.FlywheelMotor1_talon.getConfigurator().apply(FlywheelMotor1_config, 0.020);

        this.FlywheelMotor2_talon = new TalonFX(FlywheelMotor2_id);
        this.FlywheelMotor2_output_config = new MotorOutputConfigs();
        this.FlywheelMotor2_open_loop_config = new OpenLoopRampsConfigs()
            .withDutyCycleOpenLoopRampPeriod(0.25)
        ;
        this.FlywheelMotor2_config = new TalonFXConfiguration()
            .withMotorOutput(FlywheelMotor2_output_config)
            .withOpenLoopRamps(FlywheelMotor2_open_loop_config)
        ;
        this.FlywheelMotor2_talon.getConfigurator().apply(FlywheelMotor2_config, 0.020);
        
        this.input_controller = xbox_controller;
    }

    public void Flywheel_on() {
        this.FlywheelMotor1_speed = 500;
        this.FlywheelMotor2_speed = 500;

    }

    public void Flywheel_off() {
        this.FlywheelMotor1_speed = 0;
        this.FlywheelMotor2_speed = 0;
    }

    
    public double getFlywheelMotor1Velocity() {
        return this.FlywheelMotor1_talon.getVelocity().getValueAsDouble() * 60;
    }
    public double getFlywheelMotor2Velocity() {
        return this.FlywheelMotor2_talon.getVelocity().getValueAsDouble() * 60;
    }

}
