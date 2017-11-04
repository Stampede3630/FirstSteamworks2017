package org.usfirst.frc.team3630.robot.helpers;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

class PositionToVelocityPIDConverter implements PIDOutput, PIDSource {
		double vAdjust;
		double vFeed;

		public void pidWrite(double output) {
			vAdjust = output;
		}

		public void setPIDSourceType(PIDSourceType pidSource) {
			pidSource = PIDSourceType.kRate;
		}

		public PIDSourceType getPIDSourceType() {
			// TODO Auto-generated method stub
			return PIDSourceType.kRate;
		}
		public void setVDesired(double vF) {
			vFeed = vF;
		}

		public double pidGet() {
			// need to fix
			return vFeed + vAdjust;
		}
	}

