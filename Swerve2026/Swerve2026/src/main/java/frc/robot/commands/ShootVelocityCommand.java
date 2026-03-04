/**
 * --------------------------------------
 * This command will be used to run the 
 * shooter at a specifc RPM based on the 
 * PID controller and encoder build into
 * the spark and neo motors
 */

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;

public class ShootVelocityCommand extends Command{
    
    private final ShooterSubsystem shooter;
    private final double rpm;

    public ShootVelocityCommand(ShooterSubsystem shooter, double rpm){

        this.shooter = shooter;
        this.rpm = rpm;

        addRequirements(shooter);
    }

    @Override
    public void execute(){
        shooter.setRPM(rpm);
    }

    @Override
    public void end(boolean interrupted){
        shooter.stop();
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}