package frc.robot.subsystems;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import edu.wpi.first.epilogue.Logged;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import edu.wpi.first.math.controller.PIDController;



@Logged
public class HotDogSubsystem extends SubsystemBase{
    
    public enum HotDogStates {
        STOP,
        MOVE,
        REVERSE
    }

    /* Motors */
    public TalonFX HotDog_talon;

    /* Motor Configurations */
    public Slot0Configs hotdog_config;
    public final VelocityVoltage hotdog_voltage; 


    /* PID Controllers */
    public PIDController hotdog_controller;

    public HotDogStates current_state = HotDogStates.STOP;
    
    public double hotdog_speed = 0;

    public HotDogSubsystem(int hotdog_id) {
        
        /*intake Things */
        hotdog_config = new Slot0Configs();
        hotdog_config.kS = Constants.HotDog.kHotDogS;
        hotdog_config.kV = Constants.HotDog.kHotDogV;
        hotdog_config.kP = Constants.HotDog.kHotDogP;
        hotdog_config.kI = Constants.HotDog.kHotDogI;
        hotdog_config.kD = Constants.HotDog.kHotDogD;

        HotDog_talon.getConfigurator().apply(hotdog_config);

        hotdog_voltage = new VelocityVoltage(0).withSlot(0);
        hotdog_controller = new PIDController(Constants.HotDog.kHotDogP,Constants.HotDog.kHotDogI,Constants.HotDog.kHotDogD );


    
    }

    public void SpinFoward() {
        this.hotdog_speed = 60;
    }

    public void SpinBackward() {
        this.hotdog_speed = -60;
    }

    public void StopHotDogs() {
        this.hotdog_speed = 0;
    }

    
    public double gethotdogVelocity() {
        return this.HotDog_talon.getVelocity().getValueAsDouble() * 60;
    }

}