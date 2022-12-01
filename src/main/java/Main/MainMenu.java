package Main;

import java.awt.EventQueue;
import java.io.File;

import org.opencv.core.Core;

import Camera.Camera;

public class MainMenu {
	// Main driver method
	private static String userDirectory = System.getProperty("user.dir");
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
