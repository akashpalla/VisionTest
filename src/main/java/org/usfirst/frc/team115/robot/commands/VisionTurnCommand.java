package org.usfirst.frc.team115.robot.commands;

import org.usfirst.frc.team115.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class VisionTurnCommand extends Command {
    
    public VisionTurnCommand() {

    }

    protected void initialize() {
        Robot.drivetrain.setSetpoint(0);
       // Robot.drivetrain.enable();
    }

    protected void execute() {
           double angle = Robot.drivetrain.calculateTrajectory();
           Robot.drivetrain.driveWithTarget(Robot.oi.getThrottle2(), -angle);
    }

    protected boolean isFinished() {
        return !Robot.oi.getVisionButton();
    }

    protected void end() {
        
      //  Robot.drivetrain.disable();
        Robot.drivetrain.setLeftRightMotorOutputs(0, 0);
    }
}