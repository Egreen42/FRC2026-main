package frc.robot.subsystems;

import frc.robot.Constants;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;


import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class shooterSubsystem extends SubsystemBase{
    
    //Hardware
    private final SparkMax shooterMotor1;
    private final SparkMax shooterMotor2;

    //Encoders & PID
    private final RelativeEncoder shooterEncoder1;
    private final RelativeEncoder shooterEncoder2;
    private final SparkClosedLoopController PID1;
    private final SparkClosedLoopController PID2;

    //target default rpm
    public static double TARGET_RPM = 3000.0;
    public static final double RPM_Tolerance = 100.0;

    //Shooter PID Values
    private static final double kP = 0.0003;
    private static final double kI = 0.0;
    private static final double kD = 0.0;
    private static final double kV = 0.0212;
    private static final double kS = 0.0;


    @SuppressWarnings("removal")
    public shooterSubsystem() {

        shooterMotor1 = new SparkMax(Constants.kShooterMotor1Port, MotorType.kBrushless);
        shooterMotor2 = new SparkMax(Constants.kShooterMotor2Port, MotorType.kBrushless);

        PID1 = shooterMotor1.getClosedLoopController();
        PID2 = shooterMotor2.getClosedLoopController();
        shooterEncoder1 = shooterMotor1.getEncoder();
        shooterEncoder2 = shooterMotor2.getEncoder();


        //Build configs for controllers
        SparkMaxConfig shooter1Config = new SparkMaxConfig();
        shooter1Config
            .inverted(false)
            .idleMode(SparkMaxConfig.IdleMode.kCoast);
        shooter1Config.closedLoop
            .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
            .p(kP)
            .i(kI)
            .d(kD)
            .velocityFF(kV)
            .outputRange(-1.0,1.0);

        
        SparkMaxConfig shooter2Config = new SparkMaxConfig();
        shooter2Config
            .inverted(true)
            .idleMode(SparkMaxConfig.IdleMode.kCoast);
        shooter2Config.closedLoop
            .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
            .p(kP)
            .i(kI)
            .d(kD)
            .velocityFF(kV)
            .outputRange(-1.0, 1.0);

    
        //Apply the configs
        shooterMotor1.configure(
            shooter1Config,
            com.revrobotics.ResetMode.kResetSafeParameters,
            PersistMode.kPersistParameters
        );
        shooterMotor2.configure(
            shooter2Config,
            com.revrobotics.ResetMode.kResetSafeParameters,
            PersistMode.kPersistParameters
        );
    }


    // -------------------------------
    // Public Methods
    // -------------------------------

    //Default spin up to target ROM
    public void spinUptoRPM(double rpm){
        TARGET_RPM = rpm;
        PID1.setSetpoint(rpm, ControlType.kVelocity);
        PID2.setSetpoint(rpm, ControlType.kVelocity);
    }

    //Stop the Motors
    public void stop(){
        TARGET_RPM = 0.0;
        shooterMotor1.stopMotor();
        shooterMotor2.stopMotor();
    }

    //Is at speed
    public boolean isAtSpeed(){
        if(TARGET_RPM == 0.0) return false;
        return Math.abs(shooterEncoder1.getVelocity() - TARGET_RPM) < RPM_Tolerance &&
        
    }



}
