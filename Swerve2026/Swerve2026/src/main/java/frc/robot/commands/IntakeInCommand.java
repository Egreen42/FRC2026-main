package frc.robot.commands;

import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.Constants;

import edu.wpi.first.wpilibj2.command.Command;


public class IntakeInCommand extends Command{

    private final IntakeSubsystem intake;


    public IntakeInCommand(IntakeSubsystem intake){
        this.intake = intake;

        addRequirements(intake);
    }

    @Override
    public void execute(){
        intake.runIntake(Constants.intakeInSpeed);
    }

    @Override
    public void end(boolean interrupted){
        intake.stopIntake();
    }

    @Override
    public boolean isFinished(){
        return false;
    }
    
}
