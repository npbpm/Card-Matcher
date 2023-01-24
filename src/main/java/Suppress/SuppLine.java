package Suppress;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Save.CreateCSV;
import Save.Save;

public class SuppLine {
	private List<String[]> data=new ArrayList<String[]>();
	@SuppressWarnings("unlikely-arg-type")
	public SuppLine (File file, String[] name) {
		String filename=file.getName();
		try {
			Scanner sc = new Scanner(file);
			sc.useDelimiter(",");
			String[] line;
			//setting comma as delimiter pattern
		    while (sc.hasNext()) {
		    	line=sc.next().split(",");
		    	String currentName=line[1];
		    	if (currentName.equals(name)) {
		    		data.add(line);
		    	}
		    }
		    //closes the scanner 
		    sc.close();
		    new CreateCSV(filename);
		    for (String[] lineArray : data) {
		    	new Save(file, lineArray);
		    }
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}