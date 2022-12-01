package Buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import Camera.Camera;

public class FolderButton extends JButton {
	private Camera camera;
	public FolderButton (Camera c) {
		super();
		this.camera=c;
		this.setBounds(400, 480, 80, 40);
		this.setText("Folder");
		camera.add(this);
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				camera.changeFolderPath("Select the path to the folder you want to use:");
			}
		});
	}
}
