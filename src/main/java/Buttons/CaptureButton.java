package Buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import Camera.Camera;
public class CaptureButton extends JButton{
	private Camera camera;
	public CaptureButton (Camera c) {
		super();
		this.camera= c;
		this.setBounds(200, 480, 80, 40);
		this.setText("Capture");
		camera.add(this);
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				camera.changeClicked();
			}
		});
	}
}
