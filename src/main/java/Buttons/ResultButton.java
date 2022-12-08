package Buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import Camera.Camera;

public class ResultButton extends JButton {
	private Camera camera;

	public ResultButton(Camera c) {
		super();
		this.camera = c;
		this.setBounds(540, 480, 80, 40);
		this.setText("Result");
		camera.add(this);
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				camera.changeClickedResult();
			}
		});
	}
}
