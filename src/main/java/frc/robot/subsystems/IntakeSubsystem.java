package frc.robot.subsystems;


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
    public TalonFX rotate_talon;


    /* Motor Configurations */
    public TalonFXConfiguration rotate_config;
    public MotorOutputConfigs rotate_output_config;
    public OpenLoopRampsConfigs rotate_open_loop_config;
    public Slot0Configs intake_config;
    public final VelocityVoltage intake_voltage; 

    /* PID Controllers */
    public SimpleMotorFeedforward rotate_feedforward_controller;
    public PIDController intake_controller;
    public PIDController rotate_controller;

    /*Absloute Encoder */
    WPI_TalonSRX rotate_EncoderTalon;
    public static SensorCollection rotate_EncoderCollection;




    public IntakeStates current_state = IntakeStates.HOME;
    public double Rotate_Home  = 10;
    public double Rotate_HalfWay = 50;
    public double Rotate_Down = 112.5;
    public double target_position = 0;

    double commanded_power = 0;

    
    public double intake_speed = 0;

    public IntakeSubsystem(int intake_id, int rotate_id) {
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

        /*Rotate Things */
        this.rotate_talon = new TalonFX(rotate_id);
        this.rotate_output_config = new MotorOutputConfigs()
        .withInverted(InvertedValue.Clockwise_Positive);
        this.rotate_open_loop_config = new OpenLoopRampsConfigs()
            .withDutyCycleOpenLoopRampPeriod(1.2)
        ;
        this.rotate_config = new TalonFXConfiguration()
            .withMotorOutput(rotate_output_config)
            .withOpenLoopRamps(rotate_open_loop_config)
        ;
        this.rotate_talon.getConfigurator().apply(rotate_config, 0.020);
       
        rotate_controller = new PIDController(Constants.Intake.kRotateP, Constants.Intake.kRotateI, Constants.Intake.kRotateD);
        rotate_feedforward_controller = new SimpleMotorFeedforward(Constants.Intake.kRotateS, Constants.Intake.kRotateV);
        
        /*Encoder Things */
        rotate_EncoderTalon = new WPI_TalonSRX(25);
        rotate_EncoderTalon.setInverted(false);
        rotate_EncoderCollection = rotate_EncoderTalon.getSensorCollection();
    }

    public void setState(IntakeStates state) {
        this.current_state = state;
    }

    public IntakeStates getState() {
        return this.current_state;
    }

    public void intake_on() {
        this.intake_speed = 600;
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

    public void rotate_home() {
        target_position = Rotate_Home;
        rotate_intake();
    }

    public void rotate_halfway() {
        target_position = Rotate_HalfWay;
        rotate_intake();
    }

    public void rotate_down() {
        target_position = Rotate_Down;
        rotate_intake();
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
      
        SmartDashboard.putNumber("Rotate Commanded", auto_power);
        SmartDashboard.putNumber("Rotate Actual", getRotateVelocity());
    }

    public double getIntakeVelocity() {
        return this.intake_talon.getVelocity().getValueAsDouble() * 60;
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
        SmartDashboard.putNumber("Intake RPM", intake_talon.getVelocity().getValueAsDouble() * 60);
              
        SmartDashboard.putNumber("Rotate Commanded RPM", commanded_power);
        SmartDashboard.putNumber("Rotate Actual RPM", getRotateVelocity());
        SmartDashboard.putNumber("Rotate RPM Error", commanded_power - getRotateVelocity());

        SmartDashboard.putNumber("Rotate Commanded Position", target_position);
        SmartDashboard.putNumber("Rotate Actual Position", getRotatePosition());
        SmartDashboard.putNumber("Rotate Position Error", target_position - getRotatePosition());
    }

}