import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

class VelocityAdjuster implements PIDOutput, PIDSource {
		double vAdjust;
		double vFeed;

		public void pidWrite(double output) {
			vAdjust = output;
		}

		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {
			pidSource = PIDSourceType.kRate;
		}

		@Override
		public PIDSourceType getPIDSourceType() {
			// TODO Auto-generated method stub
			return PIDSourceType.kRate;
		}

		public void setVDesired(double vAdjust) {
			vFeed = vAdjust;
		}

		public double pidGet() {
			// need to fix
			return vFeed + vAdjust;
		}
	}

