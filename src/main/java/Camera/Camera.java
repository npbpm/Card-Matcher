// Java Program to take a Snapshot from System Camera
// using OpenCV

// https://www.geeksforgeeks.org/taking-a-snapshot-from-system-camera-using-opencv-in-java/
// Author : pawki

// Importing openCV modules
package Camera;

import java.awt.Color;
// importing swing and awt classes
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
// Importing date class of sql package
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
// Importing VideoCapture class
// This class is responsible for taking screenshot
import org.opencv.videoio.VideoCapture;

import Buttons.CaptureButton;
import Buttons.FolderButton;

// Class - Swing Class
public class Camera extends JFrame {

	// Camera screen
	private JLabel cameraScreen;

	// Button for image capture
	private JButton btnCapture;

	private JButton rectangle;

	// Start camera
	private VideoCapture capture;

	// Store image as 2D matrix
	private Mat image;

	// Select the folder to use
	private JButton btnFolderChoice;
	private String folderPath;

	private boolean clicked = false;

	private static String userDirectory = System.getProperty("user.dir");
	public void changeClicked() {
		clicked= clicked;
	}
	public void changeFolderPath(String sentence) {
		folderPath = JOptionPane.showInputDialog(sentence);
	}
	public Camera() {

		// Designing UI
		setLayout(null);

		// screen
		cameraScreen = new JLabel();
		cameraScreen.setBounds(0, 0, 640, 480);
		add(cameraScreen);

		// capture button
		btnCapture = new CaptureButton(this);

		// folder button
		btnFolderChoice = new FolderButton (this);

		setSize(new Dimension(640, 560));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}

	// Creating a camera
	@SuppressWarnings("deprecation")
	public void startCamera() {
		capture = new VideoCapture(0);
		image = new Mat();
		byte[] imageData;

		ImageIcon icon;
		while (true) {
			// read image to matrix
			capture.read(image);

			// create red rectangle 
			Mat src= image;   //where the rectangle has to appear
			Size s=src.size();
			Point pt1 = new Point(s.width-200,s.height-300);    // top-left corner of the rectangle
			Point pt2 = new Point(s.width,s.height);            // bottom-right corner of the rectangle
			Scalar color = new Scalar(0,0,255);                 // choice of color (RGB)
			int th = 5;                                         // choice of thickness
			Imgproc.rectangle(src, pt1, pt2,color,th);          // creation

			// convert matrix to byte
			final MatOfByte buf = new MatOfByte();
			Imgcodecs.imencode(".jpg", image, buf);

			imageData = buf.toArray();

			// Add to JLabel
			icon = new ImageIcon(imageData);
			cameraScreen.setIcon(icon);

			// Capture and save to file
			if (clicked) {
				// prompt for enter image name
				String name = JOptionPane.showInputDialog(this, "Enter image name");
				if (name == null) {
					name = new SimpleDateFormat("yyyy-mm-dd-hh-mm-ss").format(new Date(HEIGHT, WIDTH, getX()));
				}

				// Write to file
				if (folderPath==null) {
					Imgcodecs.imwrite("images/" + name + ".jpg", image);
				}
				else {
					Imgcodecs.imwrite(folderPath + name + ".jpg", image);
				}
			}
			clicked = false;
		}
	}


	// Main driver method
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		/*
		 * Creation of the Apprentissage and Test Folders
		 * */
		File apprentissage = new File(userDirectory + "/Apprentissage");
		apprentissage.mkdirs();

		File test = new File(userDirectory + "/Test");
		test.mkdirs();

		EventQueue.invokeLater(new Runnable() {
			// Overriding existing run() method
			public void run() {
				final Camera camera = new Camera();

				// Start camera in thread
				new Thread(new Runnable() {
					public void run() {
						camera.startCamera();
					}
				}).start();
			}
		});
	}
}
