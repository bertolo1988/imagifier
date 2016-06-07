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
	static final String DEFAULT_SOURCE_IMAGE = "image_source";
	static final String DEFAULT_RESULT_IMAGE = "image_result";
	static final int N = 1;
	static final int SQUARE_SIDE = 15;

	public static void main(String[] args) throws IOException {
		BufferedImage sourceImage = ImageIO.read(new File(DEFAULT_SOURCE_IMAGE + "." + PNG));
		ArrayList<PixelImage> sampleImages = buildSampleColorImages();
		BufferedImage resultImage = buildImagifiedImage(sampleImages, sourceImage);
		ImageIO.write(resultImage, PNG, new File(DEFAULT_RESULT_IMAGE + "." + PNG));
		System.out.println("Done!");
	}

	private static BufferedImage buildImagifiedImage(ArrayList<PixelImage> sampleImages, BufferedImage sourceImage) {
		int sourceWidth = sourceImage.getWidth();
		int sourceHeight = sourceImage.getHeight();
		BufferedImage finalImage = new BufferedImage((sourceWidth / N) * SQUARE_SIDE, (sourceHeight / N) * SQUARE_SIDE,
				sourceImage.getType());
		for (int x = 0, y = 0; y < sourceHeight / N; y++) {
			for (x = 0; x < sourceWidth / N; x++) {
				BufferedImage crop = ImageManipulationUtils.cropImage(sourceImage, x * N, y * N, N, N);
				Color cropColor = ImageManipulationUtils.averageColor(crop);
				BufferedImage bestSuitedSample = calcBestColor(sampleImages, cropColor);
				ImageManipulationUtils.replaceAt(finalImage, bestSuitedSample, x * SQUARE_SIDE, y * SQUARE_SIDE);
			}
		}
		return finalImage;
	}

	private static BufferedImage calcBestColor(ArrayList<PixelImage> sampleImages, Color cropColor) {
		int result = 0;

		for (int minimum = 255 * 3, i = 0; i < sampleImages.size(); i++) {
			Color sampleColor = ImageManipulationUtils.averageColor(sampleImages.get(i).getImage());
			int quantification = quantifyColorDeviation(cropColor, sampleColor);
			if (minimum > quantification) {
				minimum = quantification;
				result = i;
			}
		}
		return sampleImages.get(result).getImage();
	}

	private static ArrayList<PixelImage> buildSampleColorImages() throws IOException {
		File[] sampleImagesFiles = new File(DEFAULT_IMAGES_PATH).listFiles();
		ArrayList<PixelImage> sampleImages = new ArrayList<PixelImage>();
		for (int i = 0; i < sampleImagesFiles.length; i++) {
			BufferedImage image = ImageManipulationUtils.resize(ImageIO.read(sampleImagesFiles[i]), SQUARE_SIDE,
					SQUARE_SIDE);
			Color averageColor = ImageManipulationUtils.averageColor(image);
			sampleImages.add(new PixelImage(sampleImagesFiles[i].getName(), image, averageColor));
		}
		return sampleImages;
	}

	public static int quantifyColorDeviation(Color colorA, Color colorB) {
		int result = 0;
		result += Math.abs(colorA.getBlue() - colorB.getBlue());
		result += Math.abs(colorA.getRed() - colorB.getRed());
		result += Math.abs(colorA.getGreen() - colorB.getGreen());
		return result;
	}

}
