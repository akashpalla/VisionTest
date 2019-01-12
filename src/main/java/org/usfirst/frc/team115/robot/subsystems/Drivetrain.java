package org.usfirst.frc.team115.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc.team115.robot.commands.DriveWithJoystick;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drivetrain extends Subsystem {

	NetworkTable table;
	NetworkTableEntry tx;
	NetworkTableEntry ty;
	NetworkTableEntry ta;

	public double targetX;
	public static final double KTURN_P = 0.05;
	WPI_TalonSRX frontLeft, backLeft, frontRight, backRight;
	SpeedControllerGroup leftDrive;
	SpeedControllerGroup rightDrive;	
	DifferentialDrive drive;
	
	
	public Drivetrain() {
		frontLeft = new WPI_TalonSRX(7);
		backLeft = new WPI_TalonSRX(11);
		frontRight = new WPI_TalonSRX(13);
		backRight= new WPI_TalonSRX(40);
		leftDrive = new SpeedControllerGroup(frontLeft, backLeft);
		rightDrive = new SpeedControllerGroup(frontRight, backRight);
		drive = new DifferentialDrive(leftDrive, rightDrive);
	
		table = NetworkTableInstance.getDefault().getTable("limelight");
		tx = table.getEntry("tx");
	}
	
	public void drive(double speed, double turnRate){
		drive.arcadeDrive(speed, turnRate);
	}

	public void setLeftRightMotorOutputs(double left, double right) {
		SmartDashboard.putNumber("LEFT", left);
		System.out.print("LEFT:" + left + " RIGHT: " + right);
		frontLeft.set(-left);
		frontRight.set(right);
		backLeft.set(-left);
		backRight.set(right);
	}

	public double calculateTrajectory() {
		targetX = tx.getDouble(0.0);
		SmartDashboard.putNumber("TARGET X",targetX);
		return targetX;
	}


	public void turnP(double angle) {
		double left = angle *KTURN_P;
		double right = -angle*KTURN_P;
		setLeftRightMotorOutputs(left, right);
	}
	
	public void stop() {
		drive.stopMotor();
	}
	
	protected void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoystick());
	}

}
