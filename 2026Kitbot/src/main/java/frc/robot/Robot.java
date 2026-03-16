// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.cameraserver.CameraServer;

import edu.wpi.first.wpilibj.XboxController;


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



   boolean pressed = false;
   boolean pressed1 = false;



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
    if(driverController.getLeftY() > Constants.leftStickDeadzone || driverController.getLeftY() < -Constants.leftStickDeadzone){
      leftDriveMotor1.set(-driverController.getLeftY());
      leftDriveMotor2.set(-driverController.getLeftY());
    } else {
      leftDriveMotor1.set(0);
      leftDriveMotor2.set(0);
    }


    if(driverController.getRightY() > Constants.rightStickDeadzone || driverController.getRightY() < - Constants.rightStickDeadzone){
      rightDriveMotor1.set(driverController.getRightY());
      rightDriveMotor2.set(driverController.getRightY());
    } else {
      rightDriveMotor1.set(0);
      rightDriveMotor2.set(0);
    }

    //Intake
    if(manipController.getLeftTriggerAxis() > 0.5){
      intakeMotor.set(Constants.intakeSpeed);
      diverterMotor.set(-0.8);
      pressed = true;
    }else if(manipController.getLeftBumperButton()){
      intakeMotor.set(-Constants.intakeSpeed);
      diverterMotor.set(0.8);
      pressed = true;
    }
    else if(!manipController.getLeftBumperButton() && manipController.getLeftTriggerAxis() < 0.5 && pressed == true) {
      intakeMotor.set(0);
      diverterMotor.set(0);
      pressed = false;
      //

    }
  

    //Shooter
    if(manipController.getRightBumperButton()){
      shooterMotor.set(Constants.shooterSpeed);
    } else {
      shooterMotor.set(0);
    }



    if(manipController.getRightTriggerAxis() > 0.5){
      diverterMotor.set(Constants.diverterWhileShootSpeed);
      intakeMotor.set(Constants.intakeSpeed);
      pressed1 = true;
    } else if(manipController.getRightTriggerAxis() < 0.5 && pressed1 == true) {
      diverterMotor.set(0);
      intakeMotor.set(0);
      pressed1 = false;
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
