package Buttons;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import Camera.Camera;

public class ResultButton extends JButton {
	private Camera camera;

	public ResultButton(Camera c, JPanel panel) {
		super();
		this.camera = c;
		this.setSize(new Dimension(200,50));
		this.setPreferredSize(new Dimension(200,50));
		this.setText("Result");
		panel.add(this);
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				camera.changeClickedResult();
			}
		});
	}
}
