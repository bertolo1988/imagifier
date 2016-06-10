package org.bertolo1988.imagifier.imagifier;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.bertolo1988.imagifier.imagifier.types.ColorName;

public class PixelImage {

	private BufferedImage image;
	private Color representedColor;
	private int averageGrayLevel;
	private ColorName dominantColor;

	public PixelImage(BufferedImage image, Color representedColor, int averageGrayLevel, ColorName dominantColor) {
		super();
		this.image = image;
		this.representedColor = representedColor;
		this.averageGrayLevel = averageGrayLevel;
		this.dominantColor = dominantColor;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public Color getRepresentedColor() {
		return representedColor;
	}

	public void setRepresentedColor(Color representedColor) {
		this.representedColor = representedColor;
	}

	public int getAverageGrayLevel() {
		return averageGrayLevel;
	}

	public void setAverageGrayLevel(int averageGrayLevel) {
		this.averageGrayLevel = averageGrayLevel;
	}

	public ColorName getDominantColor() {
		return dominantColor;
	}

	public void setDominantColor(ColorName dominantColor) {
		this.dominantColor = dominantColor;
	}

}
