package UserInstructions;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * 
 * This class is used to display instructions to the user in a JFrame window.
 * 
 * The instructions are stored in an HTML file called "instructionsPage.html"
 * located in the project's root directory.
 * 
 * The JEditorPane is set to the URL of this file and displayed in a
 * JScrollPane.
 * 
 */
public class Instructions {
	/*
	 * 
	 * System property that holds the current working directory of the application.
	 */
	private static String userDirectory = System.getProperty("user.dir");

	/**
	 * 
	 * Default constructor that creates the JFrame window and calls the createUI
	 * method.
	 * 
	 * @throws MalformedURLException if the URL to the HTML file is invalid.
	 */
	public Instructions() throws MalformedURLException {
		createWindow();
	}

	/**
	 * 
	 * Creates the JFrame window and sets its properties.
	 * @throws MalformedURLException 
	 */
	public static void createWindow() throws MalformedURLException {
		JFrame frame = new JFrame("Instructions");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createUI(frame);
		frame.setSize(560, 450);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/**
	 * 
	 * Creates the UI elements and adds them to the JFrame.
	 * 
	 * @param frame the JFrame to add the UI elements to.
	 * 
	 * @throws MalformedURLException if the URL to the HTML file is invalid.
	 */
	private static void createUI(final JFrame frame) throws MalformedURLException {
		JPanel panel = new JPanel();
		LayoutManager layout = new FlowLayout();
		panel.setLayout(layout);

		JEditorPane jEditorPane = new JEditorPane();
		jEditorPane.setEditable(false);
		File HTMLFile = new File(userDirectory + "/instructionsPage.html");
		URL url = HTMLFile.toURI().toURL();

		try {
			jEditorPane.setPage(url);
		} catch (IOException e) {
			jEditorPane.setContentType("text/html");
			jEditorPane.setText("<html>Instructions page not found.</html>");
		}

		JScrollPane jScrollPane = new JScrollPane(jEditorPane);
		jScrollPane.setPreferredSize(new Dimension(540, 400));

		panel.add(jScrollPane);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
	}
}