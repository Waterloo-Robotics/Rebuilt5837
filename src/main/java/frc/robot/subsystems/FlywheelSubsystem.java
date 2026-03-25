package frc.robot.subsystems;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import edu.wpi.first.epilogue.Logged;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.OpenLoopRampsConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
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
    public TalonFXConfiguration flywheelLeft_config;
    public TalonFXConfiguration flywheelRight_config;

    public Slot0Configs flywheelLeft_slot0_config;
    public Slot0Configs flywheelRight_slot0_config;

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


        flywheelLeft_talon = new TalonFX(flywheelLeft_id);
        flywheelRight_talon = new TalonFX(flywheelRignt_id);

        
        /*FlywheelLeft Things */
        flywheelLeft_output_config = new MotorOutputConfigs()
            .withInverted(InvertedValue.Clockwise_Positive);
        flywheelLeft_config = new TalonFXConfiguration()
            .withMotorOutput(flywheelLeft_output_config);
        flywheelLeft_slot0_config = flywheelLeft_config.Slot0;
        flywheelLeft_slot0_config.kS = Constants.Flywheel.kFlywheelLeftS;
        flywheelLeft_slot0_config.kV = Constants.Flywheel.kFlywheelLeftV;
        flywheelLeft_slot0_config.kP = Constants.Flywheel.kFlywheelLeftP;
        flywheelLeft_slot0_config.kI = Constants.Flywheel.kFlywheelLeftI;
        flywheelLeft_slot0_config.kD = Constants.Flywheel.kFlywheelLeftD;

        flywheelLeft_talon.getConfigurator().apply(flywheelLeft_config);

        flywheelLeft_voltage = new VelocityVoltage(0).withSlot(0);
        flywheelLeft_controller = new PIDController(Constants.Flywheel.kFlywheelLeftP,Constants.Flywheel.kFlywheelLeftI,Constants.Flywheel.kFlywheelLeftD );


        /*FlywheeRight Things */
        flywheelRight_slot0_config = new Slot0Configs();
        flywheelRight_slot0_config.kS = Constants.Flywheel.kFlywheelRightS;
        flywheelRight_slot0_config.kV = Constants.Flywheel.kFlywheelRightV;
        flywheelRight_slot0_config.kP = Constants.Flywheel.kFlywheelRightP;
        flywheelRight_slot0_config.kI = Constants.Flywheel.kFlywheelRightI;
        flywheelRight_slot0_config.kD = Constants.Flywheel.kFlywheelRightD;

        flywheelRight_talon.getConfigurator().apply(flywheelRight_slot0_config);


        flywheelRight_voltage = new VelocityVoltage(0).withSlot(0);
        flywheelRignt_controller = new PIDController(Constants.Flywheel.kFlywheelRightP, Constants.Flywheel.kFlywheelRightI, Constants.Flywheel.kFlywheelRightD );

   
    }

    public void Flywheel_on() {
        /*wonrks supper well arround 8 1/4 ft */
        this.flywheelLeft_speed = 2500;
        this.flywheelRight_speed = 2500;
        setFlywheelLeft(flywheelLeft_speed);
        setFlywheelRight(flywheelLeft_speed);
    }

    public void Flywheel_RPM_ZONE_1(){
        this.flywheelLeft_speed = 5000;
        this.flywheelRight_speed = 5000;
        setFlywheelLeft(flywheelLeft_speed);
        setFlywheelRight(flywheelLeft_speed);
    }
    public void Flywheel_RPM_ZONE_2(){
        this.flywheelLeft_speed = 3500;
        this.flywheelRight_speed = 3500;
        setFlywheelLeft(flywheelLeft_speed);
        setFlywheelRight(flywheelLeft_speed);
    }
    public void Flywheel_RPM_ZONE_3(){
        this.flywheelLeft_speed = 2000;
        this.flywheelRight_speed = 2000;
        setFlywheelLeft(flywheelLeft_speed);
        setFlywheelRight(flywheelLeft_speed);
    }
    public void Flywheel_RPM_ZONE_4(){
        this.flywheelLeft_speed = 3000;
        this.flywheelRight_speed = 3000;
        setFlywheelLeft(flywheelLeft_speed);
        setFlywheelRight(flywheelLeft_speed);
    }
    public void Flywheel_RPM_ZONE_5(){
        this.flywheelLeft_speed = 3500;
        this.flywheelRight_speed = 3500;
        setFlywheelLeft(flywheelLeft_speed);
        setFlywheelRight(flywheelLeft_speed);
    }

    public void Flywheel_reverse() {
        this.flywheelLeft_speed = -1000;
        this.flywheelRight_speed = -1000;
        setFlywheelLeft(flywheelLeft_speed);
        setFlywheelRight(flywheelLeft_speed);
    }
    

    // public void FlywheelLeft_on() {
    //     this.flywheelLeft_speed = 10;
    //     setFlywheelLeft(flywheelLeft_speed);

    // }

    // public void FlywheelRight_on() {
    //     this.flywheelRight_speed = 10;
    //     setFlywheelRight(flywheelRight_speed);

    // }
    public void setFlywheelRight(double rpm) {
        double RPS = (rpm / 60);
        flywheelRight_talon.setControl(flywheelRight_voltage.withVelocity(RPS).withFeedForward(0.5));
        
    }
      public void setFlywheelLeft(double rpm) {
        double RPS = (rpm / 60);
        flywheelLeft_talon.setControl(flywheelLeft_voltage.withVelocity(RPS).withFeedForward(0.5));
        
    }
    

    public void Flywheel_off() {
        this.flywheelLeft_speed = 0;
        this.flywheelRight_speed = 0;
        flywheelLeft_talon.set(0);
        flywheelRight_talon.set(0);

    }

    
    public double getFlywheelMotor1Velocity() {
        return this.flywheelLeft_talon.getVelocity().getValueAsDouble() * 60 / Constants.Flywheel.kFlywheelLeftRatio;
    }
    public double getFlywheelMotor2Velocity() {
        return this.flywheelRight_talon.getVelocity().getValueAsDouble() * 60;
    }

}
