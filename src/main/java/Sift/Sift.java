package Sift;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.opencv.calib3d.Calib3d;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.SIFT;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Sift {

	static {
		// Load the native OpenCV library
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static Mat compareCards(Mat i, File BDD) {

		// Threshold the target image to isolate the cards
		Mat thresholdedImage = threshold(i);

		// Find the edges and corners of each large white region in the thresholded
		// image
		List<CardRegion> cardRegions = findCardRegions(thresholdedImage);

		// Load the set of template images
		List<Mat> templateImages = loadTemplateImages(BDD);

		// For each template image, find the transform that maps the template onto the
		// card in the target image
		// and compute the sum squared difference between the mapping and the target
		// image
		for (Mat templateImage : templateImages) {
			double minDiff = Double.MAX_VALUE;
			Mat bestTransform = null;
			for (CardRegion cardRegion : cardRegions) {
				Mat transform = findTransform(templateImage, cardRegion);
				double diff = computeDiff(templateImage, i, transform);
				if (diff < minDiff) {
					minDiff = diff;
					bestTransform = transform;
				}
			}

			// Select the template image with the lowest difference as the best match
			if (minDiff < 10f) {
				return templateImage;
			}
		}

		return i;
	}

	// Thresholds the target image to isolate the cards
	private static Mat threshold(Mat image) {
		Mat thresholdedImage = new Mat();
		Imgproc.threshold(image, thresholdedImage, 100, 255, Imgproc.THRESH_BINARY);
		return thresholdedImage;
	}

	// Finds the edges and corners of each large white region in the thresholded
	// image
	private static List<CardRegion> findCardRegions(Mat thresholdedImage) {
		// Detect edges using the Canny edge detector
		Mat edges = new Mat();
		Imgproc.Canny(thresholdedImage, edges, 50, 200);

		// Find contours in the thresholded image
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Mat hierarchy = new Mat();
		Imgproc.findContours(thresholdedImage, contours, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);

		// Find corners in each contour
		List<CardRegion> cardRegions = new ArrayList<CardRegion>();
		for (MatOfPoint contour : contours) {
			MatOfPoint2f contour2f = new MatOfPoint2f(contour.toArray());
			MatOfPoint2f approx = new MatOfPoint2f();
			Imgproc.approxPolyDP(contour2f, approx, Imgproc.arcLength(contour2f, true) * 0.02, true);
			if (approx.total() == 4) {
				RotatedRect rotatedRect = Imgproc.minAreaRect(approx);
				Rect rect = rotatedRect.boundingRect();
				Mat image = thresholdedImage.submat(rect);

				MatOfPoint2f imagePoints = new MatOfPoint2f();
				MatOfPoint2f cornerPoints = new MatOfPoint2f();
				Mat grayImage = new Mat();
				Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);
				MatOfPoint points1 = new MatOfPoint();
				Imgproc.goodFeaturesToTrack(grayImage, points1, 4, 0.01, 10);

				Core.perspectiveTransform(imagePoints, cornerPoints,
						Imgproc.getPerspectiveTransform(imagePoints, points1));
				List<org.opencv.core.Point> cornerList = cornerPoints.toList();

				cardRegions.add(new CardRegion(image, cornerList));
			}
		}

		return cardRegions;
	}

	// Loads the set of template images
	private static List<Mat> loadTemplateImages(File BDD) {
		List<Mat> templateImages = new ArrayList<Mat>();
		String[] imagesPath = BDD.list();
		for (String imgPath : imagesPath) {
			File currentImg = new File(BDD.getPath(), imgPath);
			Mat img = Imgcodecs.imread(currentImg.getPath());
			templateImages.add(img);
		}
		return templateImages;
	}

	// Finds the transform that maps the template onto the card in the target image
	private static Mat findTransform(Mat templateImage, CardRegion cardRegion) {
		// Detect SIFT keypoints and descriptors in the template image
		SIFT sift = SIFT.create();
		MatOfKeyPoint templateKeypoints = new MatOfKeyPoint();
		Mat templateDescriptors = new Mat();
		sift.detectAndCompute(templateImage, new Mat(), templateKeypoints, templateDescriptors);

		// Detect SIFT keypoints and descriptors in the card region
		MatOfKeyPoint cardKeypoints = new MatOfKeyPoint();
		Mat cardDescriptors = new Mat();
		sift.detectAndCompute(cardRegion.image, new Mat(), cardKeypoints, cardDescriptors);

		// Use vl_ubcmatch to match the feature descriptors
		DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_SL2);
		MatOfDMatch matches = new MatOfDMatch();
		matcher.match(templateDescriptors, cardDescriptors, matches);

		// Use RANSAC to find the transform that best maps the features to the matches
		List<org.opencv.core.DMatch> matchList = matches.toList();
		List<org.opencv.core.Point> templatePoints = new ArrayList<Point>();
		List<org.opencv.core.Point> cardPoints = new ArrayList<Point>();
		for (org.opencv.core.DMatch match : matchList) {
			templatePoints.add(templateKeypoints.toList().get(match.queryIdx).pt);
			cardPoints.add(cardKeypoints.toList().get(match.trainIdx).pt);
		}
		MatOfPoint2f mappedTemplatePoints = new MatOfPoint2f();
		MatOfPoint2f targetImagePoints = new MatOfPoint2f();
		Mat transform = Calib3d.findHomography(mappedTemplatePoints, targetImagePoints, Calib3d.RANSAC, 3);

		return transform;
	}

	// Computes the sum squared difference between the mapping of the template onto
	// the target image and the target image
	private static double computeDiff(Mat templateImage, Mat targetImage, Mat transform) {
		Mat mappedTemplate = new Mat();
		Imgproc.warpPerspective(templateImage, mappedTemplate, transform, targetImage.size());
		Mat diff = new Mat();
		Core.absdiff(mappedTemplate, targetImage, diff);
		diff.convertTo(diff, CvType.CV_32F);
		diff = diff.mul(diff);
		Scalar sum = Core.sumElems(diff);
		double sqDiff = sum.val[0] + sum.val[1] + sum.val[2];
		return sqDiff;
	}
}

// Represents a region of an image containing a card
class CardRegion {
	public Mat image;
	public List<org.opencv.core.Point> corners;

	public CardRegion(Mat image, List<org.opencv.core.Point> corners) {
		this.image = image;
		this.corners = corners;
	}
}
