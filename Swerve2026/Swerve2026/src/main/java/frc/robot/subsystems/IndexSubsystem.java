package frc.robot.subsystems;

/**
 * ----------------------------------------------------------
 * This subsystem will handle the indexer, and just the indexer
 * the intake and shooter are in their own subsystem
 */

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

public class IndexSubsystem extends SubsystemBase{
    
    private SparkMax indexMotor;

    public IndexSubsystem(){
        indexMotor = new SparkMax(Constants.kIndexMotorID, MotorType.kBrushless);
    }

    public void runIndexer(double speed){
        indexMotor.set(speed);
    }

    public void stopIndexer(){
        indexMotor.stopMotor();
    }
    
}
