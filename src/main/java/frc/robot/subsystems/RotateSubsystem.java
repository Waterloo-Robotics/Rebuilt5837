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
public class RotateSubsystem extends SubsystemBase {
    
    public enum RotateStates {
        HOME,
        INTAKE,
        HALFWAY,
        DOWN,
        REST
    }

    /* Motors */
    public TalonFX rotate_talon;


    /* Motor Configurations */
    public TalonFXConfiguration rotate_config;
    public MotorOutputConfigs rotate_output_config;
    public OpenLoopRampsConfigs rotate_open_loop_config;

    /* PID Controllers */
    public SimpleMotorFeedforward rotate_feedforward_controller;
    public PIDController rotate_controller;

    /*Absloute Encoder */
    WPI_TalonSRX rotate_EncoderTalon;
    public static SensorCollection rotate_EncoderCollection;




    public RotateStates current_state = RotateStates.HOME;
    public double Rotate_Home  = 90;
    public double Rotate_HalfWay = 175;
    public double Rotate_Bounce  = 155;
    public double Rotate_Travel  = 200;
    public double Rotate_Down = 209;
    
    public double target_position = Rotate_Home;

    double commanded_power = 0;

    
    public double intake_speed = 0;

    public RotateSubsystem(int rotate_id) {

        /*Rotate Things */
        this.rotate_talon = new TalonFX(rotate_id);
        this.rotate_output_config = new MotorOutputConfigs()
        .withInverted(InvertedValue.Clockwise_Positive);
        this.rotate_open_loop_config = new OpenLoopRampsConfigs()
            .withDutyCycleOpenLoopRampPeriod(1.2);
        this.rotate_config = new TalonFXConfiguration()
            .withMotorOutput(rotate_output_config)
            .withOpenLoopRamps(rotate_open_loop_config);
        this.rotate_talon.getConfigurator().apply(rotate_config, 0.020);
       
        rotate_controller = new PIDController(Constants.Intake.kRotateP, Constants.Intake.kRotateI, Constants.Intake.kRotateD);
        rotate_feedforward_controller = new SimpleMotorFeedforward(Constants.Intake.kRotateS, Constants.Intake.kRotateV);
        
        /*Encoder Things */
        rotate_EncoderTalon = new WPI_TalonSRX(25);
        rotate_EncoderTalon.setInverted(false);
        rotate_EncoderCollection = rotate_EncoderTalon.getSensorCollection();
    }

    public void setState(RotateStates state) {
        this.current_state = state;
    }

    public RotateStates getState() {
        return this.current_state;
    }


    public void rotate_home() {
        target_position = Rotate_Home;
     
    }

    public void rotate_halfway() {
        target_position = Rotate_HalfWay;
   
    }

    public void rotate_down() {
        target_position = Rotate_Down;
      
    }
    public void rotate_travel() {
        target_position = Rotate_Travel;
       
    }


    public void rotate_bounce_postion() {
        
        target_position = Rotate_Bounce;
        
    }


    public void rotate_stop() {
        rotate_talon.set(0);
    }
    
    public void rotate_intake() {

        double pid_term = rotate_controller.calculate(getRotatePosition(), target_position);
        // double feedforward_term = rotate_feedforward_controller.calculate(getRotateVelocity());

        double auto_power = MathUtil.clamp(pid_term, -2.5, 2.5);

        rotate_talon.setVoltage(auto_power);

        commanded_power = auto_power;
    }

    

    public double getRotateVelocity() {
        return this.rotate_talon.getVelocity().getValueAsDouble() * 60 / Constants.Intake.kRotateRatio;
    }
    
    public double getRotatePosition() {
        //return this.rotate_talon.getPosition().getValueAsDouble() * 360/* / Constants.Intake.kRotateRatio*/;
        System.out.print((double) (rotate_EncoderCollection.getPulseWidthPosition() / 4096.0) * 360.0);
        return (double) (rotate_EncoderCollection.getPulseWidthPosition() / 4096.0) * 360.0;
    }

    @Override
    public void periodic() {
        rotate_intake();
        SmartDashboard.putNumber("Intake", target_position);
    }

}