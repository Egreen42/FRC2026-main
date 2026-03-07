// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;



import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private final RobotContainer m_robotContainer;

  public Robot() {
    m_robotContainer = new RobotContainer();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      CommandScheduler.getInstance().schedule(m_autonomousCommand);
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void teleopExit() {}

  @Override
  public void testInit() {

      System.out.println("Starting Swerve Offset Calibration...");


      RobotContainer robotContainer = new RobotContainer();
      // Point modules forward
      robotContainer.getSwerve().drive(new ChassisSpeeds(1.0, 0.0, 0.0));

      try {
          Thread.sleep(2000); // allow modules to align
      } catch (InterruptedException e) {
          e.printStackTrace();
      }

      // Stop modules
      robotContainer.getSwerve().drive(new ChassisSpeeds(0.0, 0.0, 0.0));

      // Print encoder angles
      System.out.println("===== SWERVE MODULE OFFSETS =====");

      System.out.println("FrontLeft:  " +
          robotContainer.getSwerve().getSwerveDrive().getModules()[0].getAbsolutePosition());

      System.out.println("FrontRight: " +
          robotContainer.getSwerve().getSwerveDrive().getModules()[1].getAbsolutePosition());

      System.out.println("BackLeft:   " +
          robotContainer.getSwerve().getSwerveDrive().getModules()[2].getAbsolutePosition());

      System.out.println("BackRight:  " +
          robotContainer.getSwerve().getSwerveDrive().getModules()[3].getAbsolutePosition());

      System.out.println("=================================");
}

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {}
}
