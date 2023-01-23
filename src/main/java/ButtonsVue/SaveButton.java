package ButtonsVue;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import CameraVue.Camera;

/**
 * 
 * SaveButton is a class that creates a button for saving images. It takes in a
 * Camera object and a JPanel object in its constructor. When clicked, it
 * changes the value of the clicked_save boolean in the Camera class to true.
 * 
 */
public class SaveButton extends JButton {
	private Camera camera;

	/**
	 * Constructor for the SaveButton class.
	 * 
	 * @param c     the Camera object
	 * @param panel the JPanel to add the button to
	 */
	public SaveButton(Camera c, JPanel panel) {
		super();
		this.camera = c;
		this.setSize(new Dimension(200, 80));
		this.setPreferredSize(new Dimension(200, 80));
		this.setText("Apprentissage");
		panel.add(this);
		this.addActionListener(new ActionListener() {
			/*
			 * When clicked, changes the value of the clicked_save boolean in the Camera
			 * class to true.
			 */
			public void actionPerformed(ActionEvent e) {
				camera.changeClickedSave();
			}
		});
	}
}