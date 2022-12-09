// Java Program to take a Snapshot from System Camera
// using OpenCV

// https://www.geeksforgeeks.org/taking-a-snapshot-from-system-camera-using-opencv-in-java/
// Author : pawki

// Importing openCV modules
package Camera;

import java.awt.Color;
// importing swing and awt classes
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
// Importing date class of sql package
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
// Importing VideoCapture class
// This class is responsible for taking screenshot
import org.opencv.videoio.VideoCapture;

import Buttons.TestButton;
import Buttons.dbButton;
import Buttons.ResultButton;
import Sift.Sift;
import Buttons.SaveButton;

// Class - Swing Class
public class Camera extends JFrame {

	private Result result;

	private String imName;

	private String mode = "Apprentissage";

	private MatOfKeyPoint kpts1;

	// Camera screen
	private JLabel cameraScreen;
	private JPanel overallPanel = new JPanel();
	private JPanel cameraPanel = new JPanel();
	private JPanel resultPanel = new JPanel();
	private JPanel buttonsPanel = new JPanel();

	// Buttons
	private JButton btnCapture;
	private JButton btnDB;
	private JButton btnFolderChoice;
	private JButton btnResult;

	// Start camera
	private VideoCapture capture;

	// Store image as 2D matrix
	private Mat image;

	// Select the folder to use
	private String folderPath;

	// True si l'utilisateur a cliqué sur le mode test
	public boolean clicked_test = false;
	public boolean clicked_save = false;
	public boolean clicked_bdd = false;
	public boolean clicked_result = false;

	// Path
	private static String userDirectory;
	private String path;

	public void changeClickedTest() {
		clicked_test = !clicked_test;
	}

	public void changeClickedSave() {
		clicked_save = !clicked_save;
	}

	public void changeClickedBDD() {
		clicked_bdd = !clicked_bdd;
	}

	public void changeClickedResult() {
		clicked_result = !clicked_result;
	}

	public void changeFolderPath(String sentence) {
		folderPath = JOptionPane.showInputDialog(sentence);
	}

	public void setImName(String name) {
		imName = name;
	}

	public String getImName() {
		return imName;
	}

	public void setMode() {
		if (getMode().equals("Apprentissage")) {
			mode = "Test";
		} else {
			mode = "Apprentissage";
		}
	}

	public String getMode() {
		return mode;
	}

	public boolean isDirectoryEmpty(File directory) {
		String[] files = directory.list();
		return files.length == 0;
	}

