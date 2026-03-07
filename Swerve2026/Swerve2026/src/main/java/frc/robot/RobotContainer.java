// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.File;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.SwerveSubsystem;

import frc.robot.commands.ShootVelocityCommand;
import frc.robot.commands.IntakeInCommand;
import frc.robot.commands.IntakeOutCommand;
import frc.robot.commands.IndexInCommand;
import frc.robot.commands.IndexOutCommand;

import swervelib.SwerveInputStream;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class RobotContainer {

  //Controllers
  private final CommandXboxController driverController = new CommandXboxController(Constants.kDriverControllerPort);
  private final CommandXboxController manipController = new CommandXboxController(Constants.kManipControllerPort);

  //Subsystems
  private final ShooterSubsystem shooter = new ShooterSubsystem();
  private final IntakeSubsystem intake = new IntakeSubsystem();
  private final IndexSubsystem index = new IndexSubsystem();
  private final SwerveSubsystem swerve = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(), "swerve"));

  private SendableChooser<Command> autoSelector = new SendableChooser<>();

  public RobotContainer() {

    swerve.zeroGyro();

    configureBindings();

    /**
     * Section to setup the chooser for autonomous
     */

    NamedCommands.registerCommand("test", Commands.print("I EXIST")); //TESTING LINE


    /**
     * -----------------------------------
     * NAMED COMMANDS FOR AUTO
     */
    

    NamedCommands.registerCommand("Shoot", new SequentialCommandGroup(
      new InstantCommand(() -> shooter.setRPM(Constants.shooterRPM), shooter),
      new WaitCommand(1),
      new InstantCommand(() -> index.runIndexer(Constants.indexOutSpeed), index),
      new InstantCommand(() -> intake.runIntake(Constants.intakeInSpeed), intake),
      new WaitCommand(5),
      new InstantCommand(() -> index.stopIndexer(), index),
      new InstantCommand(() -> intake.stopIntake(), intake),
      new InstantCommand(() -> shooter.stopShooter(), shooter)
    ));
    /**
     * -----------------------------------
     * Named Commands for Auto
     */

    autoSelector = AutoBuilder.buildAutoChooser();
    autoSelector.setDefaultOption("Do Nothing", Commands.none());
    autoSelector.addOption("Drive Forward", swerve.driveForward().withTimeout(1));
    SmartDashboard.putData("Auto Selector", autoSelector);
  }


  /**
   * DRIVE USING THE RIGHT STICK FOR TURNING, CONSTANT WITH A LEFT OR RIGHT
   */
  SwerveInputStream driveAngularVelocity = SwerveInputStream.of(swerve.getSwerveDrive(),
          () -> driverController.getLeftY() * -1, 
          () -> driverController.getLeftX() * -1)
          .withControllerRotationAxis(driverController::getRightX)
          .deadband(Constants.swerveDeadband)
          .scaleTranslation(0.8)
          .allianceRelativeControl(true);

  

  /**
   * DRIVE USING THE RIGHT STICK TO SELCT AN ANGLE TO TURN TO, NOT A CONSTANT TURN
   */

  SwerveInputStream driveDirectAngle = driveAngularVelocity.copy().withControllerHeadingAxis(driverController::getRightX, 
                  driverController::getRightY)
                  .headingWhile(true);

  Command driveFeildOrientedDirectAngle = swerve.driveFieldOriented(driveDirectAngle);
  Command driveFieldOrientedAngularVelocity = swerve.driveFieldOriented(driveAngularVelocity);
        

  private void configureBindings() {

    //Drivebase Bindings
    swerve.setDefaultCommand(driveFieldOrientedAngularVelocity);
    driverController.start().onTrue(new InstantCommand(() -> swerve.zeroGyro()));

    //Shooter Bindings
    manipController.rightBumper().whileTrue(new ShootVelocityCommand(shooter, Constants.shooterRPM));
    manipController.rightTrigger(0.5).whileTrue(Commands.parallel(
      new IndexInCommand(index),
      new IntakeInCommand(intake))); //THIS IS THE SHOOT COMMAND

    //Intake/Index Bindings
    manipController.leftTrigger(0.5).whileTrue(new IntakeInCommand(intake));
    manipController.leftBumper().whileTrue(Commands.parallel(
      new IntakeOutCommand(intake),
      new IndexOutCommand(index)));

    

  }

  public Command getAutonomousCommand() {
    return autoSelector.getSelected();
  }

  public SwerveSubsystem getSwerve(){
    return swerve;
  }
}
