package org.bertolo1988.imagifier.imagifier;

import static org.junit.Assert.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.bertolo1988.imagifier.imagifier.types.ColorName;
import org.bertolo1988.imagifier.imagifier.types.ImageType;
import org.junit.Before;
import org.junit.Test;

public class ImageManipulationUtilsTests {

	private static final String TESTS_OUTPUT_PATH = "tmp/";
	private static final String DEFAULT_IMAGES_PATH = "./sample_images/";

	@Before
	public void setUp() throws Exception {
		File theDir = new File(TESTS_OUTPUT_PATH);
		if (!theDir.exists()) {
			theDir.mkdir();
		}
	}

	@Test
	public void testResize() throws IOException {
		String targetImageName = "agent_cricket";
		BufferedImage targetImage = ImageIO
				.read(new File(DEFAULT_IMAGES_PATH + targetImageName + "." + ImageType.PNG.toString()));
		BufferedImage resizedImage = ImageManipulationUtils.resize(targetImage, targetImage.getWidth() / 2,
				targetImage.getHeight() / 2);
		ImageIO.write(resizedImage, ImageType.PNG.toString(),
				new File(TESTS_OUTPUT_PATH + targetImageName + "_halved." + ImageType.PNG.toString()));
		assertTrue(new File(TESTS_OUTPUT_PATH + targetImageName + "_halved." + ImageType.PNG.toString()).isFile());
	}

	@Test
	public void testAverageColor() throws IOException {
		String targetImage1 = "redsquare";
		String targetImage2 = "twitter";
		BufferedImage image1 = ImageIO
				.read(new File(DEFAULT_IMAGES_PATH + targetImage1 + "." + ImageType.PNG.toString()));
		BufferedImage image2 = ImageIO
				.read(new File(DEFAULT_IMAGES_PATH + targetImage2 + "." + ImageType.PNG.toString()));
		Color image1Color = ImageManipulationUtils.averageColor(image1);
		Color image2Color = ImageManipulationUtils.averageColor(image2);
		assertTrue(image1Color.getRed() > image2Color.getRed());
		assertTrue(image2Color.getBlue() > image1Color.getBlue());
	}

	@Test
	public void testCropImage() throws IOException {
		String imageName = "twitter";
		BufferedImage image = ImageIO.read(new File(DEFAULT_IMAGES_PATH + imageName + "." + ImageType.PNG.toString()));
		BufferedImage resultingImage = ImageManipulationUtils.cropImage(image, 25, 25, 25, 25);
		assertTrue(resultingImage.getHeight() == 25);
		assertTrue(resultingImage.getWidth() == 25);
		ImageIO.write(resultingImage, ImageType.PNG.toString(),
				new File(TESTS_OUTPUT_PATH + imageName + "_forth_corner." + ImageType.PNG.toString()));
		assertTrue(new File(TESTS_OUTPUT_PATH + imageName + "_forth_corner." + ImageType.PNG.toString()).isFile());
	}

	@Test
	public void testReplaceAt() throws IOException {
		String image1Name = "twitter";
		BufferedImage image1 = ImageIO
				.read(new File(DEFAULT_IMAGES_PATH + image1Name + "." + ImageType.PNG.toString()));
		BufferedImage image1Cropped = ImageManipulationUtils.cropImage(image1, 25, 25, 25, 25);
		String targetImage2 = "redsquare";
		BufferedImage image2 = ImageIO
				.read(new File(DEFAULT_IMAGES_PATH + targetImage2 + "." + ImageType.PNG.toString()));
		BufferedImage resultImage = ImageManipulationUtils.replaceAt(image2, image1Cropped, 25, 25);
		ImageIO.write(resultImage, ImageType.PNG.toString(),
				new File(TESTS_OUTPUT_PATH + "hibridImage." + ImageType.PNG.toString()));
		assertTrue(new File(TESTS_OUTPUT_PATH + "hibridImage." + ImageType.PNG.toString()).isFile());
	}

	@Test
	public void testPixelImage() throws IOException {
		String image1Name = "twitter";
		BufferedImage image1 = ImageIO
				.read(new File(DEFAULT_IMAGES_PATH + image1Name + "." + ImageType.PNG.toString()));
		Color averageColor = ImageManipulationUtils.averageColor(image1);
		int grayScale = ImageManipulationUtils.calcAverageGrayLevel(image1);
		ColorName dominantColor = ImageManipulationUtils.getDominantColorName(averageColor);
		PixelImage pix = new PixelImage(image1, averageColor, grayScale, dominantColor);
		assertTrue(pix != null);
	}

	@Test
	public void testDominantColor() throws IOException {
		String image1Name = "twitter";
		String image2Name = "redsquare";
		BufferedImage image1 = ImageIO
				.read(new File(DEFAULT_IMAGES_PATH + image1Name + "." + ImageType.PNG.toString()));
		BufferedImage image2 = ImageIO
				.read(new File(DEFAULT_IMAGES_PATH + image2Name + "." + ImageType.PNG.toString()));
		ColorName twitterColor = ImageManipulationUtils
				.getDominantColorName(ImageManipulationUtils.averageColor(image1));
		ColorName redSquareColor = ImageManipulationUtils
				.getDominantColorName(ImageManipulationUtils.averageColor(image2));
		assertTrue(twitterColor == ColorName.BLUE);
		assertTrue(redSquareColor == ColorName.RED);
	}
}
