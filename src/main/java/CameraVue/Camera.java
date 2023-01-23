// Java Program to take a Snapshot from System Camera
// using OpenCV

// https://www.geeksforgeeks.org/taking-a-snapshot-from-system-camera-using-opencv-in-java/
// Author : pawki

// Importing openCV modules
package CameraVue;

import java.awt.Color;
// importing swing and awt classes
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
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
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
// Importing VideoCapture class
// This class is responsible for taking screenshot
import org.opencv.videoio.VideoCapture;

import ButtonsVue.TestButton;
import ButtonsVue.dbButton;
import ButtonsVue.ButtonCardName;
import SiftUtils.Sift;
import ButtonsVue.SaveButton;

/**
 * 
 * Camera is a class that extends JFrame and captures images from a camera and
 * displays them in a JLabel.
 * 
 * It also includes buttons for switching modes, saving images, choosing a
 * directory, and accessing the database.
 * 
 */
public class Camera extends JFrame {

	private BufferedImage result;

	private String imName;

	private String mode = "Apprentissage";

	/**
	 * 
	 * Color fields for different UI elements
	 */
	Color learningModeColor = Color.GREEN;
	Color testModeColor = Color.RED;
	Color backgroundColor = Color.decode("#E0E0E0");
	Color buttonsPanelBackgroundColor = Color.decode("#C1C9CC");
	/**
	 * 
	 * Image field for background
	 */
	Image bckImg = Toolkit.getDefaultToolkit().getImage(userDirectory + "/bckgrdImg.jpg");
	/**
	 * 
	 * JLabel and JPanel fields for the camera screen, overall panel, camera panel,
	 * result panel, and buttons panel
	 */
	private JLabel cameraScreen;
	private JPanel overallPanel = new JPanel() {
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(bckImg, 0, 0, null);
		}
	};
	private JPanel cameraPanel = new JPanel();
	private JPanel resultPanel = new JPanel();
	private JPanel buttonsPanel = new JPanel();
	/**
	 * 
	 * JButton fields for capture, database, and folder choice
	 */
	private JButton btnCapture;
	private JButton btnDB;
	private JButton btnFolderChoice;
	/**
	 * 
	 * VideoCapture field for capturing images from the camera
	 */
	private VideoCapture capture;
	/**
	 * 
	 * Mat field for storing image as a 2D matrix
	 */
	private Mat image;
	/**
	 * 
	 * boolean fields for keeping track of user clicks
	 */
	public boolean clicked_test = false;
	public boolean clicked_save = false;
	public boolean clicked_bdd = false;
	public boolean clicked_result = false;
	/**
	 * 
	 * String field for the user's directory and path
	 */
	private static String userDirectory = System.getProperty("user.dir");
	private String path;

	/**
	 * 
	 * Constructor for Camera class.
	 * 
	 * @param Directory the directory for the images to be saved
	 */
	public Camera(String Directory) {
		path = userDirectory + "/Apprentissage/";

		stylePanel();
	}

	/**
	 * 
	 * The startCamera() method creates a new VideoCapture object, a new Matrix
	 * object,
	 * 
	 * and starts capturing images from the camera and displaying them in a JLabel.
	 * 
	 * It also creates and displays buttons for switching modes, saving images,
	 * choosing a directory, and accessing the database.
	 */
	@SuppressWarnings("deprecation")
	public void startCamera() {

		capture = new VideoCapture(0);
		image = new Mat();
		byte[] imageData;

		updatePanel();

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
			Point pt1 = new Point(s.width - 350, s.height - 350); // top-left corner of the rectangle
			Point pt2 = new Point(s.width - 200, s.height - 100); // bottom-right corner of the rectangle
			Scalar color;
			if (getMode().equals("Apprentissage")) {
				color = new Scalar(learningModeColor.getBlue(), learningModeColor.getGreen(),
						learningModeColor.getRed()); // choice of color (BGR)
			} else {
				color = new Scalar(testModeColor.getBlue(), testModeColor.getGreen(), testModeColor.getRed()); // choice
																												// of
																												// color
																												// (BGR)
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

					// test poker
					Imgcodecs imageCodecs = new Imgcodecs();

					// Boucle pour comparer la carte a toutes les cartes de la BDD

					String[] imagesPath = BDD.list();
					Integer compteur = 0;
					double max_rate = 0;
					Mat im_proche = new Mat();
					String bestMatch = "";
					String resultName = new String();
					for (String imgPath : imagesPath) {
						Sift sif = new Sift();
						// Ici le compteur nous sert uniquement à différencier chaque image, si on le
						// mets pas, les images sont écrasées au fur et à mesure
						// et donc on n'as que le dernier résultat.
						compteur++;
						File currentImg = new File(BDD.getPath(), imgPath);
						Mat img = imageCodecs.imread(currentImg.getPath());

						Mat outImg = sif.run(bw, img);
						// On store l'image montrant la comparaison dans le dossier test
						Imgcodecs.imwrite(userDirectory + "/TestResults/" + getImName() + "_Test_Result"
								+ compteur.toString() + ".jpg", outImg);

						if (sif.rate > max_rate) {
							max_rate = sif.rate;
							im_proche = img;
							resultName = currentImg.getPath();
							bestMatch = userDirectory + "/TestResults/" + getImName() + "_Test_Result"
									+ compteur.toString() + ".jpg";
						}

					}

					JLabel label;
					// max_rate = 60;
					// if (max_rate >= 50) {
					// result = new Result(im_proche);
					File resultFile = new File(resultName);
					try {
						result = ImageIO.read(resultFile);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (result.getHeight() > 300) {
						result = resize(result, 200, 300);
					}

					System.out.println("Carte choisie: " + bestMatch);
					System.out.println("Taux de correspondance: " + max_rate);

					File file = new File(bestMatch);
					try {
						ImageIO.read(file);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ImageIcon imageIcon = new ImageIcon(result);
					label = new JLabel(imageIcon);
					// } else {
					// label = new JLabel("Not Found!");
					// label.setPreferredSize(new Dimension(200, 200));
					// label.setFont(new Font("Arial", Font.PLAIN, 50));
					// label.setForeground(Color.WHITE);
					// }
					// RESULTS
					resultPanel.removeAll();
					resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));

					JLabel resultsLabel = new JLabel("Results");
					resultsLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
					resultsLabel.setFont(new Font("Arial", Font.PLAIN, 70));
					resultsLabel.setForeground(Color.WHITE);
					resultPanel.add(resultsLabel);

					resultPanel.add(Box.createVerticalGlue());
					resultPanel.add(label);
					JLabel cardName = new JLabel(resultFile.getName());
					cardName.setAlignmentX(JComponent.CENTER_ALIGNMENT);
					cardName.setFont(new Font("Arial", Font.PLAIN, 25));
					cardName.setForeground(Color.WHITE);
					resultPanel.add(cardName);
					resultPanel.add(Box.createVerticalGlue());

					JLabel resultRate = new JLabel("Taux de correspondance: " + max_rate + "%");
					resultRate.setAlignmentX(JComponent.CENTER_ALIGNMENT);
					resultRate.setFont(new Font("Arial", Font.PLAIN, 25));
					resultRate.setForeground(Color.WHITE);
					resultPanel.add(resultRate);

					overallPanel.add(resultPanel);

				}

				clicked_test = false;

				updatePanel();
			}
			// result = new Result(image);

			// A sauvegarder dans des dossiers séparés après

			if (clicked_save) {
				if (getMode().equals("Test")) {
					setMode();
				}
				// prompt for enter image name
//				String name = JOptionPane.showInputDialog(this, "Enter image name");
//				setImName(name);
//				if (!(name == null)) {
//					if (name.equals("")) {
//						name = new SimpleDateFormat("yyyy-mm-dd-hh-mm-ss").format(new Date(HEIGHT, WIDTH, getX()));
//					}
//
//					// Write to file + crop
//
//					Rect rectCrop = new Rect(pt1, pt2);
//					Mat image_crop = new Mat(image, rectCrop);
//
//					File checkExistingFile = new File(userDirectory + "/Apprentissage/" + name + ".jpg");
//					while(checkExistingFile.exists()) {
//						name = JOptionPane.showInputDialog(this, "Name already Exists! Enter new image name");
//						checkExistingFile = new File(userDirectory + "/Apprentissage/" + name + ".jpg");
//					}
//
//					Imgcodecs.imwrite(path + name + ".jpg", image_crop);
//
//
//				}
				if (clicked_save) {
					ButtonCardName tempframe = new ButtonCardName(image, userDirectory);
					tempframe.setVisible(true);

					clicked_save = false;

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
		}

	}

	public void changeClickedTest() {
		clicked_test = !clicked_test;
	}

	public void changeClickedSave() {
		clicked_save = !clicked_save;
	}

	public void changeClickedBDD() {
		clicked_bdd = !clicked_bdd;
	}

	public void changeFolderPath(String sentence) {
		JOptionPane.showInputDialog(sentence);
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

	public static BufferedImage resize(BufferedImage img, int newW, int newH) {
		Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();

		return dimg;
	}

	/**
	 * 
	 * The stylePanel() method is used to set the layout, background color, and
	 * other visual properties of the JPanels used in the frame.
	 */
	private void stylePanel() {
		// STYLING

		// Designing UI
		setPreferredSize(new Dimension(1920, 1080));
		setSize(new Dimension(1920, 1080));
		setLayout(new GridLayout(1, 1, 0, 0));

		overallPanel.setLayout(new GridLayout(1, 3, 0, 0));

		// Buttons panel UI
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
		buttonsPanel.setOpaque(false);
		buttonsPanel.setPreferredSize(new Dimension(384, 1080));
		buttonsPanel.setSize(new Dimension(384, 1080));

		JLabel actionsLabel = new JLabel("Actions");
		actionsLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		actionsLabel.setFont(new Font("Arial", Font.PLAIN, 70));
		actionsLabel.setForeground(Color.WHITE);
		buttonsPanel.add(actionsLabel);

		buttonsPanel.add(Box.createVerticalGlue());

		// screen
		cameraScreen = new JLabel();
		cameraScreen.setPreferredSize(new Dimension(1200, 480));
		cameraScreen.setSize(new Dimension(1200, 480));
		cameraScreen.setBounds(0, 0, 640, 480);
		cameraScreen.setAlignmentX(JComponent.CENTER_ALIGNMENT);

		cameraPanel.setLayout(new BoxLayout(cameraPanel, BoxLayout.Y_AXIS));
		cameraPanel.setOpaque(false);
		cameraPanel.add(Box.createVerticalGlue());
		cameraPanel.add(cameraScreen);

		// capture button
		btnCapture = new TestButton(this, cameraPanel);
		btnCapture.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		btnCapture.setBackground(testModeColor);
		btnCapture.setPreferredSize(new Dimension(500, 80));
		btnCapture.setFont(new Font("Arial", Font.PLAIN, 40));
		cameraPanel.add(Box.createVerticalGlue());
		// folder button
		btnFolderChoice = new SaveButton(this, buttonsPanel);
		btnFolderChoice.setBackground(learningModeColor);
		btnFolderChoice.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		btnFolderChoice.setFont(new Font("Arial", Font.PLAIN, 40));
		buttonsPanel.add(Box.createVerticalGlue());
		// folder button
		btnDB = new dbButton(this, buttonsPanel);
		btnDB.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		btnDB.setFont(new Font("Arial", Font.PLAIN, 40));
		buttonsPanel.add(Box.createVerticalGlue());

		// Results panel UI
		resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
		resultPanel.setPreferredSize(new Dimension(576, 1080));
		resultPanel.setOpaque(false);

		overallPanel.add(buttonsPanel);

		overallPanel.add(cameraPanel);

		// Result panel
		getContentPane().add(overallPanel);

		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	/**
	 * 
	 * The updatePanel() method is used to update the frame with the latest captured
	 * image. It converts the Matrix object to a BufferedImage and updates the
	 * JLabel with this image.
	 */
	private void updatePanel() {
		revalidate();
		repaint();
	}
}
