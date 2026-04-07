package frc.robot.subsystems;


import java.util.concurrent.TimeoutException;

import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.OpenLoopRampsConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


@Logged
public class IntakeSubsystem extends SubsystemBase {
    
    public enum IntakeStates {
        HOME,
        INTAKE,
        HALFWAY,
        DOWN,
        REST
    }

    /* Motors */
    public TalonFX intake_talon;

    /* Motor Configurations */
    public Slot0Configs intake_config;
    public final VelocityVoltage intake_voltage; 

    /* PID Controllers */
    public PIDController intake_controller;



    public IntakeStates current_state = IntakeStates.HOME;

    double commanded_power = 0;

    
    public double intake_speed = 0;

    public IntakeSubsystem(int intake_id) {
        this.intake_talon = new TalonFX(intake_id);

        /*intake Things */
        intake_config = new Slot0Configs();
        intake_config.kS = Constants.Intake.kIntakeS;
        intake_config.kV = Constants.Intake.kIntakeV;
        intake_config.kP = Constants.Intake.kIntakeP;
        intake_config.kI = Constants.Intake.kIntakeI;
        intake_config.kD = Constants.Intake.kIntakeD;

        intake_talon.getConfigurator().apply(intake_config);

        intake_voltage = new VelocityVoltage(0).withSlot(0);
        intake_controller = new PIDController(Constants.Intake.kIntakeP, Constants.Intake.kIntakeI, Constants.Intake.kIntakeD );

        
    }

    public void setState(IntakeStates state) {
        this.current_state = state;
    }

    public IntakeStates getState() {
        return this.current_state;
    }

    public void intake_on() {
        this.intake_speed = 6000;
        setIntake(intake_speed);
    }
     public void intake_bounce() {
        this.intake_speed = 3000;
        setIntake(intake_speed);
    }


    public void intake_off() {
        this.intake_speed = 0;
        intake_talon.set(0);
    }

    public void setIntake(double rpm) {
        double RPS = (rpm / 60);
        intake_talon.setControl(intake_voltage.withVelocity(RPS).withFeedForward(0.5));
    }

   
    
   

    public double getIntakeVelocity() {
        return this.intake_talon.getVelocity().getValueAsDouble() * 60;
    }

   
    @Override
    public void periodic() {
        
    }

}