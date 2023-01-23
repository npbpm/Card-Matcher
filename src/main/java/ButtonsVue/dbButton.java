package ButtonsVue;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import CameraVue.Camera;

/**
 * 
 * dbButton is a class that creates a button for accessing the database. It
 * takes in a Camera object and a JPanel object in its constructor. When
 * clicked, it changes the value of the clicked_bdd boolean in the Camera class
 * to true.
 */
public class dbButton extends JButton {
	/**
	 * Camera object
	 */
	private Camera camera;

	/*
	 * Constructor for the dbButton class.
	 * 
	 * @param c the Camera object
	 * 
	 * @param panel the JPanel to add the button to
	 */
	public dbButton(Camera c, JPanel panel) {
		super();
		this.camera = c;
		this.setSize(new Dimension(200, 80));
		this.setPreferredSize(new Dimension(200, 80));
		this.setText("Select Database");
		panel.add(this);
		/*
		 * When clicked, changes the value of the clicked_bdd boolean in the Camera
		 * class to true.
		 */
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				camera.changeClickedBDD();
			}
		});
	}
}