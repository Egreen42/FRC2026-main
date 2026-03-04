package frc.robot.subsystems;


/**
 * ----------------------------------------------------------
 * This subsystem will handle the intake, and just the intake
 * the indexer and shooter are in their own subsystem
 * ----------------------------------------------------------
 */


import frc.robot.Constants;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase{

    private SparkMax intakeMotor;

    public IntakeSubsystem(){
        intakeMotor = new SparkMax(Constants.kIntakeMotorID, MotorType.kBrushless);
    }

    public void runIntake(double speed){
        intakeMotor.set(speed);
    }

    public void stopIntake(){
        intakeMotor.stopMotor();
    }
    
}
