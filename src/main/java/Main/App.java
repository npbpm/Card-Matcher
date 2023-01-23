package Main;

import java.awt.EventQueue;
import java.io.File;
import java.net.MalformedURLException;

import org.opencv.core.Core;

import CameraVue.Camera;
import Save.CreateCSV;
import UserInstructions.Instructions;

/**
 *
 * 
 * The App class is the main driver class for a project that uses OpenCV's SIFT
 * 
 * algorithm to detect cards. It loads the OpenCV native library, creates
 * 
 * necessary directories, and starts the camera thread. It also creates a CSV
 * 
 * file to store card descriptors, and creates a window to display instructions
 * 
 * to the user.
 */
public class App {
	/*
	 * 
	 * userDirectory is a private static variable that stores the current working
	 * directory of the user.
	 */
	private static String userDirectory = System.getProperty("user.dir");
	/*
	 * appCSV is a public static variable that stores the reference to the CSV file
	 * that stores the card descriptors.
	 */
	public static File appCSV;

	/**
	 * 
	 * The main method is the entry point of the program. It creates a new
	 * Instructions object, creates a window to display instructions to the user,
	 * creates a new CSV file to store card descriptors, loads the OpenCV native
	 * library, creates necessary directories and starts the camera thread.
	 * 
	 * @param args command line arguments
	 * 
	 * @throws MalformedURLException if the instruction window URL is malformed
	 */
	public static void main(String[] args) throws MalformedURLException {
		new Instructions();
		try {
			Instructions.createWindow();
		} catch (MalformedURLException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
// creation of the csv to put card descriptors
		CreateCSV CSV = new CreateCSV("ApprentissageCSV");
		appCSV = CSV.getRefToFile();
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

// Creation of the Apprentissage and Test Folders
		File apprentissage = new File(userDirectory + "/Apprentissage");
		if (!apprentissage.exists()) {
			apprentissage.mkdir();
		}

		File test = new File(userDirectory + "/Test");
		if (test.exists()) {
			String[] entries = test.list();
			for (String s : entries) {
				File currentFile = new File(test.getPath(), s);
				currentFile.delete();
			}
// test.delete();
		}
		test.mkdir();

		File testResult = new File(userDirectory + "/TestResults");
		if (testResult.exists()) {
			String[] entries = testResult.list();
			for (String s : entries) {
				File currentFile = new File(testResult.getPath(), s);
				currentFile.delete();
			}
		}
		testResult.mkdir();

		EventQueue.invokeLater(new Runnable() {
// Overriding existing run() method
			public void run() {
				final Camera camera = new Camera(userDirectory);
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
