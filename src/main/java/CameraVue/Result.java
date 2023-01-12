package CameraVue;

// importing swing and awt classes
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

public class Result extends JFrame {

	private JLabel result;
	private Mat image;

	public Result(Mat mat) {
		image = mat;

	}

	public void show() {
		setLayout(null);

		// screen
		result = new JLabel();
		result.setBounds(0, 0, 640, 480);
		add(result);
		
		setSize(new Dimension(640, 560));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		final MatOfByte buf = new MatOfByte();
		Imgcodecs.imencode(".jpg", image, buf);
		byte[] imageData;
		imageData = buf.toArray();

		// Add to JLabel
		ImageIcon icon;
		icon = new ImageIcon(imageData);
		result.setIcon(icon);
		
	}
}
