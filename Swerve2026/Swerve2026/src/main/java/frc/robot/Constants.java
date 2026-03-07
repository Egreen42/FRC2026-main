package frc.robot;

public class Constants {
    
    //Shooter Subsystem
    public static final int kShooterMotorID = 13;
    public static final double rpmTolerance = 100;
    public static final boolean shooterInverted = true;
    public static final int shooterRPM = 2000;

    //intake subsystem
    public static final int kIntakeMotorID = 10;
    public static final double intakeInSpeed = 0.5;
    public static final double intakeOutSpeed = -0.5;

    //Index Subsystem
    public static final int kIndexMotorID = 11;
    public static final double indexInSpeed = 0.8;
    public static final double indexOutSpeed = -0.8;
    public static final int kRearIndexMotorID = 12;

    //Swerve Subsystem
    public static final double MAX_SPEED = 4.5; //Feet per Second
    public static final double swerveDeadband = 0.1;

    //Controllers
    public static final int kDriverControllerPort = 0;
    public static final int kManipControllerPort = 1;


}
