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
public class HotDogSubsystem {
    
    public enum HotDogStates {
        STOP,
        MOVE,
        REVERSE
    }

    /* Motors */
    public TalonFX HotDog_talon;

    /* Motor Configurations */
    public TalonFXConfiguration hotdog_config;
   

    public MotorOutputConfigs hotdog_output_config;

    public OpenLoopRampsConfigs hotdog_open_loop_config;

    /* PID Controllers */
    public PIDController hotdog_controller;

    public HotDogStates current_state = HotDogStates.STOP;
    
    private CommandXboxController input_controller;
    public double hotdog_speed = 0;

    public HotDogSubsystem(int hotdog_id, CommandXboxController xbox_controller) {
        
        this.HotDog_talon = new TalonFX(hotdog_id);
        this.hotdog_output_config = new MotorOutputConfigs();
        this.hotdog_open_loop_config = new OpenLoopRampsConfigs()
            .withDutyCycleOpenLoopRampPeriod(0.25)
        ;
        this.hotdog_config = new TalonFXConfiguration()
            .withMotorOutput(hotdog_output_config)
            .withOpenLoopRamps(hotdog_open_loop_config)
        ;
        this.HotDog_talon.getConfigurator().apply(hotdog_config, 0.020);

    
    }

    public void SpinFoward() {
        this.hotdog_speed = 500;
    }

    public void SpinBackward() {
        this.hotdog_speed = -500;
    }

    public void StopHotDogs() {
        this.hotdog_speed = 0;
    }

    
    public double gethotdogVelocity() {
        return this.HotDog_talon.getVelocity().getValueAsDouble() * 60;
    }

}