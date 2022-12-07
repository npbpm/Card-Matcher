package Buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import Camera.Camera;
public class TestButton extends JButton{
	private Camera camera;
	public TestButton (Camera c) {
		super();
		this.camera= c;
		this.setBounds(210, 480, 80, 40);
		this.setText("Test");
		camera.add(this);
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				camera.changeClickedTest();
			}
		});
	}
}
