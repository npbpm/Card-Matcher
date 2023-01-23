package Save;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVWriter;

/**
 * 
 * CreateCSV is a class that creates a CSV file and writes its header. It takes
 * in a string representing the filename in its constructor. It should only be
 * called once in the script. (Or you can create more DataBase if you wish) It
 * creates a new file with the given filename, if the file already exists, it
 * will print "File already exists" to the console. The path to the file can be
 * accessed through the "pathToFile" variable.
 * 
 * @author rabym
 * 
 */
public class CreateCSV {
	/**
	 * String representing the path to the file created
	 */
	public String pathToFile;
	/*
	 * File object representing the file created
	 */
	private File file;

	/*
	 * Constructor for the CreateCSV class.
	 * 
	 * @param filename the name of the file to be created
	 */
	public CreateCSV(String filename) {
// creating the file
		try {
			File file = new File(filename);
			if (file.createNewFile()) {
				System.out.println("File created successfully");
			} else {
				System.out.println("File already exists");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		pathToFile = filename;
		// for now I'll leave it like this I would like to concatenate the path and the
		// filename to be more precise
		file = new File(pathToFile);
		try {
			// create FileWriter object with file as parameter
			FileWriter outputfile = new FileWriter(file); // create CSVWriter object filewriter object as parameter
			CSVWriter writer = new CSVWriter(outputfile);

			// adding header to csv
			String[] header = { "Name", "POI" };// POI stands for Point of Interest
			writer.writeNext(header);

			// closing writer connection
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	};

	/**
	 * This method returns the file object
	 * 
	 * @return file object
	 */
	public File getRefToFile() {
		return this.file;
	}
}