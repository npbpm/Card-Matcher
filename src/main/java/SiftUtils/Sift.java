// aide pour le DescriptorMatcher : https://www.programcreek.com/java-api-examples/?api=org.opencv.features2d.DescriptorMatcher

package SiftUtils;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.DMatch;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.SIFT;
import org.opencv.imgproc.Imgproc;


public class Sift {		
	public double rate;
	
	public void showSift(Mat i) {
		SIFT s = SIFT.create(20);
		 MatOfKeyPoint keyPointI = new MatOfKeyPoint();
	     Mat descriptor = new Mat(i.height(),i.width(),0);
	     Mat mask1 = new Mat();
	     s.detectAndCompute(i, mask1, keyPointI, descriptor); 
	     Features2d.drawKeypoints(i, keyPointI, i);
	}
	
	public Mat compareCards(Mat i1, Mat i2) {
		//i2 c'est l'image a comparer
   	    SIFT s = SIFT.create();
	
		Mat bw = new Mat();
		Imgproc.cvtColor(i2, bw, Imgproc.COLOR_RGB2GRAY);
		
		MatOfKeyPoint kp1 = new MatOfKeyPoint();
        Mat desc1 = new Mat(i1.height(),i1.width(),0);
	    Mat mask1 = new Mat();
	    s.detectAndCompute(i1, mask1, kp1, desc1);
	    
		MatOfKeyPoint kp2 = new MatOfKeyPoint();
		Mat desc2 = new Mat(bw.height(),bw.width(),0);
		Mat mask2 = new Mat();
		s.detectAndCompute(bw, mask2, kp2, desc2);

	    
	    // Matching descriptors
	    DescriptorMatcher dm = DescriptorMatcher.create(DescriptorMatcher.FLANNBASED);
	   
	    MatOfDMatch Matches = new MatOfDMatch();
		dm.match(desc1, desc2, Matches);
    
		List<DMatch> l = Matches.toList();
		List<DMatch> goodMatch = new ArrayList<DMatch>();
		for (int i = 0; i < l.size(); i++) {
			DMatch dmatch = l.get(i);
			if (Math.abs(dmatch.queryIdx - dmatch.trainIdx) < 5f) {
				goodMatch.add(dmatch);
			}
			
		}
		
		// Matching rate of descriptors

		rate=100*goodMatch.size()/l.size();
		
		
		Matches.fromList(goodMatch);
		
		Mat OutImage = new Mat();
		Features2d.drawMatches(i1, kp1, i2, kp2, Matches, OutImage);
		
		return OutImage;
	  
	}
}
