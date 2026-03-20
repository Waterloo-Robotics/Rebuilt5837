package frc.robot.subsystems;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import edu.wpi.first.epilogue.Logged;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.OpenLoopRampsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


@Logged
public class IntakeSubsystem extends SubsystemBase {
    
    public enum IntakeStates {
        HOME,
        INTAKE,
        REST
    }

    /* Motors */
    public TalonFX intake_talon;
    public TalonFX rotate_talon;

    /* Motor Configurations */
    public TalonFXConfiguration intake_config;
    public TalonFXConfiguration rotate_config;

    public MotorOutputConfigs intake_output_config;
    public MotorOutputConfigs rotate_output_config;

    public OpenLoopRampsConfigs intake_open_loop_config;
    public OpenLoopRampsConfigs rotate_open_loop_config;

    /* PID Controllers */
    public SimpleMotorFeedforward rotate_feedforward_controller;
    public PIDController intake_controller;
    public PIDController rotate_controller;

    public IntakeStates current_state = IntakeStates.HOME;
    
    private CommandXboxController input_controller;
    public double intake_speed = 0;

    public IntakeSubsystem(int intake_id, int rotate_id, CommandXboxController xbox_controller) {
        
        this.intake_talon = new TalonFX(intake_id);
        this.intake_output_config = new MotorOutputConfigs()
            .withInverted(InvertedValue.Clockwise_Positive);
        this.intake_open_loop_config = new OpenLoopRampsConfigs()
            .withDutyCycleOpenLoopRampPeriod(0.25)
        ;
        this.intake_config = new TalonFXConfiguration()
            .withMotorOutput(intake_output_config)
            .withOpenLoopRamps(intake_open_loop_config)
        ;
        this.intake_talon.getConfigurator().apply(intake_config, 0.020);

        // this.rotate_talon = new TalonFX(rotate_id);
        // this.rotate_output_config = new MotorOutputConfigs();
        // this.rotate_open_loop_config = new OpenLoopRampsConfigs()
        //     .withDutyCycleOpenLoopRampPeriod(0.25)
        // ;
        // this.rotate_config = new TalonFXConfiguration()
        //     .withMotorOutput(rotate_output_config)
        //     .withOpenLoopRamps(rotate_open_loop_config)
        // ;
        // this.rotate_talon.getConfigurator().apply(rotate_config, 0.020);

        intake_controller = new PIDController(Constants.Intake.kIntakeP, Constants.Intake.kIntakeI, Constants.Intake.kIntakeD );
        
        
        this.input_controller = xbox_controller;
    }

    public void intake_on() {
        this.intake_speed = 7500;
        setIntake(intake_speed);
    }

    public void intake_off() {
        this.intake_speed = 0;
        setIntake(intake_speed);
    }

    public void setIntake(double rpm) {
        double power = (rpm / 7500) /*/ Constants.Intake.kIntakeRatio*/;
        intake_talon.set(power);
    }

    public double getRotatePosition() {
        // return this.rotate_talon.getPosition().getValueAsDouble() * 360;
        return 0;
    }
    
    public double getIntakeVelocity() {
        return this.intake_talon.getVelocity().getValueAsDouble() * 60;
    }

    public void periodic() {
        SmartDashboard.putNumber("Intake RPM", intake_talon.getVelocity().getValueAsDouble() * 60);
    }

}