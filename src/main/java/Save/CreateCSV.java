package Save;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVWriter;
/**
 * This class is supposed to create a csv file and write it's header. 
 * It should only be called once in the script. (Or you can create more DataBase if you wish) 
 * Input: the filename, it should sound like "TestDataBase.csv" for example. 
 * One can access the path leading to the file linked to the method. 
 * @author rabym
 *
 */
public class CreateCSV {
	public String pathToFile;
	private File file;
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
		pathToFile=filename; 
		//for now I'll leave it like this I would like to concatenate the path and the filename to be more precise
	    file = new File(pathToFile);
	    try {
	        // create FileWriter object with file as parameter
	        FileWriter outputfile = new FileWriter(file);
	  
	        // create CSVWriter object filewriter object as parameter
	        CSVWriter writer = new CSVWriter(outputfile);
	  
	        // adding header to csv
	        String[] header = { "Name", "POI" };//POI stands for Point of Interest
	        writer.writeNext(header);
	  
	        // closing writer connection
	        writer.close();
	    }
	    catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    
	};
	public File getRefToFile() {
		return this.file;
	}
}
