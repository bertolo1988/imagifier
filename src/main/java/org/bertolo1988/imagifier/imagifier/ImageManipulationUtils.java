package org.bertolo1988.imagifier.imagifier;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public final class ImageManipulationUtils {

	public static BufferedImage resize(BufferedImage img, int newW, int newH) {
		Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		return dimg;
	}

	public static Color averageColor(BufferedImage image) {
		return ImageManipulationUtils.averageColor(image, 0, 0, image.getWidth(), image.getHeight());
	}

	public static Color averageColor(BufferedImage bi, int x0, int y0, int w, int h) {
		int x1 = x0 + w;
		int y1 = y0 + h;
		long sumr = 0, sumg = 0, sumb = 0;
		for (int x = x0; x < x1; x++) {
			for (int y = y0; y < y1; y++) {
				Color pixel = new Color(bi.getRGB(x, y));
				sumr += pixel.getRed();
				sumg += pixel.getGreen();
				sumb += pixel.getBlue();
			}
		}
		int num = w * h;
		return new Color((int) sumr / num, (int) sumg / num, (int) sumb / num);
	}

	public static BufferedImage cropSquare(BufferedImage src, int x, int y, int s) {
		return src.getSubimage(x, y, s, s);
	}

	public static BufferedImage cropImage(BufferedImage src, int x, int y, int w, int h) {
		return src.getSubimage(x, y, w, h);
	}

	public static int calcAverageGrayLevel(BufferedImage img) {
		long grayLevel = 0;
		for (int x = 0; x < img.getWidth(); ++x)
			for (int y = 0; y < img.getHeight(); ++y) {
				int rgb = img.getRGB(x, y);
				int r = (rgb >> 16) & 0xFF;
				int g = (rgb >> 8) & 0xFF;
				int b = (rgb & 0xFF);

				grayLevel = grayLevel + (r + g + b) / 3;
			}
		return (int) (grayLevel / (img.getWidth() * img.getHeight()));
	}

	public static BufferedImage replaceAt(BufferedImage sourceImage, BufferedImage partImage, int x, int y) {
		Graphics2D g2Source = sourceImage.createGraphics();
		g2Source.drawImage(partImage, x, y, null);
		g2Source.dispose();
		return sourceImage;
	}

}
