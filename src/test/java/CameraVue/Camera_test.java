package CameraVue;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import Main.App;

public class Camera_test {
	
	public void clear() {
		String userDirectory = System.getProperty("user.dir");
		new File(userDirectory+"/Test").delete();
		new File(userDirectory+"/Apprentissage").delete();
		new File(userDirectory+"/TestResults").delete();
		
	}

	@Test
	public void test_fileTest() {
		clear();
		String userDirectory = System.getProperty("user.dir");
		File uDt = new File(userDirectory+"/Test");
		assertEquals(uDt.exists(),false);
		App a = new App();
		String[] args = new String[0];
		a.main(args);
		assertEquals(uDt.exists(),true);
		clear();
	}
	
	@Test
	public void test_fileApprentissage() {
		clear();
		String userDirectory = System.getProperty("user.dir");
		File uDa = new File(userDirectory+"/Apprentissage");
		assertEquals(uDa.exists(),false);
		App a = new App();
		String[] args = new String[0];
		a.main(args);
		assertEquals(uDa.exists(),true);
		clear();
	}
	
	@Test 
	public void test_TestResults() {
		clear();
		String userDirectory = System.getProperty("user.dir");
		File uDtr = new File(userDirectory+"/TestResults");
		assertEquals(uDtr.exists(),false);
		App a = new App();
		String[] args = new String[0];
		a.main(args);
		assertEquals(uDtr.exists(),true);
		clear();
	}

}
