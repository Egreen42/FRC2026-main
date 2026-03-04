package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class IntakeOutCommand extends Command{

    private final IntakeSubsystem intake;


    public IntakeOutCommand(IntakeSubsystem intake){
        this.intake = intake;

        addRequirements(intake);
    }

    @Override
    public void execute(){
        intake.runIntake(Constants.intakeOutSpeed);
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