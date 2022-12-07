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
		if(apprentissage.exists()) {
			String[] entries = apprentissage.list();
			for(String s: entries) {
				File currentFile = new File(apprentissage.getPath(),s);
				currentFile.delete();
			}
			//			apprentissage.delete();
		}
		apprentissage.mkdir();



		File test = new File(userDirectory + "/Test");
		if(test.exists()) {
			String[] entries = test.list();
			for(String s: entries) {
				File currentFile = new File(test.getPath(),s);
				currentFile.delete();
			}
			//			test.delete();
		}
		test.mkdir();


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
