import edu.wpi.first.wpilibj.GenericHID;
inport edu.wpi.first.wpilibj.XBoxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class XBoxHelper extends XBoxContoller {
	
	
	
	public double getRoundX() {
		double result = m_Joystick.getX(GenericHID.Hand.kLeft);
		SmartDashboard.putNumber("Joystick X", result);
		return result;
	}

	public double getRoundY() {
		double result = m_Joystick.getY(GenericHID.Hand.kLeft);
		SmartDashboard.putNumber("Joystick Y", result);

		return result;
	}

	public double getRoundTwist() {
		double result = m_Joystick.getX(GenericHID.Hand.kRight);
		SmartDashboard.putNumber("Joystick Twist", result);

		return result;
	}
}
