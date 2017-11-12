package org.usfirst.frc.team3630.robot.helpers;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

//Works as the source for position and velocity for the wheel PID controllers
class EncoderPIDSource implements PIDSource {

		PIDSourceType _PIDSourceType;
		Encoder _e;

		/**
		 * @param e
		 *            encoder
		 * @param pidSourceType
		 *            Whether input is a displacement or a rate
		 */
		public EncoderPIDSource (Encoder e, PIDSourceType pidSourceType) {
			_e = e;
			_PIDSourceType = pidSourceType;
		}


		public void setPIDSourceType(PIDSourceType pidSource) {

			_PIDSourceType = pidSource;

		}


		public PIDSourceType getPIDSourceType() {
			return _PIDSourceType;
		}


		public double pidGet() {
			if (PIDSourceType.kDisplacement.equals(_PIDSourceType)) {
				return _e.getDistance();

			} else if (PIDSourceType.kRate.equals(_PIDSourceType)) {
				return _e.getRate();
			} else {
				return -1;
			}
		}

	}