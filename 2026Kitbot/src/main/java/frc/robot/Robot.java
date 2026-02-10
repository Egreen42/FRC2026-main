// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.cameraserver.CameraServer;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.motorcontrol.Spark;

/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class Robot extends TimedRobot {
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */


   //Motor and Controller Creation
   SparkMax leftDriveMotor1 = new SparkMax(Constants.kLeftDriveMotor1, MotorType.kBrushed);
   SparkMax leftDriveMotor2 = new SparkMax(Constants.kLeftDriveMotor2, MotorType.kBrushed);
   SparkMax rightDriveMotor1 = new SparkMax(Constants.kRightDriveMotor1, MotorType.kBrushed);
   SparkMax rightDriveMotor2 = new SparkMax(Constants.kRightDriveMotor2, MotorType.kBrushed);

   SparkMax intakeMotor = new SparkMax(Constants.kIntakeMotorPort, MotorType.kBrushless );
   SparkMax shooterMotor = new SparkMax(Constants.kShooterMotorPort, MotorType.kBrushless);
   SparkMax diverterMotor = new SparkMax(Constants.kDiverterMotorPort, MotorType.kBrushless);

   XboxController driverController = new XboxController(Constants.kDriverControllerPort);
   XboxController manipController = new XboxController(Constants.kManipControllerPort);



  public Robot() {
    //Begin video capture to the smart dashboard
    CameraServer.startAutomaticCapture();
  }

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {
    
    //DriveTrain, Right is reversed
    leftDriveMotor1.set(-driverController.getLeftY());
    leftDriveMotor2.set(-driverController.getLeftY());

    rightDriveMotor1.set(driverController.getRightY());
    rightDriveMotor2.set(driverController.getRightY());

    //Intake
    if(manipController.getLeftTriggerAxis() > 0.5){
      intakeMotor.set(Constants.intakeSpeed);
      diverterMotor.set(Constants.diverterSpeed);
    }else if(manipController.getLeftBumperButtonPressed()){
      intakeMotor.set(-Constants.intakeSpeed);
      diverterMotor.set(-Constants.diverterSpeed);
    }
    else {
      intakeMotor.set(0);
      diverterMotor.set(0);

    }

    //Shooter
    if(manipController.getRightTriggerAxis() > 0.5){
      shooterMotor.set(Constants.shooterSpeed);
    } else {
      shooterMotor.set(0);
    }

  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
