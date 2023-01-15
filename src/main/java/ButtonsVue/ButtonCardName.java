package ButtonsVue;
//package opencvcamera;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Color;
import net.miginfocom.swing.MigLayout;


public class ButtonCardName extends JFrame {

	private JPanel contentPane;
	private JTextField textField_1;
	private String name;
	private JTextArea txtrEnregistrezLeNom;
	private JTextArea txtrChoisissez;
	private JTextArea txtrcrivez;
	private JTextArea txtrOu;
	private final String userDirectory;

	/**
	 * Create the frame.
	 */
	public ButtonCardName(final Mat image, final String userDirectory) {
		this.userDirectory = userDirectory;
		
		setBackground(Color.DARK_GRAY);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 112);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		

		String[] itemsNombre = { "As de coeur", "2 de coeur", "3 de coeur", "4 de coeur", "5 de coeur", "6 de coeur",
				"7 de coeur", "8 de coeur", "9 de coeur", "10 de coeur", "Valet de coeur", "Dame de coeur",
				"Roi de coeur", "As de trèfle", "2 de trèfle", "3 de trèfle", "4 de trèfle", "5 de trèfle",
				"6 de trèfle", "7 de trèfle", "8 de trèfle", "9 de trèfle", "10 de trèfle", "Valet de trèfle",
				"Dame de trèfle", "Roi de trèfle", "As de pique", "2 de pique", "3 de pique", "4 de pique",
				"5 de pique", "6 de pique", "7 de pique", "8 de pique", "9 de pique", "10 de pique", "Valet de pique",
				"Dame de pique", "Roi de pique", "As de carreau", "2 de carreau", "3 de carreau", "4 de carreau",
				"5 de carreau", "6 de carreau", "7 de carreau", "8 de carreau", "9 de carreau", "10 de carreau",
				"Valet de carreau", "Dame de carreau", "Roi de carreau" };
		contentPane.setLayout(new MigLayout("", "[143px][5px][96px][5px][4px][5px][5px][5px][104px][21px]", "[22px][22px]"));

		txtrEnregistrezLeNom = new JTextArea();
		txtrEnregistrezLeNom.setEditable(false);
		txtrEnregistrezLeNom.setBackground(new Color(240, 240, 240));
		txtrEnregistrezLeNom.setText("Sélectionnez le nom de la carte");
		contentPane.add(txtrEnregistrezLeNom, "cell 0 0 5 1,alignx left,aligny top");

		txtrChoisissez = new JTextArea();
		txtrChoisissez.setBackground(Color.BLACK);
		txtrChoisissez.setEditable(false);
		contentPane.add(txtrChoisissez, "cell 6 0,alignx left,aligny top");

		final JComboBox ListeNombre = new JComboBox(itemsNombre);

		contentPane.add(ListeNombre, "cell 8 0,alignx left,aligny center");
		
				txtrOu = new JTextArea();
				txtrOu.setBackground(new Color(240, 240, 240));
				txtrOu.setEditable(false);
				txtrOu.setText("OU");
				contentPane.add(txtrOu, "flowx,cell 0 1,alignx left,aligny top");

		txtrcrivez = new JTextArea();
		txtrcrivez.setBackground(new Color(240, 240, 240));
		txtrcrivez.setEditable(false);
		txtrcrivez.setText("manuellement ");
		contentPane.add(txtrcrivez, "cell 0 1,alignx right,aligny top");

		textField_1 = new JTextField();
		contentPane.add(textField_1, "cell 2 1,alignx left,aligny center");
		textField_1.setColumns(10);
		
		ListeNombre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_1.setText("");
			}
		});
//		Mat src = image;
//		Size s = src.size();
//		Point pt1 = new Point(s.width - 350, s.height - 350); // top-left corner of the rectangle
//		Point pt2 = new Point(s.width - 200, s.height - 100); // bottom-right corner of the rectangle
		JButton btnNewButton = new JButton("Enregistrez");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				name = "" + textField_1.getText();
				if (name == "") {
					name = ListeNombre.getSelectedItem().toString();
				}
				Mat src = image;
				Size s = src.size();
				Point pt1 = new Point(s.width - 350, s.height - 350); // top-left corner of the rectangle
				Point pt2 = new Point(s.width - 200, s.height - 100); // bottom-right corner of the rectangle
				Rect rectCrop = new Rect(pt1, pt2);
				Mat image_crop = new Mat(image, rectCrop);	
				// write to file
				Imgcodecs.imwrite(userDirectory + "/Apprentissage/" + name + ".jpg", image_crop);
				dispose();
			}
		});
		contentPane.add(btnNewButton, "cell 4 1 5 1,alignx left,aligny top");
	}	
}
