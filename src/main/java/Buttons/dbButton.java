package Buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import Camera.Camera;
public class dbButton extends JButton{
	private Camera camera;
	public dbButton (Camera c) {
		super();
		this.camera= c;
		this.setBounds(0, 480, 170, 40);
		this.setText("Select Database");
		camera.add(this);
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				camera.changeClickedBDD();
			}
		});
	}
}