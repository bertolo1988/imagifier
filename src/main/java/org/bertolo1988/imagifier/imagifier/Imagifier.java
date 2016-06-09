package org.bertolo1988.imagifier.imagifier;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.bertolo1988.imagifier.imagifier.exceptions.ImageTooBigException;

public class Imagifier {

	private static final int PERCENTAGE_INCREMENTS = 25;
	private static final int BYTE_ARRAY_MAX = Integer.MAX_VALUE / 10;
	private static Imagifier instance = null;
	private static final int SRC_SQUARE_SIZE = 4;

	private Imagifier() {
	}

	public static Imagifier getInstance() {
		if (instance == null) {
			instance = new Imagifier();
		}
		return instance;
	}

	public BufferedImage imagify(ArrayList<PixelImage> sampleImages, BufferedImage sourceImage, int squareSize)
			throws Exception {
		int sourceWidth = sourceImage.getWidth();
		int sourceHeight = sourceImage.getHeight();
		BufferedImage finalImage = createOutputBufferedImage(sourceImage, sourceWidth, sourceHeight, squareSize);
		int lastPercentage = 0;
		for (int y = 0; y < sourceHeight / SRC_SQUARE_SIZE; y++) {
			lastPercentage = printPercentage(y, sourceHeight / SRC_SQUARE_SIZE, lastPercentage);
			for (int x = 0; x < sourceWidth / SRC_SQUARE_SIZE; x++) {
				BufferedImage crop = ImageManipulationUtils.cropSquare(sourceImage, x * SRC_SQUARE_SIZE,
						y * SRC_SQUARE_SIZE, SRC_SQUARE_SIZE);
				Color cropColor = ImageManipulationUtils.averageColor(crop);
				BufferedImage bestSuitedSample = calcBestColor(sampleImages, cropColor);
				ImageManipulationUtils.replaceAt(finalImage, bestSuitedSample, x * squareSize, y * squareSize);
			}
		}
		return finalImage;
	}

	private BufferedImage createOutputBufferedImage(BufferedImage sourceImage, int sourceWidth, int sourceHeight,
			int squareSize) throws Exception {
		int targetWidth = sourceWidth / SRC_SQUARE_SIZE * squareSize;
		int targetHeight = sourceHeight / SRC_SQUARE_SIZE * squareSize;
		if (targetWidth * targetHeight >= BYTE_ARRAY_MAX || targetWidth * targetHeight < 0) {
			throw new ImageTooBigException(
					"Image is too big. You are trying to create a " + targetWidth + "x" + targetHeight + " image.");
		}
		return new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
	}

	private BufferedImage calcBestColor(ArrayList<PixelImage> sampleImages, Color cropColor) {
		int result = 0;
		int minimum = 255 * 3;
		for (int i = 0; i < sampleImages.size(); i++) {
			int quantification = quantifyColorDeviation(cropColor, sampleImages.get(i).getRepresentedColor());
			if (minimum > quantification) {
				minimum = quantification;
				result = i;
			}
		}
		return sampleImages.get(result).getImage();
	}

	public int quantifyColorDeviation(Color colorA, Color colorB) {
		int result = 0;
		result += Math.abs(colorA.getBlue() - colorB.getBlue());
		result += Math.abs(colorA.getRed() - colorB.getRed());
		result += Math.abs(colorA.getGreen() - colorB.getGreen());
		return result;
	}

	private int printPercentage(int y, int i, int lastPercentage) {
		int percentage = (y * 100) / i;
		if (percentage % PERCENTAGE_INCREMENTS == 0 && percentage > lastPercentage) {
			System.out.print(percentage + "%...");
		}
		return percentage;
	}

}
