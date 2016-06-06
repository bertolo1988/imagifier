package org.bertolo1988.imagifier.imagifier;

import static org.junit.Assert.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.junit.Before;
import org.junit.Test;

public class Tests {

	static final String TESTS_OUTPUT_PATH = "tmp/";
	static final String DEFAULT_IMAGES_PATH = "./sample_images/";
	static final String PNG = "png";

	@Before
	public void setUp() throws Exception {
		File theDir = new File(TESTS_OUTPUT_PATH);
		if (!theDir.exists()) {
			theDir.mkdir();
		}
	}

	@Test
	public void testResize() throws IOException {
		String targetImageName = "agentcricket";
		BufferedImage targetImage = ImageIO.read(new File(DEFAULT_IMAGES_PATH + targetImageName + "." + PNG));
		BufferedImage resizedImage = ImageManipulationUtils.resize(targetImage, targetImage.getWidth() / 2,
				targetImage.getHeight() / 2);
		ImageIO.write(resizedImage, PNG, new File(TESTS_OUTPUT_PATH + targetImageName + "_halved." + PNG));
		assertTrue(new File(TESTS_OUTPUT_PATH + targetImageName + "_halved." + PNG).isFile());
	}

	@Test
	public void testAverageColor() throws IOException {
		String targetImage1 = "redsquare";
		String targetImage2 = "twitter";
		BufferedImage image1 = ImageIO.read(new File(DEFAULT_IMAGES_PATH + targetImage1 + "." + PNG));
		BufferedImage image2 = ImageIO.read(new File(DEFAULT_IMAGES_PATH + targetImage2 + "." + PNG));
		Color image1Color = ImageManipulationUtils.averageColor(image1);
		Color image2Color = ImageManipulationUtils.averageColor(image2);
		assertTrue(image1Color.getRed() > image2Color.getRed());
		assertTrue(image2Color.getBlue() > image1Color.getBlue());
	}

	@Test
	public void testCropImage() throws IOException {
		String imageName = "twitter";
		BufferedImage image = ImageIO.read(new File(DEFAULT_IMAGES_PATH + imageName + "." + PNG));
		BufferedImage resultingImage = ImageManipulationUtils.cropImage(image, 25, 25, 25, 25);
		assertTrue(resultingImage.getHeight() == 25);
		assertTrue(resultingImage.getWidth() == 25);
		ImageIO.write(resultingImage, PNG, new File(TESTS_OUTPUT_PATH + imageName + "_forth_corner." + PNG));
		assertTrue(new File(TESTS_OUTPUT_PATH + imageName + "_forth_corner." + PNG).isFile());
	}

}
