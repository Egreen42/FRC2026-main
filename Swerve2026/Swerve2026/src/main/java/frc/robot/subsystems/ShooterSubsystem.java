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

import edu.wpi.first.wpilibj.simulation.FlywheelSim;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.DoublePublisher;

import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;

@SuppressWarnings("removal")
public class ShooterSubsystem extends SubsystemBase{

    private SparkMax shooterMotor;
    private RelativeEncoder shooterEncoder;
    private SparkClosedLoopController shooterController;

    //Shooter physics simulation
    private final FlywheelSim shooterSim = new FlywheelSim(
        LinearSystemId.createFlywheelSystem(
            DCMotor.getNEO(1),
            0.002,
            1.0),
            DCMotor.getNEO(1));

        //Publish RPM
        private final DoublePublisher shooterRPM = NetworkTableInstance.getDefault()
            .getDoubleTopic("ShooterRPM")
            .publish();
 
        private final DoublePublisher shooterTargetRPM = NetworkTableInstance.getDefault()
            .getDoubleTopic("ShooterTargetRPM")
            .publish();

        private final DoublePublisher shooterMotorOutput =
            NetworkTableInstance.getDefault()
            .getDoubleTopic("ShooterMotorOutput")
            .publish();
    

    //PID constants
    private static final double kP = 0.0002;
    private static final double kI = 0.0;
    private static final double kD = 0.0;
    private static final double kFF = 0.00018;

    private double targetRPM = 0.0;

    //Mechanism visualization
    private final Mechanism2d shooterMechanism = new Mechanism2d(2,2);
    private final MechanismRoot2d shooterRoot = shooterMechanism.getRoot("Shooter", 1, 1);

    private final MechanismLigament2d shooterWheel =
          new MechanismLigament2d(
        "ShooterWheel",
             0.5,
             0,
             6,
            new Color8Bit(Color.kOrange));

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

        shooterRoot.append(shooterWheel);
        SmartDashboard.putData("ShooterMechanism",shooterMechanism);
    }

    public void runShooterOpenLoop(double speed){
        shooterMotor.set(speed);
    }

    public void stopShooter(){
        shooterMotor.stopMotor();
    }

    //CLOSED LOOP RPM CONTROL
    public void setRPM(double rpm){
        targetRPM = rpm;
        shooterController.setSetpoint(rpm, ControlType.kVelocity);
    }

    //TELEMETRY

    public double getRPM(){

        if(edu.wpi.first.wpilibj.RobotBase.isSimulation()){
            return shooterSim.getAngularVelocityRPM();
        }
        return shooterEncoder.getVelocity();

    }

    public boolean atSpeed(double targetRPM, double tolerance){
        return Math.abs(targetRPM - getRPM()) < tolerance;
    }

    public void stop(){
        shooterMotor.stopMotor();
    }
    
    @Override
    public void periodic(){
        shooterRPM.set(getRPM());
        shooterTargetRPM.set(targetRPM);
        shooterMotorOutput.set(shooterMotor.getAppliedOutput());
        shooterWheel.setAngle(shooterWheel.getAngle() + getRPM() * 0.02);
    }

    @Override
    public void simulationPeriodic(){
        double appliedVoltage = shooterMotor.getAppliedOutput() * 12.0;

        shooterMotorOutput.set(shooterMotor.getAppliedOutput());

        shooterSim.setInput(appliedVoltage);
        shooterSim.update(0.02);
    }


}


//TODO: JSON Motor IDs
//TODO: JSON Wheel Locations
//TODO; PID Tuning
//TODO: Pathplanner Testing
//TODO: AutoSelectorImplementation

