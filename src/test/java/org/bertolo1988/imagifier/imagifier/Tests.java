package org.bertolo1988.imagifier.imagifier;

import static org.junit.Assert.*;

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

}
