package UserInstructions;

import java.awt.Dimension;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
public class Instructions{
	
	private static String userDirectory = System.getProperty("user.dir");
	
	public void Instructions() throws MalformedURLException {
	      createWindow();
	   }

	   public static void createWindow() throws MalformedURLException {    
	      JFrame frame = new JFrame("Swing Tester");
	      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      createUI(frame);
	      frame.setSize(560, 450);      
	      frame.setLocationRelativeTo(null);  
	      frame.setVisible(true);
	   }

	   private static void createUI(final JFrame frame) throws MalformedURLException{  
	      JPanel panel = new JPanel();
	      LayoutManager layout = new FlowLayout();  
	      panel.setLayout(layout);       

	      JEditorPane jEditorPane = new JEditorPane();
	      jEditorPane.setEditable(false);   
	      File HTMLFile = new File(userDirectory + "/instructionsPage.html");
	      URL url = HTMLFile.toURI().toURL();

	      try {   
	         jEditorPane.setPage(url);
	      } catch (IOException e) { 
	         jEditorPane.setContentType("text/html");
	         jEditorPane.setText("<html>Page not found.</html>");
	      }

	      JScrollPane jScrollPane = new JScrollPane(jEditorPane);
	      jScrollPane.setPreferredSize(new Dimension(540,400));      

	      panel.add(jScrollPane);
	      frame.getContentPane().add(panel, BorderLayout.CENTER);    
	   }  
}
