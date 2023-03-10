package SiftUtils;

// aide: https://docs.opencv.org/3.4/d5/d6f/tutorial_feature_flann_matcher.html

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.DMatch;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Scalar;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.SIFT;

public class Flann {
	
	public float rate;
	
    public Mat run(Mat img1, Mat img2) {

        int nb_pt = 200;
        SIFT detector = SIFT.create(nb_pt);
        MatOfKeyPoint keypoints1 = new MatOfKeyPoint(), keypoints2 = new MatOfKeyPoint();
        Mat descriptors1 = new Mat(), descriptors2 = new Mat();
        detector.detectAndCompute(img1, new Mat(), keypoints1, descriptors1);
        detector.detectAndCompute(img2, new Mat(), keypoints2, descriptors2);
 
        DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.FLANNBASED);
        List<MatOfDMatch> knnMatches = new ArrayList<MatOfDMatch>();
        matcher.knnMatch(descriptors1, descriptors2, knnMatches, 2);

        float ratioThresh = 0.7f;
        List<DMatch> listOfGoodMatches = new ArrayList<DMatch>();
        for (int i = 0; i < knnMatches.size(); i++) {
            if (knnMatches.get(i).rows() > 1) {
                DMatch[] matches = knnMatches.get(i).toArray();
                if (matches[0].distance < ratioThresh * matches[1].distance) {
                    listOfGoodMatches.add(matches[0]);
                }
            }
        }
        
        MatOfDMatch goodMatches = new MatOfDMatch();
        goodMatches.fromList(listOfGoodMatches);

        Mat imgMatches = new Mat();
        Features2d.drawMatches(img1, keypoints1, img2, keypoints2, goodMatches, imgMatches, Scalar.all(-1),
                Scalar.all(-1), new MatOfByte(), Features2d.NOT_DRAW_SINGLE_POINTS);

        rate=100*listOfGoodMatches.size()/knnMatches.size();
		
		return imgMatches;
    }
}
