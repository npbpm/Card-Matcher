package Read;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * 
 * ReadAllLine is a class that reads all the lines in a file. It takes in a File
 * object in its constructor. It uses a Scanner object to read the file and sets
 * the delimiter pattern to ",". It prints each line read to the console. The
 * first line that will be read is the line "Name, POI"
 * 
 * @author rabym
 * 
 */
public class ReadAllLine {
	/**
	 * Constructor for the ReadAllLine class.
	 * 
	 * @param file the File object to read
	 */
	public ReadAllLine(File file) {
// create FileWriter object with file as parameter
		try {
			Scanner sc = new Scanner(file);
			sc.useDelimiter(",");
//setting comma as delimiter pattern
			while (sc.hasNext()) {
				System.out.println(sc.next());
			}
//closes the scanner
			sc.close();
		} catch (IOException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}