	public Camera(String Directory) {
		userDirectory = System.getProperty("user.dir");
		path = userDirectory + "/Apprentissage/";

		// Colors
		Color learningModeColor = Color.GREEN;
		Color testModeColor = Color.RED;
		Color backgroundColor = Color.decode("#425C81");

		// Designing UI
		setPreferredSize(new Dimension(1920, 1080));
		setSize(new Dimension(1920, 1080));
		setLayout(new GridLayout(1, 1, 0, 0));

		overallPanel.setLayout(new GridLayout(1, 3, 0, 0));
		overallPanel.setBackground(backgroundColor);

		// Buttons panel UI
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
		buttonsPanel.setBackground(backgroundColor);
		buttonsPanel.add(Box.createVerticalGlue());

		// capture button
		btnCapture = new TestButton(this, buttonsPanel);
		btnCapture.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		btnCapture.setBackground(testModeColor);
		buttonsPanel.add(Box.createVerticalGlue());
		// folder button
		btnFolderChoice = new SaveButton(this, buttonsPanel);
		btnFolderChoice.setBackground(learningModeColor);
		btnFolderChoice.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		buttonsPanel.add(Box.createVerticalGlue());
		// folder button
		btnDB = new dbButton(this, buttonsPanel);
		btnDB.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		buttonsPanel.add(Box.createVerticalGlue());
		// result button
		resultPanel.add(Box.createVerticalGlue());
		btnResult = new ResultButton(this, resultPanel);
		btnResult.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		resultPanel.add(Box.createVerticalGlue());
		
		// Results panel UI
		resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
		resultPanel.setBackground(backgroundColor);
		
		overallPanel.add(buttonsPanel);

		// screen
		cameraScreen = new JLabel();
		cameraScreen.setBounds(0, 0, 640, 480);
		overallPanel.add(cameraScreen);

		// Result pane
		overallPanel.add(resultPanel);
		getContentPane().add(overallPanel);

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

			// create rectangle
			Mat src = image; // where the rectangle has to appear
			Mat flip = new Mat();
			Core.flip(image, flip, 1);
			image = flip;
			Size s = src.size();
			Point pt1 = new Point(s.width - 250, s.height - 350); // top-left corner of the rectangle
			Point pt2 = new Point(s.width - 100, s.height - 100); // bottom-right corner of the rectangle
			Scalar color;
			if (getMode().equals("Apprentissage")) {
				color = new Scalar(0, 255, 0); // choice of color (BGR)
			} else {
				color = new Scalar(0, 0, 255); // choice of color (BGR)
			}
			int th = 2; // choice of thickness
			Imgproc.rectangle(image, pt1, pt2, color, th); // creation

			// convert matrix to byte
			final MatOfByte buf = new MatOfByte();
			Imgcodecs.imencode(".jpg", image, buf);

			imageData = buf.toArray();

			// Add to JLabel
			icon = new ImageIcon(imageData);
			cameraScreen.setIcon(icon);
			// Capture and save to file
			if (clicked_test) {
				if (getMode().equals("Apprentissage")) {
					setMode();
				}
				File BDD = new File(path);
				if (isDirectoryEmpty(BDD)) { // We see if the Apprentissage folder is not empty before doing a test
					System.out.println("The folder is empty");
				} else {
					// Write to file + crop

					Rect rectCrop = new Rect(pt1, pt2);
					Mat image_crop = new Mat(image, rectCrop);
					Mat bw = new Mat();
					Imgproc.cvtColor(image_crop, bw, Imgproc.COLOR_RGB2GRAY);
					Imgcodecs.imwrite(userDirectory + "/Test/" + getImName() + "_Test.jpg", image_crop);

					// test sift capture test + image bdd
					Sift sif = new Sift();

					// test poker
					Imgcodecs imageCodecs = new Imgcodecs();

					// Boucle pour comparer la carte a toutes les cartes de la BDD

					String[] imagesPath = BDD.list();
					Integer compteur = 0;
					for (String imgPath : imagesPath) {
						//Ici le compteur nous sert uniquement à différencier chaque image, si on le mets pas, les images sont écrassés au fur et à mesure
						//et donc on n'as que le dernier résultat.
						compteur++;
						File currentImg = new File(BDD.getPath(), imgPath);
						Mat img = imageCodecs.imread(currentImg.getPath());

						Mat outImg = sif.compareCards(bw, img);
						// On store l'image montrant la comparaison dans le dossier test
						Imgcodecs.imwrite(userDirectory + "/TestResults/" + getImName() + "_Test_Result" + compteur.toString() + ".jpg", outImg);
					}
				}

				clicked_test = false;
			}
			// result = new Result(image);

			// A sauvegarder dans des dossiers séparés après

			if (clicked_save) {
				if (getMode().equals("Test")) {
					setMode();
				}
				// prompt for enter image name
				String name = JOptionPane.showInputDialog(this, "Enter image name");
				setImName(name);
				if (!(name == null)) {
					if (name.equals("")) {
						name = new SimpleDateFormat("yyyy-mm-dd-hh-mm-ss").format(new Date(HEIGHT, WIDTH, getX()));
					}

					// Write to file + crop

					Rect rectCrop = new Rect(pt1, pt2);
					Mat image_crop = new Mat(image, rectCrop);

					Imgcodecs.imwrite(path + name + ".jpg", image_crop);
				}

				clicked_save = false;

			}

			if (clicked_bdd) {

				JFileChooser dialogue = new JFileChooser();

				dialogue.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

				dialogue.showOpenDialog(null);

				if (dialogue.getSelectedFile() != null) {
					System.out.println("Dossier choisi : " + dialogue.getSelectedFile());

					path = dialogue.getSelectedFile().toString() + "/";
				}
				clicked_bdd = false;
			}
			// if (clicked_result) {
			// result.show();
			// clicked_result = false;
			// }
		}

	}
}
