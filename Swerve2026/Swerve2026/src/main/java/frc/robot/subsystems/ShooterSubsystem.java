/**
 * ----------------------------------------------------------
 * This subsystem contains the shooter, and just the shooter
 * the indexer and intake will be in their own subsystems
 * ----------------------------------------------------------
 */

package frc.robot.subsystems;

import frc.robot.Constants;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.config.SparkMaxConfig;

@SuppressWarnings("removal")
public class ShooterSubsystem extends SubsystemBase{

    private SparkMax shooterMotor;
    private RelativeEncoder shooterEncoder;
    private SparkClosedLoopController shooterController;

    //PID constants
    private static final double kP = 0.0002;
    private static final double kI = 0.0;
    private static final double kD = 0.0;
    private static final double kFF = 0.00018;

    public ShooterSubsystem(){
        shooterMotor = new SparkMax(Constants.kShooterMotorID, MotorType.kBrushless);
        
        shooterEncoder = shooterMotor.getEncoder();
        shooterController = shooterMotor.getClosedLoopController();

        SparkMaxConfig config = new SparkMaxConfig();

        config.closedLoop.pid(kP, kI, kD);
        config.closedLoop.velocityFF(kFF);
        config.inverted(Constants.shooterInverted);

        shooterMotor.configure(
            config, 
            SparkMax.ResetMode.kResetSafeParameters,
            SparkMax.PersistMode.kPersistParameters
        );
    }

    public void runShooterOpenLoop(double speed){
        shooterMotor.set(speed);
    }

    public void stopShooter(){
        shooterMotor.stopMotor();
    }

    //CLOSED LOOP RPM CONTROL
    public void setRPM(double rpm){
        shooterController.setSetpoint(rpm, ControlType.kVelocity);
    }

    //TELEMETRY

    public double getRPM(){
        return shooterEncoder.getVelocity();
    }

    public boolean atSpeed(double targetRPM, double tolerance){
        return Math.abs(targetRPM - getRPM()) < tolerance;
    }

    public void stop(){
        shooterMotor.stopMotor();
    }
    

}


//TODO: JSON Motor IDs
//TODO: JSON Wheel Locations
//TODO; PID Tuning
//TODO: Pathplanner Testing
//TODO: AutoSelectorImplementation

