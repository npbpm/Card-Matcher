package Image;

import org.opencv.core.KeyPoint;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Size;

import Camera.Result;

public class Descriptor {
	private KeyPoint[] keyPoints;
	private Size size;

	public Descriptor(MatOfKeyPoint kpts1) {
		keyPoints = kpts1.toArray();
		size = kpts1.size();
	}

	public KeyPoint get_keypoint(int i) {
		return keyPoints[i];
	}

	public String show() {
		String text;
		text = "Found n. keypoints: " + size;
		for (int i = 0; i < keyPoints.length; i++) {
			KeyPoint kp = keyPoints[i];

			text += " " + i + ": pt " + kp.pt + ", size = " + kp.size + ", angle = " + kp.angle + ", octave = "
					+ kp.octave;
		}
		return text;
	}

}
