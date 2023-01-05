package Read;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.opencsv.CSVWriter;
/**
 * This class's purpose is to read all the lines. For now it doesn't do anything else, 
 * but later it could be used to return a serie of mathing rates.
 * Warning: the first line that will be read will be the line "Name, POI"
 * The date should be written as a list of String. 
 * @author rabym
 *
 */

public class ReadAllLine {
	public ReadAllLine (File file, String[] data) {
		// create FileWriter object with file as parameter
		FileWriter outputfile = null;
		try {
			Scanner sc = new Scanner(file);
			sc.useDelimiter(",");
			//setting comma as delimiter pattern
		    while (sc.hasNext()) {
		      //here we do something with sc.next()
		    }
		    //closes the scanner 
		    sc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
