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
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



@Logged
public class ClimberSubsystem extends SubsystemBase{
    
    public enum ClimberStates {
        NOTHING,
        LIFT,
        RELEASE,
        
    }

    /* Motors */
    public TalonFX climber_talon;

    /* Motor Configurations */
    public TalonFXConfiguration climber_config;
    public MotorOutputConfigs climber_output_config;
    public OpenLoopRampsConfigs climber_open_loop_config;

    /* PID Controllers */
    public SimpleMotorFeedforward climber_feedforward_controller;
    public PIDController climber_controller;

    public ClimberStates current_state = ClimberStates.NOTHING;
    
    private CommandXboxController input_controller;
    public double Climber_Home  = 0;
    public double Climber_Down = 10;
    public double Climber_Up = 50;
    public double target_position;

    public ClimberSubsystem(int climber_id) {
        
        this.climber_talon = new TalonFX(climber_id);
        this.climber_output_config = new MotorOutputConfigs();
        this.climber_open_loop_config = new OpenLoopRampsConfigs()
            .withDutyCycleOpenLoopRampPeriod(0.25)
        ;
        this.climber_config = new TalonFXConfiguration()
            .withMotorOutput(climber_output_config)
            .withOpenLoopRamps(climber_open_loop_config)
        ;
        this.climber_talon.getConfigurator().apply(climber_config, 0.020);

    
    }

    public void Climber_home() {
        target_position = Climber_Home;
        rotate_climber();
    }

    public void Climber_down() {
        target_position = Climber_Down;
        rotate_climber();
    }
    public void Climber_up() {
        target_position = Climber_Up;
        rotate_climber();
    }

    public void rotate_climber() {

        double pid_term = climber_controller.calculate(getClimberPosition(), target_position);
        double feedforward_term = climber_feedforward_controller.calculate(getClimberVelocity());

        double auto_power = MathUtil.clamp(pid_term + feedforward_term, -8, 8);

        climber_talon.setVoltage(auto_power);
      
        SmartDashboard.putNumber("Rotate Commanded", auto_power);
        SmartDashboard.putNumber("Rotate Actual", getClimberVelocity());
        SmartDashboard.putNumber("Rotate Pos", getClimberPosition());
    }

    public double getClimberPosition() {
        return this.climber_talon.getPosition().getValueAsDouble() * 360;
    }
    
    public double getClimberVelocity() {
        return this.climber_talon.getVelocity().getValueAsDouble() * 60;
    }

}