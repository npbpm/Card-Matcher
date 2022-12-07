package Buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import Camera.Camera;

public class SaveButton extends JButton {
	private Camera camera;
	public SaveButton (Camera c) {
		super();
		this.camera=c;
		this.setBounds(310, 480, 80, 40);
		this.setText("Save");
		camera.add(this);
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				camera.changeClickedSave();;
			}
		});
	}
}
