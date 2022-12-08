package Camera;

// importing swing and awt classes
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import org.opencv.core.Core;

import Image.Descriptor;

public class Result extends JFrame {

	private JLabel result;

	public Result(Descriptor d) {
		// Designing UI
		setLayout(null);

		result = new JLabel();
		result.setBounds(0, 0, 640, 480);
		add(result);
		setSize(new Dimension(640, 560));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		result.setText(d.show());

	}
}
