package Save;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVWriter;
/**
 * This class's purpose is to save the data into the CSV specified
 * in argument. 
 * The date should be written as a list of String. 
 * @author rabym
 *
 */

public class Save {
	public Save (File file, String[] data) {
		// create FileWriter object with file as parameter
		FileWriter outputfile = null;
		try {
			outputfile = new FileWriter(file);
			// create CSVWriter object filewriter object as parameter
			CSVWriter writer = new CSVWriter(outputfile);
			// add data to csv
			writer.writeNext(data);

			// closing writer connection
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
