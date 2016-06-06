package org.bertolo1988.imagifier.imagifier;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Imagifier {

	static final String PNG = "png";
	static final String DEFAULT_IMAGES_PATH = "./sample_images";
	static final String DEFAULT_TARGET_IMAGE = "image_target";
	static final String DEFAULT_RESULT_IMAGE = "image_result";
	static final int N = 100;

	public static void main(String[] args) throws IOException {
		BufferedImage targetImage = ImageIO.read(new File(DEFAULT_TARGET_IMAGE + PNG));
		final int k = targetImage.getWidth() / N;
		final int p = targetImage.getHeight() / N;

		ArrayList<PixelImage> sampleImages = buildSampleColorImages(k, p);

		// todo:imagify here
	}

	private static ArrayList<PixelImage> buildSampleColorImages(int newWidth, int newHeight) throws IOException {
		File[] sampleImagesFiles = new File(DEFAULT_IMAGES_PATH).listFiles();
		ArrayList<PixelImage> sampleImages = new ArrayList<PixelImage>();
		for (int i = 0; i < sampleImagesFiles.length; i++) {
			BufferedImage image = ImageManipulationUtils.resize(ImageIO.read(sampleImagesFiles[i]), newWidth,
					newHeight);
			Color averageColor = ImageManipulationUtils.averageColor(image);
			sampleImages.add(new PixelImage(sampleImagesFiles[i].getName(), image, averageColor));
		}
		return sampleImages;
	}

}
