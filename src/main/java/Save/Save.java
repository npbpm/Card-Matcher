package Save;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVWriter;

/**
 * 
 * This class's purpose is to save the data into the CSV specified in the
 * constructor. The data should be passed as a String array.
 * 
 * @author rabym
 */
public class Save {
	/*
	 * The constructor for the class.
	 * 
	 * @param file the File object to save the data to
	 * 
	 * @param data the data to save as a String array
	 */
	public Save(File file, String[] data) {
// create FileWriter object with file as parameter
		FileWriter outputfile = null;
		try {
			outputfile = new FileWriter(file, true);// the second argument indicates that we want to append another line
													// and not overwrite
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
