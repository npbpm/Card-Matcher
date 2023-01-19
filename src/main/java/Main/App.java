package Main;

import java.awt.EventQueue;
import java.io.File;
import java.net.MalformedURLException;

import org.opencv.core.Core;

import CameraVue.Camera;
import Save.CreateCSV;
import UserInstructions.Instructions;

public class App {
	// Main driver method
	private static String userDirectory = System.getProperty("user.dir");
	public static File appCSV;
	
	public static void main(String[] args) {
		Instructions instructionsTab = new Instructions();
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
		if(!apprentissage.exists()) {
			apprentissage.mkdir();
		}


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
		
		
		File testResult = new File(userDirectory + "/TestResults");
		if(testResult.exists()) {
			String[] entries = testResult.list();
			for(String s: entries) {
				File currentFile = new File(testResult.getPath(),s);
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
