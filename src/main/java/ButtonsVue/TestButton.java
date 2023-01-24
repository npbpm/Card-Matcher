package ButtonsVue;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import CameraVue.Camera;

/**
 * 
 * TestButton is a class that creates a button for switching to test mode. It
 * takes in a Camera object and a JPanel object in its constructor. When
 * clicked, it changes the value of the clicked_test boolean in the Camera class
 * to true.
 */
@SuppressWarnings("serial")
public class TestButton extends JButton {
	/**
	 * Camera object
	 */
	private Camera camera;

	/*
	 * Constructor for the TestButton class.
	 * 
	 * @param c the Camera object
	 * 
	 * @param panel the JPanel to add the button to
	 */

	public TestButton(Camera c, JPanel panel) {
		super();
		this.camera = c;
		this.setSize(new Dimension(200, 80));
		this.setPreferredSize(new Dimension(200, 80));
		this.setText("Test");
		panel.add(this);
		this.addActionListener(new ActionListener() {
			/*
			 * When clicked, changes the value of the clicked_test boolean in the Camera
			 * class to true.
			 */
			public void actionPerformed(ActionEvent e) {
				camera.changeClickedTest();
			}
		});
	}
}