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
public class ClimberSubsystem {
    
    public enum ClimberStates {
        NOTHING,
        LIFT,
        RELEASE,
        
    }

    /* Motors */
    public TalonFX Climber_talon;

    /* Motor Configurations */
    public TalonFXConfiguration Climber_config;
   

    public MotorOutputConfigs Climber_output_config;

    public OpenLoopRampsConfigs Climber_open_loop_config;

    /* PID Controllers */
    public SimpleMotorFeedforward Climber_feedforward_controller;
    public PIDController Climber_controller;

    public ClimberStates current_state = ClimberStates.NOTHING;
    
    private CommandXboxController input_controller;
    public double Climber_speed = 0;
    public double Climber_postion = 0;

    public ClimberSubsystem(int Climber_id, CommandXboxController xbox_controller) {
        
        this.Climber_talon = new TalonFX(Climber_id);
        this.Climber_output_config = new MotorOutputConfigs();
        this.Climber_open_loop_config = new OpenLoopRampsConfigs()
            .withDutyCycleOpenLoopRampPeriod(0.25)
        ;
        this.Climber_config = new TalonFXConfiguration()
            .withMotorOutput(Climber_output_config)
            .withOpenLoopRamps(Climber_open_loop_config)
        ;
        this.Climber_talon.getConfigurator().apply(Climber_config, 0.020);

    
    }

    public void ClimberUP() {
        this.Climber_postion = 50;
    }

    public void ClimberDOWN() {
        this.Climber_postion = 0;
    }

    public double getClimberPosition() {
        return this.Climber_talon.getPosition().getValueAsDouble() * 360;
    }
    
    public double getClimberVelocity() {
        return this.Climber_talon.getVelocity().getValueAsDouble() * 60;
    }

}