package Sift;

import java.util.ArrayList;
import java.util.List;

import org.opencv.calib3d.Calib3d;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.DMatch;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Scalar;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.KeyPoint;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.features2d.SIFT;

public class Flann {
    
    public double rate;

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

        List<DMatch> listOfGoodMatches = new ArrayList<DMatch>();
        for (int i = 0; i < knnMatches.size(); i++) {
            if (knnMatches.get(i).rows() > 1) {
                DMatch[] matches = knnMatches.get(i).toArray();
                if (matches[0].distance < 0.8f * matches[1].distance) {
                    listOfGoodMatches.add(matches[0]);
                }
            }
        }
        
        MatOfDMatch goodMatches = new MatOfDMatch();
        goodMatches.fromList(listOfGoodMatches);

        MatOfPoint2f obj = new MatOfPoint2f();
        MatOfPoint2f scene = new MatOfPoint2f();
        List<org.opencv.core.KeyPoint> keypoints1List = keypoints1.toList();
        List<org.opencv.core.KeyPoint> keypoints2List = keypoints2.toList();
        for (int i = 0; i < listOfGoodMatches.size(); i++) {
        	Mat mat = new Mat(1, 2, CvType.CV_32F);
        	mat.put(0, 0, keypoints1List.get(listOfGoodMatches.get(i).queryIdx).pt.x);
        	mat.put(0, 1, keypoints1List.get(listOfGoodMatches.get(i).queryIdx).pt.y);
        	obj.push_back(mat);
        	mat = new Mat(1, 2, CvType.CV_32F);
        	mat.put(0, 0, keypoints2List.get(listOfGoodMatches.get(i).trainIdx).pt.x);
        	mat.put(0, 1, keypoints2List.get(listOfGoodMatches.get(i).trainIdx).pt.y);
        	scene.push_back(mat);

        }

        Mat H = Calib3d.findHomography(obj, scene, Calib3d.RANSAC, 3);

        rate = (double) Core.countNonZero(H) / listOfGoodMatches.size();

        Mat imgMatches = new Mat();
        Features2d.drawMatches(img1, keypoints1, img2, keypoints2, goodMatches, imgMatches, Scalar.all(-1),
                Scalar.all(-1), new MatOfByte(), Features2d.NOT_DRAW_SINGLE_POINTS);
        return imgMatches;
    }
}
                
