package frc.robot.subsystems;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import edu.wpi.first.epilogue.Logged;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.OpenLoopRampsConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



@Logged
public class FlywheelSubsystem extends SubsystemBase{
    
    public enum FlywheelStates {
        SHOOT,
        PANIC,
        NOTHING,
    }


    /* Motors */
    public TalonFX flywheelLeft_talon;
    public TalonFX flywheelRight_talon;

    /* Motor Configurations */
    public Slot0Configs flywheelLeft_config;
    public Slot0Configs flywheelRight_config;

    public MotorOutputConfigs flywheelLeft_output_config;
    public MotorOutputConfigs flywheelRoght_output_config;

    public OpenLoopRampsConfigs flywheelLeft_open_loop_config;
    public OpenLoopRampsConfigs flywheelRignt_open_loop_config;

    public final VelocityVoltage flywheelLeft_voltage; 
    public final VelocityVoltage flywheelRight_voltage; 


    /* PID Controllers */
    public PIDController flywheelLeft_controller;
    public PIDController flywheelRignt_controller;

    public FlywheelStates current_state = FlywheelStates.NOTHING;
    
    public double flywheelLeft_speed = 0;
    public double flywheelRight_speed = 0;

    public FlywheelSubsystem(int flywheelLeft_id, int flywheelRignt_id) {
        
        /*FlywheelLeft Things */
        flywheelLeft_config = new Slot0Configs();
        flywheelLeft_config.kS = Constants.Flywheel.kFlywheelLeftS;
        flywheelLeft_config.kV = Constants.Flywheel.kFlywheelLeftV;
        flywheelLeft_config.kP = Constants.Flywheel.kFlywheelLeftP;
        flywheelLeft_config.kI = Constants.Flywheel.kFlywheelLeftI;
        flywheelLeft_config.kD = Constants.Flywheel.kFlywheelLeftD;

        flywheelLeft_talon.getConfigurator().apply(flywheelLeft_config);

        flywheelLeft_voltage = new VelocityVoltage(0).withSlot(0);
        flywheelLeft_controller = new PIDController(Constants.Flywheel.kFlywheelLeftP,Constants.Flywheel.kFlywheelLeftI,Constants.Flywheel.kFlywheelLeftD );


        /*FlywheeRight Things */
        flywheelRight_config = new Slot0Configs();
        flywheelRight_config.kS = Constants.Flywheel.kFlywheelRightS;
        flywheelRight_config.kV = Constants.Flywheel.kFlywheelRightV;
        flywheelRight_config.kP = Constants.Flywheel.kFlywheelRightP;
        flywheelRight_config.kI = Constants.Flywheel.kFlywheelRightI;
        flywheelRight_config.kD = Constants.Flywheel.kFlywheelRightD;

        flywheelRight_talon.getConfigurator().apply(flywheelRight_config);

        flywheelRight_voltage = new VelocityVoltage(0).withSlot(0);
        flywheelRignt_controller = new PIDController(Constants.Flywheel.kFlywheelRightP, Constants.Flywheel.kFlywheelRightI, Constants.Flywheel.kFlywheelRightD );

   
    }

    public void Flywheel_on() {
        this.flywheelLeft_speed = 60;
        this.flywheelRight_speed = 60;
    }

    public void FlywheelLeft_on() {
        this.flywheelLeft_speed = 60;
    }

    public void FlywheelRight_on() {
        this.flywheelRight_speed = 60;
    }

    public void Flywheel_off() {
        this.flywheelLeft_speed = 0;
        this.flywheelRight_speed = 0;
    }

    
    public double getFlywheelMotor1Velocity() {
        return this.flywheelLeft_talon.getVelocity().getValueAsDouble() * 60;
    }
    public double getFlywheelMotor2Velocity() {
        return this.flywheelRight_talon.getVelocity().getValueAsDouble() * 60;
    }

}
