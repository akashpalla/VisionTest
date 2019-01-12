package org.usfirst.frc.team115.robot.commands;

import org.usfirst.frc.team115.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class VisionTurnCommand extends Command {
    
    public VisionTurnCommand() {

    }

    protected void initialize() {}

    protected void execute() {
        System.out.print("VISION TURN");
        double angle = Robot.drivetrain.calculateTrajectory();
        Robot.drivetrain.turnP(angle);
    }

    protected boolean isFinished() {
        return !Robot.oi.getVisionButton();
    }

    protected void end() {
        Robot.drivetrain.setLeftRightMotorOutputs(0, 0);
    }
}