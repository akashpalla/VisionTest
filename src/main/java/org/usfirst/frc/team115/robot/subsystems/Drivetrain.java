package org.usfirst.frc.team115.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMax.IdleMode;

import org.usfirst.frc.team115.robot.Robot;
import org.usfirst.frc.team115.robot.commands.DriveWithJoystick;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drivetrain extends PIDSubsystem {

	NetworkTable table;
	NetworkTableEntry tx;
	NetworkTableEntry ty;
	NetworkTableEntry ta;

	public double targetX;
	public static final double KTURN_P = 0.03;
	public static final double KTURN_DRIVE_P = .01;
	public static final double KTURN_DRIVE = .01;

	CANSparkMax frontLeft, backLeft, frontRight, backRight;
	SpeedControllerGroup leftDrive;
	SpeedControllerGroup rightDrive;	
	DifferentialDrive drive;
	
	
	public Drivetrain() {
		super(0.07, 0, 0);
		
		frontLeft = new CANSparkMax(4, CANSparkMaxLowLevel.MotorType.kBrushless);
		backLeft = new CANSparkMax(3,CANSparkMaxLowLevel.MotorType.kBrushless );
		frontRight = new CANSparkMax(1, CANSparkMaxLowLevel.MotorType.kBrushless);
		backRight= new CANSparkMax(2 , CANSparkMaxLowLevel.MotorType.kBrushless);
		leftDrive = new SpeedControllerGroup(frontLeft, backLeft);
		rightDrive = new SpeedControllerGroup(frontRight, backRight);
		
		/*frontLeft.setIdleMode(CANSparkMax.IdleMode.kBrake);
		frontRight.setIdleMode(CANSparkMax.IdleMode.kBrake);
		backLeft.setIdleMode(CANSparkMax.IdleMode.kBrake);
		backRight.setIdleMode(IdleMode.kCoast.kBrake);
		*/
		drive = new DifferentialDrive(leftDrive, rightDrive);
		drive.setExpiration(Robot.kDefaultPeriod);
		table = NetworkTableInstance.getDefault().getTable("limelight");
		tx = table.getEntry("tx");
	}
	
	public void drive(double speed, double turnRate){
		drive.curvatureDrive(speed, turnRate, false);
	}

	public void setLeftRightMotorOutputs(double left, double right) {
		SmartDashboard.putNumber("LEFT OUTPUT", left);
		SmartDashboard.putNumber("RIGHT Output", right);
		frontLeft.set(left);
		frontRight.set(right);
		backLeft.set(left);
		backRight.set(right);
	}

	public void driveWithTarget(double throttle, double angle) {	
	//SmartDashboard.putNumber("DriveTarget", angle);
		double left = angle * KTURN_DRIVE_P;
		double right = -angle * KTURN_DRIVE_P;
		left+=throttle;
		right+=throttle;
		setLeftRightMotorOutputs(left, -right);
	}

	public double calculateTrajectory() {
		targetX = tx.getDouble(0.0);
		return targetX;
	}

	protected double returnPIDInput() {
		return calculateTrajectory();
	}

	protected void usePIDOutput(double output) {
		double throttle = Robot.oi.getThrottle2();
		double left = throttle;
		double right = throttle;
		
		if(throttle>0.1) {	
			left += output * KTURN_DRIVE;
			right += -output *KTURN_DRIVE; 
		}
		else {
			left += output;
			right += -output;
		}
		setLeftRightMotorOutputs(left, right);
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
