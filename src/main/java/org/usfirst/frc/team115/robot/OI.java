package org.usfirst.frc.team115.robot;

import org.usfirst.frc.team115.robot.commands.VisionTurnCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	Joystick joystick;
	JoystickButton visionTurn;
	public OI(){
		joystick = new Joystick(0);
		visionTurn = new JoystickButton(joystick, 5);

		visionTurn.whenPressed(new VisionTurnCommand());
	}

	public boolean getVisionButton() {
		return visionTurn.get();
	}
	
	public double getThrottle() {
		return joystick.getRawAxis(5);
	}
	
	public double getWheel() {
		return joystick.getRawAxis(0);
	}


	public double getThrottle2() {
		return joystick.getRawAxis(3);
	}
}
