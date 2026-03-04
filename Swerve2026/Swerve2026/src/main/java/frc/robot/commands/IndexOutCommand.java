package frc.robot.commands;

import frc.robot.subsystems.IndexSubsystem;
import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.Command;

public class IndexOutCommand extends Command{

    private final IndexSubsystem index;

    public IndexOutCommand(IndexSubsystem index){
        this.index = index;

        addRequirements(index);
    }

    @Override
    public void execute(){
        index.runIndexer(Constants.indexOutSpeed);
    }

    @Override
    public void end(boolean interrupted){
        index.stopIndexer();
    }

    @Override
    public boolean isFinished(){
        return false;
    }
    
}
